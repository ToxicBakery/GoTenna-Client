package com.ToxicBakery.library.btle.gotenna;

import android.bluetooth.le.ScanFilter;
import android.content.Context;
import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;
import com.ToxicBakery.library.btle.scanning.ILeScanConfiguration;
import com.ToxicBakery.library.btle.scanning.LeDiscovery;
import com.ToxicBakery.library.btle.scanning.SimpleLeScanConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * Scanner implementation for searching
 */
public class GoTennaScannerProvider implements IGoTennaScannerProvider {

    private final Context context;

    /**
     * Create a provider to scan for GoTenna devices.
     *
     * @param context application context
     */
    public GoTennaScannerProvider(@NonNull Context context) {
        this.context = context.getApplicationContext();
    }

    @Override
    public ILeScanBinder startScan(@NonNull ILeScanCallback leScanCallback) {
        ILeScanConfiguration scanConfiguration = createScanConfiguration();
        List<ScanFilter> filters = createFilters();
        return LeDiscovery.startScanning(leScanCallback, scanConfiguration, filters);
    }

    /**
     * Create the scan configuration.
     *
     * @return scan config
     */
    ILeScanConfiguration createScanConfiguration() {
        return new SimpleLeScanConfiguration(context);
    }

    /**
     * Create the scan filters for GoTenna devices.
     *
     * @return scan filters
     */
    List<ScanFilter> createFilters() {
        return new LinkedList<>();
    }

}
