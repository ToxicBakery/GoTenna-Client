package com.ToxicBakery.library.btle.gotenna;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;

import java.util.UUID;

/**
 * Base interface to scan for GoTenna devices
 */
public interface IGoTennaScannerProvider {

    /**
     * Service ID
     */
    UUID SERVICE_UUID = UUID.fromString("f0abaaee-ebfa-f96f-28da-076c35a521db");

    /**
     * Start a scan and retrieve the a binder for controlling scanning.
     *
     * @param leScanCallback callback for LE scan events
     * @return the scan binder
     */
    ILeScanBinder startScan(@NonNull ILeScanCallback leScanCallback);

}
