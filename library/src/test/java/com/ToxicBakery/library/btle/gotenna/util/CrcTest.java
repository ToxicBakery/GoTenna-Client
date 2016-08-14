package com.ToxicBakery.library.btle.gotenna.util;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

/**
 * Evaluate CRC calculations match known good CRC values.
 */
public class CrcTest {

    /**
     * Three packets (joined as one array) from a real message sent by a device. This message has
     * been stripped of DLE escaping.
     */
    private static final byte[] COMPLETE_PACKET = new byte[]{
            0x03, (byte) 0x94, 0x06, 0x03, 0x02, 0x3f, (byte) 0xff, 0x05, 0x22, (byte) 0xfb, 0x0f,
            0x00, 0x00, 0x00, 0x58, 0x75, 0x48, (byte) 0xe6, (byte) 0xaa, 0x10, 0x57, (byte) 0xab,
            (byte) 0xf2, (byte) 0xdd, 0x00, 0x00, 0x01, 0x01, 0x30, 0x03, 0x01, 0x49, 0x04, 0x07,
            0x74, 0x65, 0x73, 0x74, 0x69, 0x6e, 0x67, 0x2c, (byte) 0x84, (byte) 0xb6, (byte) 0xf8
    };

    /**
     * Test complete read of input array.
     *
     * @throws Exception if crc fails
     */
    @Test
    public void testCrc16_one() throws Exception {
        // The complete data packet includes the CRC, copy the data
        int packetLength = COMPLETE_PACKET.length;
        int expectedCRC = ((COMPLETE_PACKET[packetLength - 2]) << 8
                | COMPLETE_PACKET[packetLength - 1] & 0xff) & 0xffff;

        byte[] input = new byte[packetLength - 2];
        System.arraycopy(COMPLETE_PACKET, 0, input, 0, input.length);

        int resultCrc = Crc.crc16(input) & 0xffff;
        assertEquals(expectedCRC, resultCrc);
    }

    /**
     * Test partial read of input array.
     *
     * @throws Exception if crc fails
     */
    @Test
    public void testCrc16_two() throws Exception {
        // The complete data packet includes the CRC, copy the data
        int packetLength = COMPLETE_PACKET.length;
        int expectedCRC = ((COMPLETE_PACKET[packetLength - 2]) << 8
                | COMPLETE_PACKET[packetLength - 1] & 0xff) & 0xffff;

        int resultCrc = Crc.crc16(COMPLETE_PACKET, 0, packetLength - 2) & 0xffff;
        assertEquals(expectedCRC, resultCrc);
    }

}