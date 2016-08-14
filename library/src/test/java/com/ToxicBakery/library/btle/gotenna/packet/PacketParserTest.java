package com.ToxicBakery.library.btle.gotenna.packet;

import com.ToxicBakery.library.btle.gotenna.command.Command;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test the parser handles real messages and bad messages.
 */
public class PacketParserTest {

    private static final int BLE_PACKET_SIZE = 20;

    /**
     * Three packets (joined as one array) from a real message sent by a device.
     */
    private static final byte[] COMPLETE_PACKET = new byte[]{
            0x10, 0x02, 0x03, (byte) 0x94, 0x06, 0x03, 0x02, 0x3f, (byte) 0xff, 0x05, 0x22,
            (byte) 0xfb, 0x0f, 0x00, 0x00, 0x00, 0x58, 0x75, 0x48, (byte) 0xe6, (byte) 0xaa, 0x10,
            0x10, 0x57, (byte) 0xab, (byte) 0xf2, (byte) 0xdd, 0x00, 0x00, 0x01, 0x01, 0x30, 0x03,
            0x01, 0x49, 0x04, 0x07, 0x74, 0x65, 0x73, 0x74, 0x69, 0x6e, 0x67, 0x2c, (byte) 0x84,
            (byte) 0xb6, (byte) 0xf8, 0x10, 0x03
    };

    @Test
    public void testTakePacket() throws Exception {
        final PacketParser packetParser = new PacketParser();
        chunkPacket(COMPLETE_PACKET, new Callback() {
            @Override
            public void chunk(byte[] chunk) throws Exception {
                packetParser.takePacket(chunk);
            }
        });
    }

    @Test
    public void testIsFinished() throws Exception {
        final PacketParser packetParser = new PacketParser();
        chunkPacket(COMPLETE_PACKET, new Callback() {
            @Override
            public void chunk(byte[] chunk) throws Exception {
                assertFalse(packetParser.isFinished());
                packetParser.takePacket(chunk);
            }
        });

        assertTrue(packetParser.isFinished());
    }

    @Test
    public void testToCommand() throws Exception {
        final PacketParser packetParser = new PacketParser();
        chunkPacket(COMPLETE_PACKET, new Callback() {
            @Override
            public void chunk(byte[] chunk) throws Exception {
                packetParser.takePacket(chunk);
            }
        });

        Command command = packetParser.toCommand();
        assertArrayEquals(COMPLETE_PACKET, command.getPayload());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testToCommand_ExceptionInvalidFirstPacket() throws Exception {
        PacketParser packetParser = new PacketParser();

        // Should fail due to invalid first packet
        packetParser.takePacket(new byte[BLE_PACKET_SIZE]);
    }

    @Test(expected = IllegalStateException.class)
    public void testToCommand_ExceptionPacketAfterComplete() throws Exception {
        final PacketParser packetParser = new PacketParser();
        chunkPacket(COMPLETE_PACKET, new Callback() {
            @Override
            public void chunk(byte[] chunk) throws Exception {
                packetParser.takePacket(chunk);
            }
        });

        // Should fail adding packet after finish
        packetParser.takePacket(new byte[BLE_PACKET_SIZE]);
    }

    interface Callback {

        void chunk(byte[] chunk) throws Exception;

    }

    /**
     * Split an input array into chunks up to {@link #BLE_PACKET_SIZE} in length.
     *
     * @param packet   source data
     * @param callback to send to
     * @throws Exception if chunk callback throws an error
     */
    static void chunkPacket(byte[] packet, Callback callback) throws Exception {
        for (int i = 0; i < packet.length; i += BLE_PACKET_SIZE) {
            int bytesRemaining = packet.length - i;
            int bytesRun = bytesRemaining < BLE_PACKET_SIZE ? bytesRemaining : BLE_PACKET_SIZE;
            byte[] chunk = new byte[bytesRun];
            System.arraycopy(packet, i, chunk, 0, bytesRun);
            callback.chunk(chunk);
        }
    }

}