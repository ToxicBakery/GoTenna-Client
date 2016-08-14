package com.ToxicBakery.library.btle.gotenna.packet;

/**
 * Common elements of packets.
 */
public class PacketParts {

    /**
     * First byte of a message and 2nd to last.
     */
    static final byte BYTE_MARKER = 0x10;

    /**
     * Second byte of a message.
     */
    static final byte BYTE_START = 0x02;

    /**
     * Last byte of a message.
     */
    static final byte BYTE_END = 0x03;

    /**
     * Default size of buffers for reading and writing packets.
     */
    static final int DEFAULT_BUFFER_SIZE = 512;

    /**
     * Maximum packet size limited by LE transmission buffer.
     */
    static final int PACKET_SIZE = 20;

}
