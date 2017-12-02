package com.cmput401f17.eplscavengerhunt.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * SummaryAdapter is an adapter for setting the content for the summary page of the app
 */
public class SummaryAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private final Activity activity;
    private final List<Question> questions;
    private final List<Zone> zones;
    private final List<Response> responses;

    public SummaryAdapter(final Activity activity,
                          final List<Question> questions,
                          final List<Zone> zones,
                          final List<Response> responses) {
        this.activity = activity;
        this.questions = questions;
        this.zones = zones;
        this.responses = responses;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    // Taken from https://stackoverflow.com/questions/24832497/avoid-passing-null-as-the-view-root-need-to-resolve-layout-parameters-on-the-in
    // Accessed on 20/10/2017
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null) {
            vi = inflater.inflate(R.layout.item_summary, parent, false);
        }

        // find views
        final ImageView pictureIV = vi.findViewById(R.id.IV_summary_picture);
        final TextView zoneTV = vi.findViewById(R.id.TV_summary_zone);
        final TextView areaTV = vi.findViewById(R.id.TV_summary_area);
        final TextView answerTV = vi.findViewById(R.id.TV_summary_answer);
        final TextView responseTV = vi.findViewById(R.id.TV_summary_response);
        final RelativeLayout summaryContentRL = vi.findViewById(R.id.RL_summary_content);

        // get the question and response belonging to summary at this position
        final Question question = questions.get(position);
        final Zone zone = zones.get(position);
        final Response response = responses.get(position);

        String picture;
        if (question instanceof PicInputQuestion) {
            if (question.isSkipped()) {
                // set a default image if the user did not take a picture
                if (response.isCorrect()) {
                    picture = "ic_dolphin";
                } else {
                    picture = "ic_monkey_wrong";
                }
                final int resourceId = activity.getResources().getIdentifier(
                        picture, "drawable", activity.getPackageName());
                pictureIV.setImageDrawable(activity.getResources().getDrawable(resourceId));
            } else {
                // user took a picture
                pictureIV.setImageBitmap(response.getImageFile());
            }
        } else {
            // for the other two types of questions, set the image associated with the question
            Picasso.with(activity).load(question.getImageLink()).fit().into(pictureIV);
        }

        // set the correct answer and player's response
        answerTV.setText("Correct Answer : " + question.getAnswer());
        if (question.isSkipped()) {
            responseTV.setText("You skipped this question");
        } else {
            responseTV.setText("Your Answer: " + response.getResponseStr());
        }

        // set the zone description and theme
        zoneTV.setText(zone.getName());
        areaTV.setText(zone.getCategory());
        zoneTV.setBackgroundColor(Color.parseColor(zone.getColor()));
        summaryContentRL.setBackgroundColor(Color.parseColor(zone.getColor()));

        return vi;
    }

    public int getCount() {
        return questions.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
}
