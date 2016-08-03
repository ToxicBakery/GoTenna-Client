package com.ToxicBakery.library.btle.gotenna.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Holder implementation coupled to a data type.
 *
 * @param <M> value type binded by this holder
 */
public abstract class BindedViewHolder<M> extends RecyclerView.ViewHolder {

    /**
     * Create the holder with the given view.
     *
     * @param itemView to hold
     */
    public BindedViewHolder(View itemView) {
        super(itemView);
    }

    /**
     * Bind a model to the view.
     *
     * @param model to bind
     */
    public abstract void bind(M model);

}
