package com.ToxicBakery.library.btle.gotenna.app.adapter.holder;

import android.view.View;
import android.widget.TextView;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.BindedViewHolder;

/**
 * Message view holder
 */
public class StateMessageViewHolder extends BindedViewHolder<String> {

    private final TextView textViewMessage;

    /**
     * Create the holder from the inflated view.
     *
     * @param itemView inflated scan result view
     */
    public StateMessageViewHolder(View itemView) {
        super(itemView);

        textViewMessage = (TextView) itemView.findViewById(R.id.item_message);
    }

    @Override
    public void bind(String model) {
        textViewMessage.setText(model);
    }

}
