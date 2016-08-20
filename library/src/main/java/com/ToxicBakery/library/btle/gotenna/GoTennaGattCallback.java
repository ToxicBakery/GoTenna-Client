package com.ToxicBakery.library.btle.gotenna;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.support.annotation.NonNull;

import com.ToxicBakery.library.btle.gotenna.command.Command;
import com.ToxicBakery.library.btle.gotenna.command.CommandSendQueue;
import com.ToxicBakery.library.btle.gotenna.command.CommandSendState;
import com.ToxicBakery.library.btle.gotenna.packet.IPacketParser;
import com.ToxicBakery.library.btle.gotenna.packet.IPacketParserFactory;
import com.ToxicBakery.library.btle.gotenna.packet.PacketParserFactory;

import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.UUID;

import timber.log.Timber;

import static com.ToxicBakery.library.btle.gotenna.Characteristics.CHARACTERISTIC_KEEP_ALIVE;
import static com.ToxicBakery.library.btle.gotenna.Characteristics.CHARACTERISTIC_PROTOCOL_REVISION;
import static com.ToxicBakery.library.btle.gotenna.Characteristics.CHARACTERISTIC_READ;
import static com.ToxicBakery.library.btle.gotenna.Characteristics.CHARACTERISTIC_WRITE;

/**
 * Callback implementation for handling state.
 */
public class GoTennaGattCallback extends BluetoothGattCallback {

    private final IPacketParserFactory messageParserFactory;
    private final Queue<BluetoothGattDescriptor> descriptorWriteQueue;
    private final CommandSendQueue commandSendQueue;

    private BluetoothGattCharacteristic characteristicKeepAlive;
    private BluetoothGattCharacteristic characteristicProtocolRev;
    private BluetoothGattCharacteristic characteristicRead;
    private BluetoothGattCharacteristic characteristicWrite;
    private CommandSendState commandSendState;
    private IPacketParser currentMessageParser;
    private boolean clearToSend;
    private int lastRssi;
    private int lastStatus;
    private int lastState;
    private String protocolRevision;

    /**
     * Create the callback using the default parsing and command holder.
     */
    public GoTennaGattCallback() {
        this(new PacketParserFactory(), new CommandSendQueue());
    }

    /**
     * Create the callback using the given parser for handling received messages.
     *
     * @param messageParserFactory builder of message parsers
     * @param commandSendQueue     manager for queued commands
     */
    public GoTennaGattCallback(@NonNull IPacketParserFactory messageParserFactory,
                               @NonNull CommandSendQueue commandSendQueue) {

        this.messageParserFactory = messageParserFactory;
        descriptorWriteQueue = new LinkedList<>();
        this.commandSendQueue = commandSendQueue;
    }

    @Override
    public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
        super.onConnectionStateChange(gatt, status, newState);

        if (newState == BluetoothGatt.STATE_CONNECTED
                && status == BluetoothGatt.GATT_SUCCESS) {

            // Request services
            gatt.discoverServices();
            Timber.d("Connected to device.");
        } else if (newState == BluetoothGatt.STATE_DISCONNECTING) {
            Timber.d("Device disconnecting.");
        } else if (newState == BluetoothGatt.STATE_DISCONNECTED) {
            Timber.d("Device disconnected");
            descriptorWriteQueue.clear();
        }

