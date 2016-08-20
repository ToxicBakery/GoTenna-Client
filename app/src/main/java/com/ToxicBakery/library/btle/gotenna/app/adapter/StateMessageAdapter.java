package com.ToxicBakery.library.btle.gotenna.app.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.holder.StateMessageViewHolder;

/**
 * Adapter for displaying state messages
 */
public class StateMessageAdapter extends ArrayListAdapter<String, StateMessageViewHolder> {

    @Override
    protected StateMessageViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType,
                                                        IItemClickListener<String> itemClickListener) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);

        return new StateMessageViewHolder(view);
    }

}
