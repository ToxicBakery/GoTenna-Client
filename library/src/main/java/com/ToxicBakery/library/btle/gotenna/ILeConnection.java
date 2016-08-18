package com.ToxicBakery.library.btle.gotenna;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * Representation of the a BTLE connection.
 */
public interface ILeConnection {

    /**
     * Attempt to connect to the device.
     *
     * @param context application context
     */
    void connect(@NonNull Context context);

    /**
     * Disconnect the connection releasing all resources.
     */
    void disconnect();

    /**
     * Get the rssi level of the connection.
     *
     * @return rssi level
     */
    int getRssi();

    /**
     * Get the connection state.
     *
     * @return connection state
     */
    int getState();

    /**
     * Get the Gatt status of the connection.
     *
     * @return gatt status
     */
    int getStatus();

}
