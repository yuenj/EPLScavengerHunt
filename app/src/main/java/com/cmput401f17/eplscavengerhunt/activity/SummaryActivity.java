package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
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
    private ListView summaryListView;
    private TextView usersScore;
    private Button done;

    @Inject
    GameController gameController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);
        // set up
        findViews();
        setOnDone();

        displaySummary();
        displayScore();
    }

    private void displaySummary() {
        final ArrayList<Question> questions = gameController.retrieveQuestions();
        final ArrayList<Response> responses = gameController.retrieveResponses();

        summaryAdapter = new SummaryAdapter(this, responses, questions);
        summaryListView.setAdapter(summaryAdapter);
    }

    private void displayScore() {
        final int score = gameController.retrieveScore();
        final int maxScore = gameController.retrieveMaxScore();
        usersScore.setText(Integer.toString(score) + "/" + Integer.toString(maxScore));
    }

    private void refreshDisplay() {
        summaryAdapter.notifyDataSetChanged();
    }

    private void setOnDone() {
        done.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("SummaryActivity", "going to TitleActivity");
                Intent intent = new Intent(SummaryActivity.this, TitleActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        summaryListView = (ListView) findViewById(R.id.summaryLV);
        usersScore = (TextView) findViewById(R.id.summary_total);
        done = (Button) findViewById(R.id.summary_next);
    }

    @Override
    protected void onStart() {
        super.onStart();

        refreshDisplay();
    }

    @Override
    protected void onResume() {
        super.onResume();

        refreshDisplay();
    }
}
