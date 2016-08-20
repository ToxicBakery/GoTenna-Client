package com.ToxicBakery.library.btle.gotenna.app.di;

import com.ToxicBakery.library.btle.gotenna.app.presenter.ConnectActivityPresenter;
import com.ToxicBakery.library.btle.gotenna.app.presenter.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger2 component
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    /**
     * Inject the dependencies in a MainActivityPresenter instance.
     *
     * @param target presenter instance to inject
     */
    void inject(MainActivityPresenter target);

    /**
     * Inject the dependencies in a ConnectActivityPresenter instance.
     *
     * @param target presenter instance to inject
     */
    void inject(ConnectActivityPresenter target);

}
