package com.ToxicBakery.library.btle.gotenna.app.scan;

import android.support.annotation.MainThread;

import com.ToxicBakery.library.btle.gotenna.IGoTennaScannerProvider;
import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ScanStatusCode;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Manager for scan instances.
 */
@Singleton
public class ScanManager {

    private final IGoTennaScannerProvider scannerProvider;
    private final IRegistrableLeScanCallback registrableLeScanCallback;

    private ILeScanBinder scanBinder;

    @Inject
    ScanManager(IGoTennaScannerProvider scannerProvider) {
        this.scannerProvider = scannerProvider;
        registrableLeScanCallback = new RegistrableLeScanCallback();
    }

    /**
     * Check if scanning is currently active.
     *
     * @return true if scanning
     */
    @MainThread
    public boolean isScanning() {
        return scanBinder != null
                && scanBinder.getErrorCode() == ScanStatusCode.SCAN_OK;
    }

    /**
     * Toggle scanning on or off based on the current state.
     */
    @MainThread
    public void toggle() {
        if (isScanning()) {
            scanBinder.stop();
            scanBinder = null;
            registrableLeScanCallback.clearResults();
        } else {
            scanBinder = scannerProvider.startScan(registrableLeScanCallback);
        }
    }


}
