package com.ToxicBakery.library.btle.gotenna;

import com.ToxicBakery.library.btle.gotenna.command.Command;
import com.ToxicBakery.library.btle.gotenna.command.CommandPriority;
import com.ToxicBakery.library.btle.gotenna.command.CommandSendQueue;

import org.junit.Test;

import static com.ToxicBakery.library.btle.gotenna.command.Command.HIGH_PRIORITY;
import static com.ToxicBakery.library.btle.gotenna.command.Command.NORMAL_PRIORITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Validate the holder responds properly to state changes and inputs
 */
public class CommandSendQueueTest {

    @Test
    public void testHasCommandToSend() throws Exception {
        CommandSendQueue commandSendQueue = new CommandSendQueue();
        assertFalse(commandSendQueue.hasCommandToSend());
        commandSendQueue.enqueue(buildCommand(NORMAL_PRIORITY));
        assertTrue(commandSendQueue.hasCommandToSend());
    }

    @Test
    public void testNextCommand() throws Exception {
        CommandSendQueue commandSendQueue = new CommandSendQueue();
        Command command = buildCommand(NORMAL_PRIORITY);
        commandSendQueue.enqueue(command);
        assertEquals(command, commandSendQueue.nextCommand());
    }

    @Test
    public void testEnqueue() throws Exception {
        CommandSendQueue commandSendQueue = new CommandSendQueue();
        Command command = buildCommand(HIGH_PRIORITY);
        commandSendQueue.enqueue(buildCommand(NORMAL_PRIORITY));
        commandSendQueue.enqueue(command);
        commandSendQueue.enqueue(buildCommand(NORMAL_PRIORITY));
        assertEquals(command, commandSendQueue.nextCommand());
    }

    private Command buildCommand(@CommandPriority int commandPriority) {
        return Command.builder()
                .priority(commandPriority)
                .payload(new byte[0])
                .build();
    }

}