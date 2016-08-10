package com.ToxicBakery.library.btle.gotenna.command;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Representation of a device command.
 */
@AutoValue
public abstract class Command implements Comparable<Command> {

    public static final int HIGH_PRIORITY = 1;
    public static final int NORMAL_PRIORITY = 2;

    /**
     * Return a new command builder instance.
     *
     * @return command builder instance
     */
    public static Builder builder() {
        return new AutoValue_Command.Builder();
    }

    /**
     * Priority of the command.
     *
     * @return command priority
     */
    @CommandPriority
    public abstract int getPriority();

    /**
     * Builder for creating command instances.
     */
    @AutoValue.Builder
    public static abstract class Builder {

        /**
         * Set the priority of the command.
         *
         * @param priority lower value is higher
         * @return the builder
         */
        public abstract Builder priority(@CommandPriority int priority);

        /**
         * Build the command with the current builder values.
         *
         * @return command instance representing the builder state
         */
        public abstract Command build();

    }

    @Override
    public int compareTo(@NonNull Command command) {
        return Integer.compare(getPriority(), command.getPriority());
    }

}
