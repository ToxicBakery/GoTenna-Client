package com.ToxicBakery.library.btle.gotenna;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.command.Command;
import com.ToxicBakery.library.btle.gotenna.command.CommandPriorityQueue;

import java.util.Queue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Manage commands ensuring send priority and tracking if commands should be sent.
 */
public class CommandSendHolder {

    private final Queue<Command> commandQueue;
    private final AtomicBoolean clearToSend;

    /**
     * Create a command queue instance.
     */
    public CommandSendHolder() {
        commandQueue = new CommandPriorityQueue();
        clearToSend = new AtomicBoolean();
    }

    /**
     * Determine if the queue is clear to send.
     *
     * @return true indicates clear to send
     */
    public boolean isClearToSend() {
        return clearToSend.get();
    }

    /**
     * Mark the clear to send flag.
     *
     * @param flag true indicates the antenna is ready for messages
     */
    public void isClearToSend(boolean flag) {
        clearToSend.set(flag);
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
        return commandQueue.element();
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
