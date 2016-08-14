package com.ToxicBakery.library.btle.gotenna;

/**
 * Takes segments of messages and pieces them back together.
 */
public interface IMessageParser {

    /**
     * Take a piece of a message and handle it for complete message reassembly.
     *
     * @param packet received segment of the message
     * @throws Exception when parsing a received packet
     */
    void takePacket(byte[] packet) throws Exception;

}
