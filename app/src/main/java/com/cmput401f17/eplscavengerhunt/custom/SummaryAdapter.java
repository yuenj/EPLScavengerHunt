package com.cmput401f17.eplscavengerhunt.custom;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.model.MultipleChoiceQuestion;
import com.cmput401f17.eplscavengerhunt.model.PicInputQuestion;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.squareup.picasso.Picasso;

import java.util.List;

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

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            // https://stackoverflow.com/questions/24832497/avoid-passing-null-as-the-view-root-need-to-resolve-layout-parameters-on-the-in
            // 20/10/2017
            vi = inflater.inflate(R.layout.item_summary, parent, false);

        final ImageView pictureIV = vi.findViewById(R.id.IV_summary_picture);
        final TextView zoneTV = vi.findViewById(R.id.TV_summary_zone);
        final TextView areaTV = vi.findViewById(R.id.TV_summary_area);
        final TextView answerTV = vi.findViewById(R.id.TV_summary_answer);
        final TextView responseTV = vi.findViewById(R.id.TV_summary_response);
        final RelativeLayout summaryContentRL = vi.findViewById(R.id.RL_summary_content);

        final Question question = questions.get(position);
        final Zone zone = zones.get(position);
        final Response response = responses.get(position);

        // TODO: Set this as the user's photo
        String picture;
        if (question instanceof PicInputQuestion) {

            // Set to monkey or dolphin
            if (question.isSkipped()) {
                if (response.isCorrect()) {
                    picture = "ic_dolphin";
                } else {
                    picture = "ic_monkey_wrong";
                }
                final int resourceId = activity.getResources().getIdentifier(
                        picture, "drawable", activity.getPackageName());
                pictureIV.setImageDrawable(activity.getResources().getDrawable(resourceId));
            } else {
                pictureIV.setImageBitmap(response.getImageFile());
            }
        } else {
            Picasso.with(activity).load(question.getImageLink()).fit().into(pictureIV);
        }
        /*
        if (question.getImageLink().isEmpty()) {
            if (response.isCorrect()) {
                picture = "ic_dolphin";
            } else {
                picture = "ic_monkey_wrong";
            }
            final int resourceId = activity.getResources().getIdentifier(
                    picture, "drawable", activity.getPackageName());
            pictureIV.setImageDrawable(activity.getResources().getDrawable(resourceId));
        } else {
            Picasso.with(activity).load(question.getImageLink()).fit().into(pictureIV);
        } */

        // Gets the full answer instead of just 'A' or 'C'

        answerTV.setText("Correct Answer : " + question.getAnswer());
        
        if(question.isSkipped()){
            responseTV.setText("You skipped this question");
        }
        else {
            responseTV.setText("Your Answer : " + response.getResponseStr());
        }

        zoneTV.setText(zone.getName());
        zoneTV.setBackgroundColor(Color.parseColor(zone.getColor()));
        areaTV.setText(zone.getCategory());
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
