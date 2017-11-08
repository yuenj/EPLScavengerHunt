package com.cmput401f17.eplscavengerhunt.model;

import java.util.List;

public class MultipleChoiceQuestion extends Question {
    private List<String> choices;

    public MultipleChoiceQuestion(final int questionID,
                                  final String prompt,
                                  final String imageLink,
                                  final List<String> choices,
                                  final String multipleChoiceSolution) {
        super(questionID, prompt, multipleChoiceSolution, imageLink);
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
