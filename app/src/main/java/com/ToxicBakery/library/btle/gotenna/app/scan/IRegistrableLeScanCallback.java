package com.ToxicBakery.library.btle.gotenna.app.scan;

import com.ToxicBakery.library.btle.scanning.ILeScanCallback;

/**
 * Supports register and unregister of scan callbacks.
 */
public interface IRegistrableLeScanCallback extends ILeScanCallback {

    /**
     * Register a scan callback. If a scan is ongoing, registering a new listener will receive all
     * found devices excluding any lost devices.
     *
     * @param leScanCallback to register
     */
    void registerCallback(ILeScanCallback leScanCallback);

    /**
     * Unregister a scan callback.
     *
     * @param leScanCallback to unregister
     */
    void unregisterCallback(ILeScanCallback leScanCallback);

    /**
     * Clear all stored results.
     */
    void clearResults();

}
