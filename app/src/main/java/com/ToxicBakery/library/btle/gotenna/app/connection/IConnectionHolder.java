package com.ToxicBakery.library.btle.gotenna.app.connection;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.ILeConnection;

/**
 * Created by tencent on 8/19/16.
 */
public interface IConnectionHolder {
    /**
     * Get the connection associated to the device.
     *
     * @param bluetoothDevice connection for device
     */
    ILeConnection get(@NonNull BluetoothDevice bluetoothDevice);

    /**
     * Add the connection associated to the device.
     *
     * @param bluetoothDevice device used in the connection
     * @param leConnection    connection to the device
     */
    void add(@NonNull BluetoothDevice bluetoothDevice,
             @NonNull ILeConnection leConnection);

    /**
     * Remove a connection from the holder.
     *
     * @param bluetoothDevice device to be removed
     */
    void remove(@NonNull BluetoothDevice bluetoothDevice);
}
