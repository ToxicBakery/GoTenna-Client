package com.ToxicBakery.library.btle.gotenna.app.presenter;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ToxicBakery.library.btle.gotenna.app.ClientApplication;
import com.ToxicBakery.library.btle.gotenna.app.scan.ScanManager;
import com.ToxicBakery.library.btle.gotenna.app.view.MainActivity;

import javax.inject.Inject;

import nucleus.presenter.RxPresenter;

/**
 * Presenter for the main activity.
 */
public class MainActivityPresenter extends RxPresenter<MainActivity> {

    @Inject
    ScanManager scanManager;

    @Override
    protected void onCreate(@Nullable Bundle savedState) {
        super.onCreate(savedState);
        ClientApplication.getAppComponent().inject(this);
    }
}
