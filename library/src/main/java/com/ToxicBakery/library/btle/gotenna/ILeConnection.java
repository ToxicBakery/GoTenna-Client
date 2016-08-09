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

}
