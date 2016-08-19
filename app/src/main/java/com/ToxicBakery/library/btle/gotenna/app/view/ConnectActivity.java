package com.ToxicBakery.library.btle.gotenna.app.view;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.StateMessageAdapter;
import com.ToxicBakery.library.btle.gotenna.app.presenter.ConnectActivityPresenter;

import nucleus.view.NucleusAppCompatActivity;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Connect to a device and monitor it's state
 */
public class ConnectActivity extends NucleusAppCompatActivity<ConnectActivityPresenter> {

    private BluetoothDevice bluetoothDevice;
    private StateMessageAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        bluetoothDevice = getIntent()
                .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

        adapter = getPresenter().getAdapter();

        View buttonDisconnect = findViewById(R.id.activity_connect_button_disconnect);
        buttonDisconnect.setOnClickListener(getPresenter().getDisconnectClickListener());

        RecyclerView recyclerView
                = (RecyclerView) findViewById(R.id.activity_connect_recycler_view);

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this, VERTICAL, true));
            recyclerView.setAdapter(adapter);
        }

    }

    /**
     * Add a message to the adapter.
     *
     * @param message to add
     */
    public void onAddItem(String message) {
        adapter.addItem(message);
    }

}
