package com.ToxicBakery.library.btle.gotenna.app.presenter;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.ToxicBakery.library.btle.gotenna.GoTennaConnection;
import com.ToxicBakery.library.btle.gotenna.ILeConnection;
import com.ToxicBakery.library.btle.gotenna.app.ClientApplication;
import com.ToxicBakery.library.btle.gotenna.app.adapter.StateMessageAdapter;
import com.ToxicBakery.library.btle.gotenna.app.connection.IConnectionHolder;
import com.ToxicBakery.library.btle.gotenna.app.view.ConnectActivity;

import javax.inject.Inject;

import nucleus.presenter.Presenter;

/**
 * Presenter for the connect activity.
 */
public class ConnectActivityPresenter extends Presenter<ConnectActivity> {

    @Inject
    Context context;

    @Inject
    IConnectionHolder connectionHolder;

    private ILeConnection leConnection;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        ClientApplication.getAppComponent().inject(this);
    }

    /**
     * Create an adapter for displaying scan results.
     *
     * @return scan result adapter
     */
    public StateMessageAdapter getAdapter() {
        return new StateMessageAdapter();
    }

    /**
     * Handle disconnect request by the user.
     *
     * @return disconnect click handler
     */
    public View.OnClickListener getDisconnectClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isConnecting() || isDisconnecting()) {
                    return;
                }

                if (isConnected()) {
                    disconnect();

                    ConnectActivity activity = getView();
                    if (activity != null)
                        activity.finish();
                } else {
                    connect();
                }
            }
        };
    }

    /**
     * Set the device to be monitored and connected to by the presenter.
     *
     * @param bluetoothDevice to be monitored and connected to
     */
    @MainThread
    public void setBluetoothDevice(@NonNull BluetoothDevice bluetoothDevice) {
        leConnection = connectionHolder.get(bluetoothDevice);
        if (leConnection == null) {
            leConnection = new GoTennaConnection(bluetoothDevice);
            connectionHolder.add(bluetoothDevice, leConnection);
        }

        ConnectActivity connectActivity = getView();
        if (connectActivity != null) {
            connectActivity.updateState(leConnection);
        }
    }

    /**
     * Determine if the connection is connected.
     *
     * @return true if connected
     */
    private boolean isConnected() {
        return leConnection != null
                && leConnection.getState() == BluetoothProfile.STATE_CONNECTED;
    }

    /**
     * Determine if the connection is connecting.
     *
     * @return true if connecting
     */
    private boolean isConnecting() {
        return leConnection != null
                && leConnection.getState() == BluetoothProfile.STATE_CONNECTING;
    }

    /**
     * Determine if the connection is disconnecting.
     *
     * @return true if disconnecting
     */
    private boolean isDisconnecting() {
        return leConnection != null
                && leConnection.getState() == BluetoothProfile.STATE_DISCONNECTING;
    }

    /**
     * Attempt to connect to the selected device.
     */
    private void connect() {
        if (leConnection != null) {
            leConnection.connect(context);
        }
    }

    /**
     * Disconnect the connection if present.
     */
    private void disconnect() {
        if (leConnection != null) {
            leConnection.disconnect();
        }
    }

}
