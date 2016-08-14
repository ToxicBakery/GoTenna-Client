package com.ToxicBakery.library.btle.gotenna.gid;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test that generally unique ids are created.
 * <p/>
 * It is worth noting I fully understand this is not a perfect test. The test is only intended to
 * validate that mostly unique numbers are generated. Optimally the devices would use 128 bits of
 * uniqueness in a perfect world to match the generally accepted uniqueness of the UUID
 * implementation.
 */
public class GloballyUniqueIdTest {

    /**
     * Failure acceptance rate. In testing with JDK8 on OSX failure failures are typically 0.
     */
    private static final double THRESHOLD = 0.000001;

    @Test
    public void testCreateGid() throws Exception {
        Set<Long> ids = new HashSet<>();
        long iterations = (long) Math.pow(2, 17);
        int failures = 0;
        for (long i = 0; i < iterations; i++) {
            long gid = GloballyUniqueId.createGid();

            if (!ids.add(gid)) {
                ++failures;
            }
        }

        assertFalse(ids.contains(GloballyUniqueId.GID_EMERGENCY));
        assertFalse(ids.contains(GloballyUniqueId.GID_SHOUT));
        assertTrue("Failures: " + failures + " Desired: " + (iterations * THRESHOLD),
                failures < iterations * THRESHOLD);
    }

}