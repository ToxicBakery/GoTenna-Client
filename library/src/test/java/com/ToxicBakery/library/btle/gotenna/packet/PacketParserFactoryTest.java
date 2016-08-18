package com.ToxicBakery.library.btle.gotenna.packet;

import org.junit.Test;

import static junit.framework.Assert.assertNotNull;

/**
 * Ensure the parser factory does not return null parsers
 */
public class PacketParserFactoryTest {

    @Test
    public void testCreateParser() throws Exception {
        assertNotNull(new PacketParserFactory().createParser());
    }

}