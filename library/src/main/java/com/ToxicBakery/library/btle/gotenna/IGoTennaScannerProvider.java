package com.ToxicBakery.library.btle.gotenna;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.scanning.ILeScanBinder;
import com.ToxicBakery.library.btle.scanning.ILeScanCallback;

import java.util.UUID;

/**
 * Base interface to scan for GoTenna devices
 */
public interface IGoTennaScannerProvider {

    UUID CHARACTERISTIC_KEEP_ALIVE = UUID.fromString("f0ab2b18-ebfa-f96f-28da-076c35a521db");
    UUID CHARACTERISTIC_PROTOCOL_REVISION = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");
    UUID CHARACTERISTIC_WRITE = UUID.fromString("f0abb20a-ebfa-f96f-28da-076c35a521db");
    UUID CHARACTERISTIC_READ = UUID.fromString("f0abb20b-ebfa-f96f-28da-076c35a521db");

    /**
     * Start a scan and retrieve the a binder for controlling scanning.
     *
     * @param leScanCallback callback for LE scan events
     * @return the scan binder
     */
    ILeScanBinder startScan(@NonNull ILeScanCallback leScanCallback);

}
