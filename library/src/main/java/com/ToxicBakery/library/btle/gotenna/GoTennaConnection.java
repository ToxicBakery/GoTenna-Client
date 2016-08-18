package com.ToxicBakery.library.btle.gotenna;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Connection class attempting to connect and manage a GoTenna device.
 */
public class GoTennaConnection implements ILeConnection {

    private final BluetoothDevice bluetoothDevice;
    private final GoTennaGattCallback gattCallback;

    private BluetoothGatt bluetoothGatt;

    /**
     * Constructs the connection with the given device and the default callback implementation.
     *
     * @param bluetoothDevice device for the connection
     */
    public GoTennaConnection(@NonNull BluetoothDevice bluetoothDevice) {
        this(bluetoothDevice, new GoTennaGattCallback());
    }

    /**
     * Constructs the connection with the given device and callback implementation.
     *
     * @param bluetoothDevice device for the connection
     * @param gattCallback    callback for handling device interactions
     */
    public GoTennaConnection(@NonNull BluetoothDevice bluetoothDevice,
                             @NonNull GoTennaGattCallback gattCallback) {

        this.bluetoothDevice = bluetoothDevice;
        this.gattCallback = gattCallback;
    }

    @Override
    public void connect(@NonNull Context context) {
        bluetoothGatt = bluetoothDevice.connectGatt(
                context.getApplicationContext(),
                true,
                gattCallback);
    }

    @Override
    public void disconnect() {
        bluetoothGatt.disconnect();
    }

    @Override
    public int getRssi() {
        return gattCallback.getLastRssi();
    }

    @Override
    public int getState() {
        return gattCallback.getLastState();
    }

    @Override
    public int getStatus() {
        return gattCallback.getLastStatus();
    }

}
