package com.ToxicBakery.library.btle.gotenna.packet;

import com.ToxicBakery.library.btle.gotenna.command.Command;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static com.ToxicBakery.library.btle.gotenna.command.Command.NORMAL_PRIORITY;
import static com.ToxicBakery.library.btle.gotenna.packet.PacketParts.PACKET_SIZE;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Test packet preparing splits and wraps as intended
 */
public class PreparedPacketTest {

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

    /**
     * Message body of unprepared command.
     */
    private static final byte[] UNPREPARED_PACKET = new byte[]{
            0x03, (byte) 0x94, 0x06, 0x03, 0x02, 0x3f, (byte) 0xff, 0x05, 0x22, (byte) 0xfb, 0x0f,
            0x00, 0x00, 0x00, 0x58, 0x75, 0x48, (byte) 0xe6, (byte) 0xaa, 0x10, 0x57, (byte) 0xab,
            (byte) 0xf2, (byte) 0xdd, 0x00, 0x00, 0x01, 0x01, 0x30, 0x03, 0x01, 0x49, 0x04, 0x07,
            0x74, 0x65, 0x73, 0x74, 0x69, 0x6e, 0x67, 0x2c, (byte) 0x84
    };

    @Test
    public void testGetPacket() throws Exception {
        PreparedPacket preparedPacket1 = new PreparedPacket(buildCommand(new byte[0]));
        assertNotNull(preparedPacket1.getPacket(0));

        PreparedPacket preparedPacket2 = new PreparedPacket(buildCommand(UNPREPARED_PACKET));
        assertNotNull(preparedPacket2.getPacket(0));
        assertNotNull(preparedPacket2.getPacket(1));
        assertNotNull(preparedPacket2.getPacket(2));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void testGetPacket1() throws Exception {
        PreparedPacket preparedPacket1 = new PreparedPacket(buildCommand(new byte[0]));
        preparedPacket1.getPacket(1);
    }

    @Test
    public void testSize() throws Exception {
        assertEquals(1, new PreparedPacket(buildCommand(new byte[0])).size());
        assertEquals(3, new PreparedPacket(buildCommand(UNPREPARED_PACKET)).size());
    }

    @Test
    public void testLength() throws Exception {
        // Length should be data length + 6 + (0x10 size * 2) for escaping
        assertEquals(6, new PreparedPacket(buildCommand(new byte[0])).length());
        assertEquals(UNPREPARED_PACKET.length + 7, new PreparedPacket(buildCommand(UNPREPARED_PACKET)).length());
    }

    @Test
    public void testPrepare() throws Exception {
        assertArrayEquals(new byte[]{0x10, 0x02, 0x00, 0x00, 0x10, 0x03}, PreparedPacket.prepare(buildCommand(new byte[0])));
        assertArrayEquals(COMPLETE_PACKET, PreparedPacket.prepare(buildCommand(UNPREPARED_PACKET)));
    }

    @Test
    public void testSplit() throws Exception {
        // Walk the source data and check each byte matches the split data
        List<byte[]> split = new ArrayList<>(PreparedPacket.split(COMPLETE_PACKET));
        for (int i = 0; i < COMPLETE_PACKET.length; i++) {
            int splitIndex = i / PACKET_SIZE;
            int subIndex = i % PACKET_SIZE;
            byte b = COMPLETE_PACKET[i];
            assertEquals(b, split.get(splitIndex)[subIndex]);
        }
    }

    Command buildCommand(byte[] payload) {
        return Command.builder()
                .priority(NORMAL_PRIORITY)
                .payload(payload)
                .build();
    }

}