package com.ToxicBakery.library.btle.gotenna.command;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Priority of a command to be sent to a connected device.
 */
@Retention(RetentionPolicy.SOURCE)
@IntDef({Command.HIGH_PRIORITY, Command.NORMAL_PRIORITY})
public @interface CommandPriority {
}
