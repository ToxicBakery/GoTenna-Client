package com.ToxicBakery.library.btle.gotenna;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Connection class attempting to connect and manage a GoTenna device.
 */
public class GoTennaConnection extends BluetoothGattCallback implements ILeConnection {

    private final BluetoothDevice bluetoothDevice;

    private GattCallback gattCallback;
    private BluetoothGatt bluetoothGatt;

    /**
     * Constructs the connection with the given device.
     *
     * @param bluetoothDevice device for the connection
     */
    public GoTennaConnection(BluetoothDevice bluetoothDevice) {
        this.bluetoothDevice = bluetoothDevice;
    }

    @Override
    public void connect(@NonNull Context context) {
        bluetoothGatt = bluetoothDevice.connectGatt(context.getApplicationContext(), true, this);
    }

    @Override
    public void disconnect() {
        bluetoothGatt.disconnect();
    }

}
