package com.ToxicBakery.library.btle.gotenna.app.adapter;

import android.support.annotation.IntRange;
import android.support.annotation.MainThread;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic ArrayList adapter.
 */
public abstract class ArrayListAdapter<Model, Holder extends BindedViewHolder<Model>>
        extends RecyclerView.Adapter<Holder> {

    private final List<Model> modelList;

    /**
     * Create the adapter using an array list.
     */
    public ArrayListAdapter() {
        modelList = new ArrayList<>();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        Model model = getItemAt(position);
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
    public Model getItemAt(@IntRange(from = 0) int index) {
        return modelList.get(index);
    }

    /**
     * Add an item to the list and update the adapter.
     *
     * @param model to insert
     */
    @MainThread
    public void addItem(Model model) {
        modelList.add(model);
        notifyItemInserted(getItemCount() - 1);
    }

    /**
     * Remove an item from the list and update the adapter.
     *
     * @param model to remove
     */
    @MainThread
    public boolean removeItem(Model model) {
        int index = modelList.indexOf(model);
        if (index >= 0) {
            modelList.remove(index);
            notifyItemRemoved(index);
            return true;
        }

        return false;
    }

}
