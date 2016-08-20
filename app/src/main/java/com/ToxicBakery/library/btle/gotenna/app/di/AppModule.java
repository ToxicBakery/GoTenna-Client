package com.ToxicBakery.library.btle.gotenna.app.di;

import android.app.Application;
import android.content.Context;

import com.ToxicBakery.library.btle.gotenna.GoTennaScannerProvider;
import com.ToxicBakery.library.btle.gotenna.IGoTennaScannerProvider;
import com.ToxicBakery.library.btle.gotenna.app.connection.ConnectionHolder;
import com.ToxicBakery.library.btle.gotenna.app.connection.IConnectionHolder;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger Module
 */
@Module
public class AppModule {

    private final Application application;

    /**
     * Create the module for the given application
     *
     * @param application of this process
     */
    public AppModule(Application application) {
        this.application = application;
    }

    /**
     * The application class instance for the process.
     *
     * @return application instance
     */
    @Provides
    @Singleton
    Application providesApplication() {
        return application;
    }

    /**
     * Application context instance for the process.
     *
     * @return application context instance
     */
    @Provides
    @Singleton
    Context providesContext() {
        return application;
    }

    /**
     * GoTenna scan provider for creating scan instances.
     *
     * @param context application context
     * @return a configured scan provider
     */
    @Provides
    @Singleton
    IGoTennaScannerProvider providesScanProvider(Context context) {
        return new GoTennaScannerProvider(context);
    }

    @Provides
    @Singleton
    IConnectionHolder providesConnectionHolder() {
        return new ConnectionHolder();
    }

}
