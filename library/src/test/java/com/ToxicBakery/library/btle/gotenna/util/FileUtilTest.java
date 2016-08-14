package com.ToxicBakery.library.btle.gotenna.util;

import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;

import static org.junit.Assert.assertTrue;

/**
 * Test file util operations
 */
public class FileUtilTest {

    private static final File TEST_FILE = new File("test.txt");

    @After
    public void tearDown() {
        if (TEST_FILE.exists() && !TEST_FILE.delete()) {
            throw new IllegalStateException("Failed to delete created test file");
        }
    }

    @Test
    public void testCloseQuietly() throws Exception {
        FileUtil.closeQuietly(null);
        FileUtil.closeQuietly(new FileOutputStream(TEST_FILE));
        assertTrue(TEST_FILE.exists());
    }

}