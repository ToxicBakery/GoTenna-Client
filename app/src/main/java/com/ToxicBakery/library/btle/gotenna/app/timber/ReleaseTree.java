package com.ToxicBakery.library.btle.gotenna.app.timber;

import timber.log.Timber;

/**
 * Tree implementation that does not log anything.
 */
public class ReleaseTree extends Timber.Tree {

    @Override
    protected void log(int priority, String tag, String message, Throwable t) {
    }

}
