package com.ToxicBakery.library.btle.gotenna.command;

import android.support.annotation.VisibleForTesting;

import com.ToxicBakery.library.btle.gotenna.util.FileUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Maintainer for the message sequence number.
 */
public class SequenceNumber {

    private static final File FILE_NAME = new File("files", "SequenceNumber");

    /**
     * Single thread cached executor
     */
    private static final ExecutorService FILE_SAVE_EXECUTOR = new ThreadPoolExecutor(0, 1, 1,
            TimeUnit.MINUTES, new LinkedBlockingQueue<Runnable>());

    private static volatile int count;

    /**
     * Create the sequence manager and load the current sequence.
     */
    public SequenceNumber() {
        Runnable loadRunner = new SequenceNumberLoadRunner();
        FILE_SAVE_EXECUTOR.submit(loadRunner);
    }

    /**
     * Reset the sequenceNumber to zero.
     */
    public void reset() {
        synchronized (FILE_SAVE_EXECUTOR) {
            count = 0;

            Runnable runnable = new SequenceNumberSaveRunner(count);
            FILE_SAVE_EXECUTOR.submit(runnable);
        }
    }

    /**
     * Get the next sequence number.
     *
     * @return next sequence number
     */
    public int next() {
        synchronized (FILE_SAVE_EXECUTOR) {
            int nextSequenceNumber = ++count;

            Runnable runnable = new SequenceNumberSaveRunner(nextSequenceNumber);
            FILE_SAVE_EXECUTOR.submit(runnable);

            return nextSequenceNumber;
        }
    }

    /**
     * Runner for loading the sequence number from disk.
     */
    @VisibleForTesting
    static class SequenceNumberLoadRunner implements Runnable {

        @Override
        public void run() {
            synchronized (FILE_NAME) {
                DataInputStream inputStream = null;
                try {
                    inputStream = new DataInputStream(new FileInputStream(FILE_NAME));
                    count = inputStream.readInt();
                } catch (IOException e) {
                    Timber.e(e, "Failed to persist sequence.");
                } finally {
                    FileUtil.closeQuietly(inputStream);
                }
            }
        }

    }

    /**
     * Runner for persisting the sequence number to disk.
     */
    @VisibleForTesting
    static class SequenceNumberSaveRunner implements Runnable {

        private final int sequenceNumber;

        /**
         * Create an instance of the runner to save the given sequence.
         *
         * @param sequenceNumber to persist
         */
        public SequenceNumberSaveRunner(int sequenceNumber) {
            this.sequenceNumber = sequenceNumber;
        }

        @Override
        public void run() {
            synchronized (FILE_NAME) {
                DataOutputStream outputStream = null;
                try {
                    outputStream = new DataOutputStream(new FileOutputStream(FILE_NAME));
                    outputStream.writeInt(sequenceNumber);
                } catch (IOException e) {
                    Timber.e(e, "Failed to persist sequence.");
                } finally {
                    FileUtil.closeQuietly(outputStream);
                }
            }
        }

    }

}
