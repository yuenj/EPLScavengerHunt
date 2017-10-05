package com.cmput401f17.eplscavengerhunt.dagger;

import android.content.Context;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {

    private final ScavengerHuntApplication app;

    public AppModule(ScavengerHuntApplication app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public GameController provideGameController() {
        return new GameController();
    }
}