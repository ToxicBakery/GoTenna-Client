package com.ToxicBakery.library.btle.gotenna.packet;

import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.ToxicBakery.library.btle.gotenna.command.Command;
import com.ToxicBakery.library.btle.gotenna.util.Crc;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.BYTE_END;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.BYTE_MARKER;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.BYTE_START;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.DEFAULT_BUFFER_SIZE;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.PACKET_SIZE;

/**
 * Write packets for a given command.
 */
public class PreparedPacket {

    private final List<byte[]> packets;
    private final int length;

    /**
     * Create prepared packets representing the command.
     *
     * @param command to prepare
     */
    public PreparedPacket(@NonNull Command command) {
        byte[] preparedCommand = prepare(command);
        length = preparedCommand.length;
        packets = new ArrayList<>(split(preparedCommand));
    }

    /**
     * Get the packet at the given index.
     *
     * @param index of stored packet
     * @return stored packet
     * @throws IndexOutOfBoundsException if index is &lt;0 or &gt;preparedPacket.size()
     */
    public byte[] getPacket(int index) {
        return packets.get(index);
    }

    /**
     * The number of packets representing the command.
     *
     * @return stored packet size
     */
    public int size() {
        return packets.size();
    }

    /**
     * The total number of bytes of the prepared packets.
     *
     * @return total bytes
     */
    public int length() {
        return length;
    }

    /**
     * Prepare a command with CRC encoding, marker escaping, and packet markers
     *
     * @param command to prepare
     * @return prepared command as a byte array
     */
    @VisibleForTesting
    static byte[] prepare(Command command) {
        byte[] source = command.getPayload();
        int crc = Crc.crc16(source);
        ByteArrayOutputStream baos = new ByteArrayOutputStream(DEFAULT_BUFFER_SIZE);
        baos.write(BYTE_MARKER);
        baos.write(BYTE_START);

        for (byte b : source) {
            // Duplicate BYTE_MARKER instance as escape characters
            if (b == BYTE_MARKER) {
                baos.write(BYTE_MARKER);
            }
            baos.write(b);
        }

        baos.write((crc >> 8) & 0xff);
        baos.write(crc & 0xff);
        baos.write(BYTE_MARKER);
        baos.write(BYTE_END);
        return baos.toByteArray();
    }

    /**
     * Split the prepared command to a list of packets.
     *
     * @param preparedCommand prepared command data
     */
    @VisibleForTesting
    static List<byte[]> split(byte[] preparedCommand) {
        List<byte[]> packets = new LinkedList<>();
        for (int i = 0, j = preparedCommand.length; i < j; i += PACKET_SIZE) {
            int bytesRemaining = j - i;
            int run = bytesRemaining > PACKET_SIZE ? PACKET_SIZE : bytesRemaining;
            byte[] runBytes = new byte[run];
            System.arraycopy(preparedCommand, i, runBytes, 0, run);
            packets.add(runBytes);
        }

        return packets;
    }

}