        setLastStatus(gatt, status);
        lastState = newState;
    }

    @Override
    public void onServicesDiscovered(BluetoothGatt gatt, int status) {
        super.onServicesDiscovered(gatt, status);

        List<BluetoothGattService> gattServices = gatt.getServices();
        for (BluetoothGattService gattService : gattServices) {
            for (BluetoothGattCharacteristic characteristic : gattService.getCharacteristics()) {
                UUID characteristicUuid = characteristic.getUuid();
                if (CHARACTERISTIC_KEEP_ALIVE.equals(characteristicUuid)) {
                    gatt.setCharacteristicNotification(characteristic, true);
                    characteristicKeepAlive = characteristic;
                    Timber.d("Processed keep alive characteristic %s", characteristic.toString());
                } else if (CHARACTERISTIC_PROTOCOL_REVISION.equals(characteristicUuid)) {
                    characteristicProtocolRev = characteristic;
                    Timber.d("Processed protocol rev characteristic %s", characteristic.toString());
                } else if (CHARACTERISTIC_READ.equals(characteristicUuid)) {
                    gatt.setCharacteristicNotification(characteristic, true);
                    characteristicRead = characteristic;
                    Timber.d("Processed read characteristic %s", characteristic.toString());
                } else if (CHARACTERISTIC_WRITE.equals(characteristicUuid)) {
                    gatt.setCharacteristicNotification(characteristic, true);
                    characteristicWrite = characteristic;
                    Timber.d("Processed write characteristic %s", characteristic.toString());
                } else {
                    Timber.e("Found unknown characteristic: %s", characteristicUuid.toString());
                }
            }
        }

        if (characteristicKeepAlive == null
                || characteristicProtocolRev == null
                || characteristicRead == null
                || characteristicWrite == null) {

            Timber.e("Failed to retrieve all characteristics");
            gatt.disconnect();
        } else {
            Timber.d("Read all characteristics.");
            gatt.readCharacteristic(characteristicProtocolRev);
        }

        setLastStatus(gatt, status);
    }

    @Override
    public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicRead(gatt, characteristic, status);

        if (characteristic.getUuid().equals(CHARACTERISTIC_PROTOCOL_REVISION)) {
            protocolRevision = characteristic.getStringValue(0);
            Timber.d("Protocol revision determined to be %s", protocolRevision);
            setNotifyOrIndicateOnCharacteristic(gatt, characteristicRead);
            setNotifyOrIndicateOnCharacteristic(gatt, characteristicWrite);
            setNotifyOrIndicateOnCharacteristic(gatt, characteristicKeepAlive);
        }

        setLastStatus(gatt, status);
    }

    @Override
    public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
        super.onCharacteristicWrite(gatt, characteristic, status);

        setLastStatus(gatt, status);
    }

    @Override
    public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        super.onCharacteristicChanged(gatt, characteristic);

        if (characteristic == characteristicRead) {
            try {
                takePacket(characteristic.getValue());
            } catch (Exception e) {
                throw new IllegalStateException(e);
            }
        } else if (characteristic == characteristicWrite) {
            onKeepAliveReceived(characteristic.getValue());
        }
    }

    @Override
    public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
        super.onDescriptorWrite(gatt, descriptor, status);

        setLastStatus(gatt, status);

        descriptorWriteQueue.remove(descriptor);
        writeNextDescriptor(gatt);
    }

    @Override
    public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        super.onReadRemoteRssi(gatt, rssi, status);
        lastRssi = rssi;
        setLastStatus(gatt, status);
    }

    /**
     * Last reported gatt rssi level
     *
     * @return rssi level
     */
    public int getLastRssi() {
        return lastRssi;
    }

    /**
     * Last reported Gatt status.
     *
     * @return gatt status
     */
    public int getLastStatus() {
        return lastStatus;
    }

    /**
     * The last reported Gatt state.
     *
     * @return gatt state
     */
    public int getLastState() {
        return lastState;
    }

    /**
     * Set notification or indication flag on the characteristic.
     *
     * @param gatt           to write to
     * @param characteristic to flag for notification or indication
     */
    private void setNotifyOrIndicateOnCharacteristic(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
        for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
            byte[] value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE;
            if (characteristic == characteristicRead) {
                value = BluetoothGattDescriptor.ENABLE_INDICATION_VALUE;
            }
            descriptor.setValue(value);
            descriptorWriteQueue.add(descriptor);
        }

        writeNextDescriptor(gatt);
    }

    /**
     * Take a received packet and process it in the associated parser or create a new one.
     *
     * @param input packet
     * @throws Exception when parsing a received packet fails
     */
    void takePacket(byte[] input) throws Exception {
        currentMessageParser.takePacket(input);
        if (currentMessageParser.isFinished()) {
            Charset charset = Charset.forName("UTF-8");
            Timber.d("Received command: %s",
                    new String(currentMessageParser.toCommand().getPayload(), charset));
            currentMessageParser = messageParserFactory.createParser();
        }
    }

    /**
     * Set the last device status.
     *
     * @param gatt   device gatt
     * @param status new connection status
     */
    void setLastStatus(BluetoothGatt gatt, int status) {
        Timber.d("Changing status from %d to %d", lastStatus, status);
        if (lastStatus != BluetoothGatt.STATE_DISCONNECTED
                && lastStatus != BluetoothGatt.STATE_DISCONNECTING
                && status == BluetoothGatt.STATE_DISCONNECTED
                || status == BluetoothGatt.STATE_DISCONNECTING) {

            Timber.d("Disconnecting due to status %d", status);
            gatt.disconnect();
        }

        lastStatus = status;
    }

    /**
     * Write the next queued descriptor.
     *
     * @param gatt to write to
     */
    void writeNextDescriptor(BluetoothGatt gatt) {
        if (descriptorWriteQueue.size() > 0) {
            gatt.writeDescriptor(descriptorWriteQueue.element());
        }
    }

    /**
     * Indicates keep alive response was received.
     *
     * @param payload received keep alive data
     */
    void onKeepAliveReceived(byte[] payload) {
        Timber.d("Received keepAlive payload: %d bytes.", payload.length);
        clearToSend = true;
        sendNextCommand();
    }

    /**
     * Tracks the state of the connection to determine if a command can be sent currently.
     *
     * @return true if no commands are being sent an the connection is capable of sending
     */
    boolean canSend() {
        // TODO Determine if connection state needs to be tracked here also
        return commandSendState == null
                || commandSendState.isComplete();
    }

    /**
     * Request the next command to be sent.
     */
    void sendNextCommand() {
        if (clearToSend
                && commandSendQueue.hasCommandToSend()
                && canSend()) {

            Command command = commandSendQueue.nextCommand();
            commandSendState = new CommandSendState(command);
        }
    }

}
