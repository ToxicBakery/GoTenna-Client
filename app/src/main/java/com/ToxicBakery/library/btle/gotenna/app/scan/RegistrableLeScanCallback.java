package com.ToxicBakery.library.btle.gotenna.app.scan;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * Callback wrapper for passing scan results to any number of listeners.
 */
class RegistrableLeScanCallback implements IRegistrableLeScanCallback {

    private final List<ILeScanCallback> callbackList;
    private final Set<ScanResultCompat> scanResults;

    /**
     * Instantiate the callback implementation.
     */
    public RegistrableLeScanCallback() {
        callbackList = new LinkedList<>();
        scanResults = new HashSet<>();
    }

    @Override
    public void onDeviceFound(@NonNull ScanResultCompat leScanCallback) {
        synchronized (scanResults) {
            scanResults.add(leScanCallback);
        }

        synchronized (callbackList) {
            for (ILeScanCallback scanCallback : callbackList) {
                scanCallback.onDeviceFound(leScanCallback);
            }
        }
    }

    @Override
    public void onDeviceLost(@NonNull ScanResultCompat leScanCallback) {
        synchronized (scanResults) {
            scanResults.remove(leScanCallback);
        }

        synchronized (callbackList) {
            for (ILeScanCallback scanCallback : callbackList) {
                scanCallback.onDeviceLost(leScanCallback);
            }
        }
    }

    @Override
    public void registerCallback(ILeScanCallback leScanCallback) {
        synchronized (callbackList) {
            callbackList.add(leScanCallback);

            synchronized (scanResults) {
                for (ScanResultCompat scanResult : scanResults) {
                    leScanCallback.onDeviceFound(scanResult);
                }
            }
        }
    }

    @Override
    public void unregisterCallback(ILeScanCallback leScanCallback) {
        synchronized (callbackList) {
            callbackList.remove(leScanCallback);
        }
    }

    @Override
    public void clearResults() {
        synchronized (scanResults) {
            scanResults.clear();
        }
    }

}
