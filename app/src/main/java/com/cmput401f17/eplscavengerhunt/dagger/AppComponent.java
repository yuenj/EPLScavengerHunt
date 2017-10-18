package com.cmput401f17.eplscavengerhunt.dagger;

import com.cmput401f17.eplscavengerhunt.activity.CongratulationsActivity;
import com.cmput401f17.eplscavengerhunt.activity.DebugActivity;
import com.cmput401f17.eplscavengerhunt.activity.LocationActivity;
import com.cmput401f17.eplscavengerhunt.activity.QuestionActivity;
import com.cmput401f17.eplscavengerhunt.activity.ResultsActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.controller.LocationController;
import com.cmput401f17.eplscavengerhunt.controller.QuestionController;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(GameController gameController);
    void inject(QuestionController questionController);
    void inject(CongratulationsActivity congratulationsActivity);
    void inject(DebugActivity debugActivity);
    void inject(LocationActivity locationActivity);
    void inject(QuestionActivity questionActivity);
    void inject(ResultsActivity resultsActivity);
    void inject(TitleActivity titleActivity);
    void inject(GameController gameController);
    void inject(LocationController locationController);
}
