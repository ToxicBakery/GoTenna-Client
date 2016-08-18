package com.ToxicBakery.library.btle.gotenna.packet;

import com.ToxicBakery.library.btle.gotenna.command.Command;

/**
 * Takes segments of messages and pieces them back together.
 */
public interface IPacketParser {

    /**
     * Take a piece of a message and handle it for complete message reassembly.
     *
     * @param packet received segment of the message
     * @throws Exception when parsing a received packet fails
     */
    void takePacket(byte[] packet) throws Exception;

    /**
     * Determine if the parser has read all packets in the command.
     *
     * @return true if all packets have been read
     */
    boolean isFinished();

    /**
     * Create a command representing the payload.
     *
     * @return command for the payload
     */
    Command toCommand();

}
