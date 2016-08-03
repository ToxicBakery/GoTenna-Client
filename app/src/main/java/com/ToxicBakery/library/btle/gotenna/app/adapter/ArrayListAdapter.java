package com.ToxicBakery.library.btle.gotenna.app.adapter;

import android.support.annotation.CheckResult;
import android.support.annotation.IntRange;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic ArrayList adapter.
 * @param <M> model
 * @param <H> holder
 */
public abstract class ArrayListAdapter<M, H extends BindedViewHolder<M>>
        extends RecyclerView.Adapter<H> {

    private final List<M> modelList;

    /**
     * Create the adapter using an array list.
     */
    public ArrayListAdapter() {
        modelList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(H holder, int position) {
        M model = getItemAt(position);
        holder.bind(model);
    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    /**
     * Get an item from the adapter at the specified index.
     *
     * @param index of item
     * @return stored model instance
     */
    @MainThread
    public M getItemAt(@IntRange(from = 0) int index) {
        return modelList.get(index);
    }

    /**
     * Add an item to the list and update the adapter.
     *
     * @param model to insert
     */
    @MainThread
    public void addItem(M model) {
        modelList.add(model);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Remove an item from the list and update the adapter.
     *
     * @param model to remove
     */
    @MainThread
    @CheckResult
    public boolean removeItem(M model) {
        int index = modelList.indexOf(model);
        if (index >= 0) {
            modelList.remove(index);
            notifyItemRemoved(index);
            return true;
        }

        return false;
    }

}
