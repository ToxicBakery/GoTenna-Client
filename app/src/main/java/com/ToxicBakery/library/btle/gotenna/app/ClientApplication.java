package com.ToxicBakery.library.btle.gotenna.app;

import android.app.Application;

import com.ToxicBakery.library.btle.gotenna.app.di.AppComponent;
import com.ToxicBakery.library.btle.gotenna.app.di.AppModule;
import com.ToxicBakery.library.btle.gotenna.app.di.DaggerAppComponent;
import com.ToxicBakery.library.btle.gotenna.app.timber.ReleaseTree;

import timber.log.Timber;
import timber.log.Timber.DebugTree;

/**
 * Client application.
 */
public class ClientApplication extends Application {

    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        installTimber();

        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    /**
     * The AppComponent instance for injection.
     *
     * @return injection instance
     */
    public static AppComponent getAppComponent() {
        return appComponent;
    }

    /**
     * Install Timber logging library.
     */
    void installTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new DebugTree());
        } else {
            Timber.plant(new ReleaseTree());
        }
    }

}
