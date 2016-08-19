package com.ToxicBakery.library.btle.gotenna.app.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ToxicBakery.library.btle.gotenna.app.R;
import com.ToxicBakery.library.btle.gotenna.app.adapter.ArrayListAdapter;
import com.ToxicBakery.library.btle.gotenna.app.adapter.BindedViewHolder;
import com.ToxicBakery.library.btle.gotenna.app.presenter.ConnectActivityPresenter;

import nucleus.view.NucleusAppCompatActivity;

import static android.support.v7.widget.LinearLayoutManager.VERTICAL;

/**
 * Connect to a device and monitor it's state
 */
public class ConnectActivity extends NucleusAppCompatActivity<ConnectActivityPresenter> {

    private Adapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);

        adapter = new Adapter();

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

    /**
     * Message adapter
     */
    private static class Adapter extends ArrayListAdapter<String, ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_message, parent, false);

            return new ViewHolder(view);
        }

    }

    /**
     * Message view holder
     */
    private static class ViewHolder extends BindedViewHolder<String> {

        private final TextView textViewMessage;

        public ViewHolder(View itemView) {
            super(itemView);

            textViewMessage = (TextView) itemView.findViewById(R.id.item_message);
        }

        @Override
        public void bind(String model) {
            textViewMessage.setText(model);
        }

    }

}
