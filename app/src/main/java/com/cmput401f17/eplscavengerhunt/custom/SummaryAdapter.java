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
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Zone;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SummaryAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private final Activity activity;
    private final List<Question> questions;
    private final List<Zone> zones;

    public SummaryAdapter(final Activity activity,
                          final List<Question> questions,
                          final List<Zone> zones) {
        this.activity = activity;
        this.questions = questions;
        this.zones = zones;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            // https://stackoverflow.com/questions/24832497/avoid-passing-null-as-the-view-root-need-to-resolve-layout-parameters-on-the-in
            // 20/10/2017
            // vi = inflater.inflate(R.layout.item_summary, null);
            vi = inflater.inflate(R.layout.item_summary, parent, false);

        final ImageView pictureIV = vi.findViewById(R.id.IV_summary_picture);
        final TextView zoneTV = vi.findViewById(R.id.TV_summary_zone);
        final TextView areaTV = vi.findViewById(R.id.TV_summary_area);
        final TextView answerTV = vi.findViewById(R.id.TV_summary_answer);
        final RelativeLayout summaryContentRL = vi.findViewById(R.id.RL_summary_content);

        final Question question = questions.get(position);
        final Zone zone = zones.get(position);

        // Gets the full answer instead of just 'A' or 'C'
        if (question instanceof MultipleChoiceQuestion) {
            for (String string: question.getChoices()) {
                if (string.charAt(0) == question.getAnswer().charAt(0)) {
                    answerTV.setText(string);
                }
            }
        } else {
            answerTV.setText(question.getAnswer());
        }
        zoneTV.setText("Zone " + zone.getName());
        zoneTV.setBackgroundColor(Color.parseColor(zone.getColor()));
        areaTV.setText(zone.getCategory());
        summaryContentRL.setBackgroundColor(Color.parseColor(zone.getColor()));
        Picasso.with(activity).load(question.getImageLink()).into(pictureIV);

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
