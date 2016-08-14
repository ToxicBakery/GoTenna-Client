package com.ToxicBakery.library.btle.gotenna.command;

import android.support.annotation.NonNull;

import com.google.auto.value.AutoValue;

/**
 * Representation of a device command.
 */
@AutoValue
public abstract class Command implements Comparable<Command> {

    /**
     * Highest priority for a command.
     */
    public static final int HIGH_PRIORITY = 1;

    /**
     * Default priority for a command
     */
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
     * Get the entire command payload.
     *
     * @return command payload
     */
    @SuppressWarnings("mutable")
    public abstract byte[] getPayload();

    /**
     * Compares this object to the specified object to determine their relative order.
     *
     * @param command the object to compare to this instance.
     * @return a negative integer if this instance is less than another; a positive integer if this
     * instance is greater than another; 0 if this instance has the same order as another
     */
    @Override
    public int compareTo(@NonNull Command command) {
        return Integer.compare(getPriority(), command.getPriority());
    }

    /**
     * Builder for creating command instances.
     */
    @AutoValue.Builder
    public abstract static class Builder {

        /**
         * Set the priority of the command.
         *
         * @param priority lower value is higher
         * @return the builder
         */
        public abstract Builder priority(@CommandPriority int priority);

        /**
         * Set the command message payload.
         *
         * @param payload message
         * @return the builder
         */
        public abstract Builder payload(byte[] payload);

        /**
         * Build the command with the current builder values.
         *
         * @return command instance representing the builder state
         */
        public abstract Command build();

    }

}
