package com.ToxicBakery.library.btle.gotenna.app.adapter.holder;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.TextView;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.BindedViewHolder;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

/**
 * Scan result view holder
 */
public class ScanResultViewHolder extends BindedViewHolder<ScanResultCompat> {

    private final TextView textViewName;
    private final TextView textViewAddress;

    /**
     * Create the holder from the inflated view.
     *
     * @param itemView inflated scan result view
     */
    public ScanResultViewHolder(View itemView) {
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
