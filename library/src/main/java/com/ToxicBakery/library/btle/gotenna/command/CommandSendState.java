package com.ToxicBakery.library.btle.gotenna.command;

import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.packet.PreparedPackets;

import timber.log.Timber;

/**
 * Monitor the state of a command currently being sent.
 */
public class CommandSendState {

    private final PreparedPackets preparedPackets;

    private int nextPacketIndex;
    private boolean complete;

    /**
     * Create the monitor with the given command.
     *
     * @param command to prepare for sending
     */
    public CommandSendState(@NonNull Command command) {
        preparedPackets = new PreparedPackets(command);
        Timber.d("Prepared command, sending %d packets and %d total bytes",
                preparedPackets.size(),
                preparedPackets.length());
    }

    /**
     * Indicate if a command has been successfully sent.
     *
     * @return true indicates completion of sending all packets in a command
     */
    public boolean isComplete() {
        return complete;
    }

    /**
     * Get the next packet of bytes to send.
     *
     * @return bytes to send
     */
    @NonNull
    public byte[] getNextPacket() {
        // Get the packet index and increment
        int next = nextPacketIndex++;

        // Get the packet and update the completion state
        byte[] nextPacket = preparedPackets.getPacket(next);
        complete = nextPacketIndex == preparedPackets.size();
        return nextPacket;
    }

}
