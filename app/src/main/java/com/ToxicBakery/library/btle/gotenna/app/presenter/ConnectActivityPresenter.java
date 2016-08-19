package com.ToxicBakery.library.btle.gotenna.app.presenter;

import android.view.View;

import com.ToxicBakery.library.btle.gotenna.app.adapter.StateMessageAdapter;
import com.ToxicBakery.library.btle.gotenna.app.view.ConnectActivity;

import nucleus.presenter.Presenter;

/**
 * Presenter for the connect activity.
 */
public class ConnectActivityPresenter extends Presenter<ConnectActivity> {


    public StateMessageAdapter getAdapter() {
        return new StateMessageAdapter();
    }

    /**
     * Handle disconnect request by the user.
     *
     * @return disconnect click handler
     */
    public View.OnClickListener getDisconnectClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConnectActivity activity = getView();
                if (activity != null)
                    activity.finish();
            }
        };
    }

}
