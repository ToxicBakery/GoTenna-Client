package com.ToxicBakery.library.btle.gotenna.command;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Validate command send queue reports correct states and queue priorities
 */
public class CommandSendQueueTest {

    @Test
    public void testHasCommandToSend() throws Exception {
        Command command = buildCommand(new byte[1]);

        CommandSendQueue commandSendQueue = new CommandSendQueue();
        assertFalse(commandSendQueue.hasCommandToSend());
        commandSendQueue.enqueue(command);
        assertTrue(commandSendQueue.hasCommandToSend());
    }

    @Test
    public void testNextCommand() throws Exception {
        Command command = buildCommand(new byte[1]);
        CommandSendQueue commandSendQueue = new CommandSendQueue();
        commandSendQueue.enqueue(command);
        assertEquals(command, commandSendQueue.nextCommand());
    }

    @Test(expected = NoSuchElementException.class)
    public void testNextCommand1() throws Exception {
        CommandSendQueue commandSendQueue = new CommandSendQueue();
        commandSendQueue.nextCommand();
    }

    @Test
    public void testEnqueue() throws Exception {
        CommandSendQueue commandSendQueue = new CommandSendQueue();
        commandSendQueue.enqueue(buildCommand(new byte[1]));
        commandSendQueue.enqueue(buildCommand(new byte[2]));
        commandSendQueue.enqueue(buildCommand(new byte[3]));

        assertNotNull(commandSendQueue.nextCommand());
        assertNotNull(commandSendQueue.nextCommand());
        assertNotNull(commandSendQueue.nextCommand());
    }

    @Test
    public void testEnqueue1() throws Exception {

        CommandSendQueue commandSendQueue = new CommandSendQueue();
        commandSendQueue.enqueue(buildCommand(new byte[1]));
        commandSendQueue.enqueue(buildCommand(Command.HIGH_PRIORITY, new byte[2]));
        commandSendQueue.enqueue(buildCommand(new byte[3]));

        assertEquals(Command.HIGH_PRIORITY, commandSendQueue.nextCommand().getPriority());
        assertEquals(Command.NORMAL_PRIORITY, commandSendQueue.nextCommand().getPriority());
        assertEquals(Command.NORMAL_PRIORITY, commandSendQueue.nextCommand().getPriority());
    }

    static Command buildCommand(byte[] payload) {
        return buildCommand(Command.NORMAL_PRIORITY, payload);
    }

    static Command buildCommand(@CommandPriority int commandPriority,
                                byte[] payload) {

        return Command.builder()
                .priority(commandPriority)
                .payload(payload)
                .build();
    }

}