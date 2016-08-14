package com.ToxicBakery.library.btle.gotenna.util;

import java.io.Closeable;
import java.io.IOException;

import timber.log.Timber;

/**
 * Utilities for files
 */
public final class FileUtil {

    private FileUtil() {
    }

    /**
     * Close a file stream handling null and exceptions.
     *
     * @param closeable to be closed
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) {
            return;
        }

        try {
            closeable.close();
        } catch (IOException exception) {
            Timber.e(exception, "Failed to closed stream.");
        }
    }

}
