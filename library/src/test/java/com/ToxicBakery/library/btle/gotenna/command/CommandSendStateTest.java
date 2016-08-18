package com.ToxicBakery.library.btle.gotenna.command;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Validate states reported properly
 */
public class CommandSendStateTest {

    @Test
    public void testIsComplete() throws Exception {
        Command command = Command.builder()
                .priority(Command.NORMAL_PRIORITY)
                .payload(new byte[1])
                .build();

        CommandSendState commandSendState = new CommandSendState(command);
        assertFalse(commandSendState.isComplete());
        commandSendState.getNextPacket();
        assertTrue(commandSendState.isComplete());
    }

    @Test
    public void testGetNextPacket() throws Exception {
        Command command = Command.builder()
                .priority(Command.NORMAL_PRIORITY)
                .payload(new byte[]{(byte) 0xff})
                .build();

        CommandSendState commandSendState = new CommandSendState(command);
        byte[] nextPacket = commandSendState.getNextPacket();
        assertEquals((byte) 0xff, nextPacket[2]);
    }

}