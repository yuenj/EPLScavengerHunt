package com.cmput401f17.eplscavengerhunt.dagger;

import com.cmput401f17.eplscavengerhunt.activity.CongratulationsActivity;
import com.cmput401f17.eplscavengerhunt.activity.DebugActivity;
import com.cmput401f17.eplscavengerhunt.activity.LocationActivity;
import com.cmput401f17.eplscavengerhunt.activity.QuestionActivity;
import com.cmput401f17.eplscavengerhunt.activity.ResultsActivity;
import com.cmput401f17.eplscavengerhunt.activity.TitleActivity;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    void inject(CongratulationsActivity congratulationsActivity);
    void inject(DebugActivity debugActivity);
    void inject(LocationActivity locationActivity);
    void inject(QuestionActivity questionActivity);
    void inject(ResultsActivity resultsActivity);
    void inject(TitleActivity titleActivity);
}
