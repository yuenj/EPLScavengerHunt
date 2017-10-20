package com.cmput401f17.eplscavengerhunt.custom;

import android.app.Activity;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cmput401f17.eplscavengerhunt.R;
import com.cmput401f17.eplscavengerhunt.model.Question;
import com.cmput401f17.eplscavengerhunt.model.Response;

import java.util.List;

public class SummaryAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;

    private final Activity activity;
    private final List<Response> responses;
    private final List<Question> questions;

    public SummaryAdapter(final Activity activity, final List<Response> responses,
                          final List<Question> questions) {
        this.activity = activity;
        this.questions = questions;
        this.responses = responses;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            // https://stackoverflow.com/questions/24832497/avoid-passing-null-as-the-view-root-need-to-resolve-layout-parameters-on-the-in
            // 20/10/2017
            // vi = inflater.inflate(R.layout.item_summary, null);
            vi = inflater.inflate(R.layout.item_summary, parent, false);

        ImageView thumbnail = vi.findViewById(R.id.summary_thumbnail);
        TextView prompt = vi.findViewById(R.id.summary_prompt);
        TextView usersResponse = vi.findViewById(R.id.summary_response);
        TextView answer = vi.findViewById(R.id.summary_answer);
        RelativeLayout background = vi.findViewById(R.id.summary_background);

        Question question = questions.get(position);
        Response response = responses.get(position);

        prompt.setText(question.getQuestionPrompt());
        usersResponse.setText(response.getResponseStr());
        answer.setText(question.getSolution());

        int correctColor = ContextCompat.getColor(activity.getApplicationContext(), R.color.colorCorrectResponse);
        int incorrectColor = ContextCompat.getColor(activity.getApplicationContext(), R.color.colorIncorrectResponse);

        if (response.isCorrect())
            background.setBackgroundColor(correctColor);
        else
            background.setBackgroundColor(incorrectColor);

        return vi;
    }

    public int getCount() {
        return responses.size();
    }

    public Object getItem(int position) {
        return position;
    }

    public long getItemId(int position) {
        return position;
    }
}
