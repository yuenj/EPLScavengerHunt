package com.cmput401f17.eplscavengerhunt.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.custom.SummaryAdapter;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;

import java.util.ArrayList;

import javax.inject.Inject;

public class SummaryActivity extends AppCompatActivity {

    private SummaryAdapter summaryAdapter;
    private ArrayList<Question> questions;
    private ArrayList<Response> responses;
    private ListView resultsListView;
    private TextView usersScore;
    private Button next;

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        resultsListView = (ListView) findViewById(R.id.summaryLV);
        usersScore = (TextView) findViewById(R.id.summary_total);
        next = (Button) findViewById(R.id.summary_next);

        questions = gameController.retrieveQuestions();
        responses = gameController.retrieveResponses();
        int score = gameController.retrieveScore();
        int maxScore = gameController.retrieveMaxScore();
        usersScore.setText(Integer.toString(score) + "/" + Integer.toString(maxScore));
    }

    @Override
    protected void onStart() {
        super.onStart();

        summaryAdapter = new SummaryAdapter(this, responses, questions);
        resultsListView.setAdapter(summaryAdapter);

        summaryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();

        summaryAdapter.notifyDataSetChanged();
    }
}
