package com.ToxicBakery.library.btle.gotenna.packet;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.IMessageParser;
import com.ToxicBakery.library.btle.gotenna.command.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Reads a received packet as part of a command.
 */
public class PacketParser implements IMessageParser {

    /**
     * First byte of a message and 2nd to last.
     */
    private static final byte BYTE_MARKER = 0x10;

    /**
     * Second byte of a message.
     */
    private static final byte BYTE_START = 0x02;

    /**
     * Last byte of a message.
     */
    private static final byte BYTE_END = 0x03;

    private final ByteArrayOutputStream outputStream;

    private boolean hasFinished;

    /**
     * Create an instance of the parser.
     */
    public PacketParser() {
        // Create an initially small stream as most messages are only a few packets at 20 bytes each
        // FIXME this could likely avoid most or all GC if it was backed by a pool
        outputStream = new ByteArrayOutputStream(512);
    }

    /**
     * Take the packet and store it to later generate a complete command representation.
     *
     * @param packet to store
     * @throws Exception if the packet could not be stored
     */
    @Override
    public void takePacket(@NonNull byte[] packet) throws Exception {
        if (isFinished()) {
            throw new IllegalStateException("Parser has already received all expected packets");
        }

        processPacket(packet);
    }

    /**
     * Determine if the parser has read all packets in the command.
     *
     * @return true if all packets have been read
     */
    public boolean isFinished() {
        return hasFinished;
    }

    /**
     * Create a command representing the payload.
     *
     * @return command for the payload
     */
    public Command toCommand() {
        if (!isFinished()) {
            throw new IllegalStateException("Can not create command from incomplete packet list");
        }

        return Command.builder()
                .priority(Command.NORMAL_PRIORITY)
                .payload(outputStream.toByteArray())
                .build();
    }

    /**
     * Process a packet and add it to the packet list.
     *
     * @param packet to add
     */
    private void processPacket(@NonNull byte[] packet) throws IOException {
        if (outputStream.size() == 0
                && (packet[0] != BYTE_MARKER || packet[1] != BYTE_START)) {

            // First packet expected but not received
            throw new IllegalArgumentException("Invalid initial packet received.");

        } else if (packet[packet.length - 2] == BYTE_MARKER
                && packet[packet.length - 1] == BYTE_END) {

            hasFinished = true;
        }

        outputStream.write(packet);
    }

}
