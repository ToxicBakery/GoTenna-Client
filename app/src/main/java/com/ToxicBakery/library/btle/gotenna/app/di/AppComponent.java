package com.ToxicBakery.library.btle.gotenna.app.di;

import com.ToxicBakery.library.btle.gotenna.app.presenter.MainActivityPresenter;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger2 component
 */
@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    void inject(MainActivityPresenter presenter);

}
