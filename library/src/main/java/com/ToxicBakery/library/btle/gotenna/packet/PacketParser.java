package com.ToxicBakery.library.btle.gotenna.packet;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.command.Command;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.BYTE_END;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.BYTE_MARKER;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.BYTE_START;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.DEFAULT_BUFFER_SIZE;

/**
 * Reads a received packet as part of a command.
 */
class PacketParser implements IPacketParser {

    private final ByteArrayOutputStream outputStream;

    private boolean hasFinished;

    /**
     * Create an instance of the parser.
     */
    public PacketParser() {
        // Create an initially small stream as most messages are only a few packets at 20 bytes each
        // FIXME this could likely avoid most or all GC if it was backed by a pool
        outputStream = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
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

    @Override
    public boolean isFinished() {
        return hasFinished;
    }

    @Override
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
