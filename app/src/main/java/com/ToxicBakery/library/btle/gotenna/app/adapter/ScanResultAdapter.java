package com.ToxicBakery.library.btle.gotenna.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.holder.ScanResultViewHolder;
import com.ToxicBakery.library.btle.scanning.ScanResultCompat;

/**
 * Scan result adapter
 */
public class ScanResultAdapter extends ArrayListAdapter<ScanResultCompat, ScanResultViewHolder> {


    /**
     * Create the scan result using the click listener as a callback.
     *
     * @param itemClickListener click callback for scan items
     */
    public ScanResultAdapter(IItemClickListener<ScanResultCompat> itemClickListener) {
        super(itemClickListener);
    }

    @Override
    protected ScanResultViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType,
                                                      IItemClickListener<ScanResultCompat> itemClickListener) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_scan_result, parent, false);

        return new ScanResultViewHolder(view, itemClickListener);
    }

}
