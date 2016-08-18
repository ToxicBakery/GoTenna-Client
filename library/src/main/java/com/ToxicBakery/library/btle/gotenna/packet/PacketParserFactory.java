package com.ToxicBakery.library.btle.gotenna.packet;

/**
 * Factory for building PacketParser instances.
 */
public class PacketParserFactory implements IPacketParserFactory<PacketParser> {

    @Override
    public PacketParser createParser() {
        return new PacketParser();
    }

}
