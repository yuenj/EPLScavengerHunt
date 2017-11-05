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
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.List;

import javax.inject.Inject;

/**
 * Displays the users results and overall score
 * Also displays which questions a user answered right or wrong
 */
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

        final Summary summary = gameController.requestSummary();
        Log.i("SUMMARY:", summary.getQuestions().toString());

        // set up
        findViews();
        setOnDone();

        displaySummary(summary);
        displayScore(summary);
    }

    /**
     * Displays the questions and responses
     */
    private void displaySummary(final Summary summary) {
        final List<Question> questions = summary.getQuestions();
        final List<Zone> zones = summary.getZones();

        summaryAdapter = new SummaryAdapter(this, questions, zones);
        summaryListView.setAdapter(summaryAdapter);
    }

    /**
     * Displays the users score with respect to the
     * total questions played
     */
    private void displayScore(final Summary summary) {
        final int score = summary.getScore();
        final int maxScore = summary.getNumQuestions();

        // TODO display score and maxScore in two different textviews
        usersScore.setText(Integer.toString(score) + "/" + Integer.toString(maxScore));
    }

    private void refreshDisplay() {
        summaryAdapter.notifyDataSetChanged();
    }

    /**
     * User click leads them to TitleActivity
      */
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
        summaryListView = findViewById(R.id.summary_lv);
        usersScore = findViewById(R.id.summary_total_text_view);
        done = findViewById(R.id.summary_next_button);
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
