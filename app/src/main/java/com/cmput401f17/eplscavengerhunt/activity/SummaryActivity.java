package com.cmput401f17.eplscavengerhunt.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.ScavengerHuntApplication;
import com.cmput401f17.eplscavengerhunt.controller.GameController;
import com.cmput401f17.eplscavengerhunt.custom.SummaryAdapter;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Summary;
import com.cmput401f17.eplscavengerhunt.model.Zone;

import java.util.List;

import javax.inject.Inject;

/**
 * The Summary page of the app
 * Displays a summary of the scavenger hunt results
 */
public class SummaryActivity extends AppCompatActivity {

    @Inject
    GameController gameController;

    private SummaryAdapter summaryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ScavengerHuntApplication.getInstance().getAppComponent().inject(this);

        // find views
        final ListView summaryContentLV = findViewById(R.id.LV_summary_content);
        final Button replayButton = findViewById(R.id.button_summary_replay);

        // retrieve question metadata
        final Summary summary = gameController.requestSummary();
        final List<Question> questions = summary.getQuestions();
        final List<Zone> zones = summary.getZones();
        final List<Response> responses = summary.getResponses();

        summaryAdapter = new SummaryAdapter(this, questions, zones, responses);
        summaryContentLV.setAdapter(summaryAdapter);

        // set on click listeners
        replayButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SummaryActivity.this, TitleActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        summaryAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        summaryAdapter.notifyDataSetChanged();
    }
}
