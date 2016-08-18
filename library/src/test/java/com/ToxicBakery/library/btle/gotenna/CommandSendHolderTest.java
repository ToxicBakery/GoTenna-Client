package com.ToxicBakery.library.btle.gotenna;

import com.ToxicBakery.library.btle.gotenna.command.Command;
import com.ToxicBakery.library.btle.gotenna.command.CommandPriority;
import com.ToxicBakery.library.btle.gotenna.command.CommandSendHolder;

import org.junit.Test;

import static com.ToxicBakery.library.btle.gotenna.command.Command.HIGH_PRIORITY;
import static com.ToxicBakery.library.btle.gotenna.command.Command.NORMAL_PRIORITY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Validate the holder responds properly to state changes and inputs
 */
public class CommandSendHolderTest {

    @Test
    public void testHasCommandToSend() throws Exception {
        CommandSendHolder commandSendHolder = new CommandSendHolder();
        assertFalse(commandSendHolder.hasCommandToSend());
        commandSendHolder.enqueue(buildCommand(NORMAL_PRIORITY));
        assertTrue(commandSendHolder.hasCommandToSend());
    }

    @Test
    public void testNextCommand() throws Exception {
        CommandSendHolder commandSendHolder = new CommandSendHolder();
        Command command = buildCommand(NORMAL_PRIORITY);
        commandSendHolder.enqueue(command);
        assertEquals(command, commandSendHolder.nextCommand());
    }

    @Test
    public void testIsClearToSend() throws Exception {
        CommandSendHolder commandSendHolder = new CommandSendHolder();
        assertFalse(commandSendHolder.isClearToSend());
    }

    @Test
    public void testIsClearToSend1() throws Exception {
        CommandSendHolder commandSendHolder = new CommandSendHolder();
        assertFalse(commandSendHolder.isClearToSend());
        commandSendHolder.isClearToSend(true);
        assertTrue(commandSendHolder.isClearToSend());
        commandSendHolder.isClearToSend(false);
        assertFalse(commandSendHolder.isClearToSend());
    }

    @Test
    public void testEnqueue() throws Exception {
        CommandSendHolder commandSendHolder = new CommandSendHolder();
        Command command = buildCommand(HIGH_PRIORITY);
        commandSendHolder.enqueue(buildCommand(NORMAL_PRIORITY));
        commandSendHolder.enqueue(command);
        commandSendHolder.enqueue(buildCommand(NORMAL_PRIORITY));
        assertEquals(command, commandSendHolder.nextCommand());
    }

    private Command buildCommand(@CommandPriority int commandPriority) {
        return Command.builder()
                .priority(commandPriority)
                .payload(new byte[0])
                .build();
    }

}