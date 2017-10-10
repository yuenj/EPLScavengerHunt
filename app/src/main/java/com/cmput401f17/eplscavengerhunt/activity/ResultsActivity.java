package com.cmput401f17.eplscavengerhunt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.custom.ResultsAdapter;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;

import java.util.ArrayList;

import javax.inject.Inject;

public class ResultsActivity extends AppCompatActivity {

    private ResultsAdapter resultsAdapter;
    private ArrayList<Question> questions;
    private ArrayList<Response> responses;
    private ListView resultsListView;
    private TextView totalScore;
    private Button next;

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        resultsListView = (ListView) findViewById(R.id.resultsLV);
        totalScore = (TextView) findViewById(R.id.results_total);
        next = (Button) findViewById(R.id.results_next);

        questions = gameController.retrieveQuestions();
        responses = gameController.retrieveResponses();
        int score = gameController.retrieveTotalScore();
        totalScore.setText(Integer.toString(score) + "/5");
    }

    @Override
    protected void onStart() {
        super.onStart();

        resultsAdapter = new ResultsAdapter(this, responses, questions);
        resultsListView.setAdapter(resultsAdapter);

        resultsAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        resultsAdapter.notifyDataSetChanged();
    }
}
