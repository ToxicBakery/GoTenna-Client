package com.ToxicBakery.library.btle.gotenna.command;

import android.support.annotation.NonNull;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Manage commands ensuring send priority and tracking if commands should be sent.
 */
public class CommandSendQueue {

    private final Queue<Command> commandQueue;

    /**
     * Create a command queue instance.
     */
    public CommandSendQueue() {
        commandQueue = new PriorityQueue<>();
    }

    /**
     * Check if any commands are queued to be sent.
     *
     * @return true if commands are queued.
     */
    public boolean hasCommandToSend() {
        return !commandQueue.isEmpty();
    }

    /**
     * Poll the next queued command.
     *
     * @return the next command
     */
    @NonNull
    public Command nextCommand() {
        return commandQueue.remove();
    }

    /**
     * Enqueue a command to be sent.
     *
     * @param command to be sent
     */
    public void enqueue(@NonNull Command command) {
        commandQueue.add(command);
    }

}
