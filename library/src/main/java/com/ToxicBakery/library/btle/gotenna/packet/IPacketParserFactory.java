package com.ToxicBakery.library.btle.gotenna.packet;

/**
 * Factory for building message parsers
 *
 * @param <P> type of {@link IPacketParser} instance to be returned by the factory
 */
public interface IPacketParserFactory<P extends IPacketParser> {

    /**
     * Create a new parser for packet parsing messages.
     *
     * @return a parser for processing incoming packets
     */
    P createParser();

}
