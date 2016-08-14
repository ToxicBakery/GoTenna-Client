package com.ToxicBakery.library.btle.gotenna;

import java.util.UUID;

/**
 * Device characteristic identifiers.
 */
public final class Characteristics {

    /**
     * Keep alive
     */
    public static final UUID CHARACTERISTIC_KEEP_ALIVE
            = UUID.fromString("f0ab2b18-ebfa-f96f-28da-076c35a521db");

    /**
     * Version
     */
    public static final UUID CHARACTERISTIC_PROTOCOL_REVISION
            = UUID.fromString("00002a26-0000-1000-8000-00805f9b34fb");

    /**
     * Output channel
     */
    public static final UUID CHARACTERISTIC_WRITE
            = UUID.fromString("f0abb20a-ebfa-f96f-28da-076c35a521db");

    /**
     * Input channel
     */
    public static final UUID CHARACTERISTIC_READ
            = UUID.fromString("f0abb20b-ebfa-f96f-28da-076c35a521db");

    private Characteristics() {
    }

}
