package com.ToxicBakery.library.btle.gotenna;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;

/**
 * LE Device utilities
 */
public final class LeUtility {

    private LeUtility() {
    }

    /**
     * Determine if LE is supported on this device.
     *
     * @param context application context
     * @return true if device has LE support
     */
    public static boolean isLeSupported(@NonNull Context context) {
        return context.getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }

    /**
     * Determine if Bluetooth is enabled on the device.
     *
     * @param context application context
     * @return true if the device bluetooth radio is enabled (on)
     */
    public static boolean isBluetoothEnabled(@NonNull Context context) {
        BluetoothManager bluetoothManager = (BluetoothManager) context
                .getSystemService(Context.BLUETOOTH_SERVICE);

        BluetoothAdapter bluetoothAdapter = bluetoothManager.getAdapter();
        return bluetoothAdapter != null && bluetoothAdapter.isEnabled();

    }

}
