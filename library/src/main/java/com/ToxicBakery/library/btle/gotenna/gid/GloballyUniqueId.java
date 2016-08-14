package com.ToxicBakery.library.btle.gotenna.gid;

import java.util.UUID;

/**
 * Utility for globally unique ids based on the Java UUID implementation. This strays from the
 * source implementation which is based on the Date object as a source of uniqueness.
 */
public class GloballyUniqueId {

    /**
     * Reserved id for emergency messages.
     */
    public static final long GID_EMERGENCY = 9999999999L;

    /**
     * Reserved id for shout messages.
     */
    public static final long GID_SHOUT = 1111111111L;

    /**
     * Create a new GID from a random UUID.
     *
     * @return a mostly unique identifier
     */
    public static long createGid() {
        long gId;
        do {
            UUID uuid = UUID.randomUUID();
            gId = uuid.getMostSignificantBits() + uuid.getLeastSignificantBits();
        } while (gId == GID_EMERGENCY || gId == GID_SHOUT);

        return gId;
    }

}
