package com.ToxicBakery.library.btle.gotenna.app.adapter;

import android.support.annotation.CheckResult;
import android.support.annotation.IntRange;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic ArrayList adapter.
 *
 * @param <M> model
 * @param <H> holder
 */
public abstract class ArrayListAdapter<M, H extends BindedViewHolder<M>>
        extends RecyclerView.Adapter<H> {

    private final List<M> modelList;
    private final IItemClickListener<M> itemClickListener;

    /**
     * Create the adapter using an array list.
     */
    public ArrayListAdapter() {
        this(new NullItemClickListener<M>());
    }

    /**
     * Create the adapter using an array list.
     *
     * @param itemClickListener click handler for items.
     */
    public ArrayListAdapter(@NonNull IItemClickListener<M> itemClickListener) {
        modelList = new ArrayList<>();
        this.itemClickListener = itemClickListener;
    }

    /**
     * @param parent            The ViewGroup into which the new View will be added after it is
     *                          bound to an adapter position
     * @param viewType          The view type of the new View
     * @param itemClickListener The handler for view clicks
     * @return A new ViewHolder that holds a View of the given view type
     */
    protected abstract H onCreateViewHolder(ViewGroup parent,
                                            int viewType,
                                            IItemClickListener<M> itemClickListener);

    @Override
    public final H onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolder(parent, viewType, itemClickListener);
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
     * @return true if an item was removed
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

    /**
     * No op click listener.
     *
     * @param <M> model type
     */
    private static final class NullItemClickListener<M> implements IItemClickListener<M> {
        @Override
        public void onItemClicked(M item) {
        }
    }

    /**
     * Listener callback for click events.
     *
     * @param <M> model type
     */
    public interface IItemClickListener<M> {

        /**
         * Callback for selection of an item in a display list.
         *
         * @param item bound item on view
         */
        void onItemClicked(M item);

    }

}
