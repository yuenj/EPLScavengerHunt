package com.cmput401f17.eplscavengerhunt.dagger;

import android.content.Context;

import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.DatabaseController;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;
import com.cmput401f17.eplscavengerhunt.model.ScavHuntState;

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
    public GameController provideGameController(ScavHuntState scavHuntState,
                                                DatabaseController databaseController) {
        return new GameController(scavHuntState, databaseController);
    }

    @Provides
    @Singleton
    public ScavHuntState provideScavHuntState() {
        return new ScavHuntState();
    }

    @Provides
    @Singleton
    public DatabaseController provideDatabaseController() {
        return new DatabaseController();
    }

    @Provides
    @Singleton
    public LocationController provideLocationController(ScavHuntState scavHuntState) {
        return new LocationController(scavHuntState);
    }

    @Provides
    @Singleton
    public QuestionController provideQuestionController(ScavHuntState scavHuntState) {
        return new QuestionController(scavHuntState);
    }
}
