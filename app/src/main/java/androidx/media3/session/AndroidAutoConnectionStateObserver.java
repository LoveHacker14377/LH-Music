/*
 * Patched copy of androidx.media3.session.AndroidAutoConnectionStateObserver from
 * media3-session 1.11.0.
 *
 * Upstream calls Context.getContentResolver().query(Uri.parse("content://androidx.car.app.connection"), ...)
 * directly on a background executor whenever MediaSessionLegacyStub enables its seekbar
 * button-reservation workaround (API 33+ non-automotive Google/Motorola/Sony/Nothing/Vivo/unknown
 * devices). On devices where Android Auto (com.google.android.projection.gearhead) is not
 * installed, that blind ContentResolver.query triggers framework log:
 *
 *   E/ActivityThread: Failed to find provider info for androidx.car.app.connection
 *
 * Guarding the query with PackageManager.resolveContentProvider("androidx.car.app.connection", ...)
 * keeps Android Auto detection working whenever the provider exists (declared in AndroidManifest
 * <queries>), while avoiding the missing-provider ActivityThread error on devices/emulators
 * without Android Auto.
 *
 * app/build.gradle.kts strips AndroidAutoConnectionStateObserver*.class from media3-session-1.11.0
 * so this copy is the single definition in both debug and release DEX outputs.
 */
package androidx.media3.session;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import androidx.core.content.ContextCompat;
import androidx.media3.common.util.BackgroundExecutor;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

final class AndroidAutoConnectionStateObserver {

    private static final String PROVIDER_AUTHORITY = "androidx.car.app.connection";
    private static final Uri QUERY_URI = Uri.parse("content://" + PROVIDER_AUTHORITY);
    private static final String QUERY_COLUMN = "CarConnectionState";
    private static final String BROADCAST_INTENT =
        "androidx.car.app.connection.action.CAR_CONNECTION_UPDATED";

    private final Context context;
    private final Runnable listener;
    private final Executor backgroundExecutor;
    private final AndroidAutoChangeReceiver changeReceiver;
    private final AtomicBoolean isConnected = new AtomicBoolean();
    private final AtomicBoolean isReleased = new AtomicBoolean();

    public AndroidAutoConnectionStateObserver(Context context, Runnable listener) {
        this.context = context.getApplicationContext();
        this.listener = listener;
        this.backgroundExecutor = BackgroundExecutor.get();
        this.changeReceiver = new AndroidAutoChangeReceiver();
        backgroundExecutor.execute(this::registerAndUpdateConnectionState);
    }

    public void release() {
        if (isReleased.getAndSet(true)) {
            return;
        }
        backgroundExecutor.execute(this::unregisterReceiverSafely);
    }

    public boolean isConnected() {
        return isConnected.get();
    }

    private void registerAndUpdateConnectionState() {
        if (isReleased.get()) {
            return;
        }
        IntentFilter filter = new IntentFilter(BROADCAST_INTENT);
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(changeReceiver, filter, Context.RECEIVER_EXPORTED);
        } else {
            ContextCompat.registerReceiver(
                context,
                changeReceiver,
                filter,
                ContextCompat.RECEIVER_EXPORTED
            );
        }
        updateConnectionState();
    }

    private void unregisterReceiverSafely() {
        try {
            context.unregisterReceiver(changeReceiver);
        } catch (IllegalArgumentException ignored) {
            // Receiver may not be registered if release() ran before registration or multiple times.
        }
    }

    private void updateConnectionState() {
        boolean wasConnected = isConnected.get();
        boolean nowConnected = queryConnectionState();
        isConnected.set(nowConnected);
        if (wasConnected != nowConnected && !isReleased.get()) {
            listener.run();
        }
    }

    private boolean queryConnectionState() {
        if (!isCarConnectionProviderAvailable()) {
            return false;
        }
        try (Cursor cursor =
            context
                .getContentResolver()
                .query(QUERY_URI, new String[] {QUERY_COLUMN}, null, null, null)) {
            if (cursor == null) {
                return false;
            }
            int columnIndex = cursor.getColumnIndex(QUERY_COLUMN);
            if (columnIndex == -1 || !cursor.moveToNext()) {
                return false;
            }
            return cursor.getInt(columnIndex) != 0;
        } catch (Exception e) {
            return false;
        }
    }

    @SuppressWarnings("deprecation")
    private boolean isCarConnectionProviderAvailable() {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        if (Build.VERSION.SDK_INT >= 33) {
            return packageManager.resolveContentProvider(
                    PROVIDER_AUTHORITY,
                    PackageManager.ComponentInfoFlags.of(0L)
                )
                != null;
        }
        return packageManager.resolveContentProvider(PROVIDER_AUTHORITY, 0) != null;
    }

    private final class AndroidAutoChangeReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            backgroundExecutor.execute(AndroidAutoConnectionStateObserver.this::updateConnectionState);
        }
    }
}
