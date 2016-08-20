package com.ToxicBakery.library.btle.gotenna.app.connection;

import android.bluetooth.BluetoothDevice;
import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.ILeConnection;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Singleton;

/**
 * Holder for connections.
 */
@Singleton
public class ConnectionHolder implements IConnectionHolder {

    private final Map<String, ILeConnection> leConnections;

    /**
     * Create the holder instance.
     */
    public ConnectionHolder() {
        this.leConnections = new HashMap<>();
    }

    @Override
    public ILeConnection get(@NonNull BluetoothDevice bluetoothDevice) {
        String key = getKeyForDevice(bluetoothDevice);
        return leConnections.get(key);
    }

    @Override
    public void add(@NonNull BluetoothDevice bluetoothDevice,
                    @NonNull ILeConnection leConnection) {

        String key = getKeyForDevice(bluetoothDevice);
        leConnections.put(key, leConnection);
    }

    @Override
    public void remove(@NonNull BluetoothDevice bluetoothDevice) {
        String key = getKeyForDevice(bluetoothDevice);
        leConnections.remove(key);
    }

    /**
     * Get a constant unique key representing the device.
     *
     * @param bluetoothDevice device to create a key for
     * @return device key
     */
    private String getKeyForDevice(@NonNull BluetoothDevice bluetoothDevice) {
        return bluetoothDevice.getAddress();
    }

}
