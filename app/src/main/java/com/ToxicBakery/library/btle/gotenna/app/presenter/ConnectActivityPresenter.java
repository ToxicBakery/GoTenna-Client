package com.ToxicBakery.library.btle.gotenna.app.presenter;

import android.view.View;

import nucleus.presenter.Presenter;

/**
 * Presenter for the connect activity.
 */
public class ConnectActivityPresenter extends Presenter<ConnectActivityPresenter> {

    public View.OnClickListener getDisconnectClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        };
    }

}
