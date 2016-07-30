package com.ToxicBakery.library.btle.gotenna.app.view;

import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.ArrayListAdapter;
import com.ToxicBakery.library.btle.gotenna.app.adapter.BindedViewHolder;
import com.ToxicBakery.library.btle.gotenna.app.presenter.MainActivityPresenter;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

import nucleus.factory.RequiresPresenter;
import nucleus.view.NucleusAppCompatActivity;

/**
 * Launch activity for the application displaying.
 */
@RequiresPresenter(MainActivityPresenter.class)
public class MainActivity extends NucleusAppCompatActivity<MainActivityPresenter> {

    private Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        adapter = new Adapter();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.activity_main_recycler_view);
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
        }

        findViewById(R.id.activity_main_toggle_scan)
                .setOnClickListener(getPresenter().getToggleScanClickListener());
    }

    private static class Adapter extends ArrayListAdapter<ScanResultCompat, ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_scan_result, parent, false);

            return new ViewHolder(view);
        }

    }

    private static class ViewHolder extends BindedViewHolder<ScanResultCompat> {

        private final TextView textViewName;
        private final TextView textViewAddress;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewName = (TextView) itemView.findViewById(R.id.item_scan_result_name);
            textViewAddress = (TextView) itemView.findViewById(R.id.item_scan_result_address);
        }

        @Override
        public void bind(ScanResultCompat scanResultCompat) {
            BluetoothDevice bluetoothDevice = scanResultCompat.getDevice();
            textViewName.setText(bluetoothDevice.getName());
            textViewAddress.setText(bluetoothDevice.getAddress());
        }

    }

}
