package com.ToxicBakery.library.btle.gotenna.app.di;

import android.app.Application;
import android.content.Context;

import com.ToxicBakery.library.btle.gotenna.GoTennaScannerProvider;
import com.ToxicBakery.library.btle.gotenna.IGoTennaScannerProvider;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger Module
 */
@Module
public class AppModule {

    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    @Provides
    @Singleton
    Context providesContext() {
        return application;
    }

    @Provides
    @Singleton
    IGoTennaScannerProvider providesScanProvider(Context context) {
        return new GoTennaScannerProvider(context);
    }

}
