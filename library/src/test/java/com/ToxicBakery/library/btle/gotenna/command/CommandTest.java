package com.ToxicBakery.library.btle.gotenna.command;

import org.junit.Assert;

import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Test the command model.
 */
public class CommandTest {

    @org.junit.Test
    public void testGetPriority() throws Exception {
        Assert.assertEquals(Command.NORMAL_PRIORITY, buildCommand(Command.NORMAL_PRIORITY).getPriority());
        Assert.assertEquals(Command.HIGH_PRIORITY, buildCommand(Command.HIGH_PRIORITY).getPriority());
    }

    @org.junit.Test
    public void testCompareTo() throws Exception {
        Queue<Command> commandQueue = new PriorityQueue<>();
        commandQueue.add(buildCommand(Command.NORMAL_PRIORITY));
        commandQueue.add(buildCommand(Command.HIGH_PRIORITY));
        commandQueue.add(buildCommand(Command.NORMAL_PRIORITY));

        Assert.assertEquals(Command.HIGH_PRIORITY, commandQueue.remove().getPriority());
        Assert.assertEquals(Command.NORMAL_PRIORITY, commandQueue.remove().getPriority());
        Assert.assertEquals(Command.NORMAL_PRIORITY, commandQueue.remove().getPriority());
    }

    /**
     * Build a command instance with an empty payload.
     *
     * @param priority priority to test
     * @return command with given priority
     */
    Command buildCommand(@CommandPriority int priority) {
        return Command.builder()
                .priority(priority)
                .payload(new byte[0])
                .build();
    }

}