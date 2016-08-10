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
        Assert.assertEquals(Command.NORMAL_PRIORITY, Command.builder().priority(Command.NORMAL_PRIORITY).build().getPriority());
        Assert.assertEquals(Command.HIGH_PRIORITY, Command.builder().priority(Command.HIGH_PRIORITY).build().getPriority());
    }

    @org.junit.Test
    public void testCompareTo() throws Exception {
        Queue<Command> commandQueue = new PriorityQueue<>();
        commandQueue.add(Command.builder().priority(Command.NORMAL_PRIORITY).build());
        commandQueue.add(Command.builder().priority(Command.HIGH_PRIORITY).build());
        commandQueue.add(Command.builder().priority(Command.NORMAL_PRIORITY).build());

        Command command = commandQueue.poll();
        Assert.assertEquals(Command.HIGH_PRIORITY, command.getPriority());
    }

}