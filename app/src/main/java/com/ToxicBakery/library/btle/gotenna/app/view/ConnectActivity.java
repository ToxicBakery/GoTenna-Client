package com.ToxicBakery.library.btle.gotenna.app.view;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothProfile;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.ToxicBakery.library.btle.gotenna.ILeConnection;
import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.StateMessageAdapter;
import com.ToxicBakery.library.btle.gotenna.app.presenter.ConnectActivityPresenter;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Connect to a device and monitor it's state
 */
@RequiresPresenter(ConnectActivityPresenter.class)
public class ConnectActivity extends NucleusAppCompatActivity<ConnectActivityPresenter> {

    private BluetoothDevice bluetoothDevice;
    private StateMessageAdapter adapter;

    private Button buttonDisconnect;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        bluetoothDevice = getIntent()
                .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        buttonDisconnect = (Button) findViewById(R.id.activity_connect_button_disconnect);
        buttonDisconnect.setOnClickListener(getPresenter().getDisconnectClickListener());

        recyclerView = (RecyclerView) findViewById(R.id.activity_connect_recycler_view);

        ConnectActivityPresenter presenter = getPresenter();
        adapter = presenter.getAdapter();

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, true));
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        getPresenter().setBluetoothDevice(bluetoothDevice);
    }

    /**
     * Add a message to the adapter.
     *
     * @param message to add
     */
    @MainThread
    public void onAddItem(String message) {
        if (adapter != null) {
            adapter.addItem(message);
        }
    }

    /**
     * Update the UI to reflect the state of the given connection.
     *
     * @param leConnection connection to the selected device
     */
    public void updateState(@Nullable ILeConnection leConnection) {
        final int state = leConnection == null ? BluetoothProfile.STATE_DISCONNECTED
                : leConnection.getState();

        switch (state) {
            case BluetoothProfile.STATE_CONNECTED:
                buttonDisconnect.setText(R.string.activity_connect_button_disconnect);
                buttonDisconnect.setEnabled(true);
                break;
            case BluetoothProfile.STATE_CONNECTING:
                buttonDisconnect.setText(R.string.activity_connect_button_connecting);
                buttonDisconnect.setEnabled(false);
                break;
            case BluetoothProfile.STATE_DISCONNECTING:
                buttonDisconnect.setText(R.string.activity_connect_button_disconnecting);
                buttonDisconnect.setEnabled(false);
                break;
            case BluetoothProfile.STATE_DISCONNECTED:
            default:
                buttonDisconnect.setText(R.string.activity_connect_button_connect);
                buttonDisconnect.setEnabled(true);
        }
    }

}
