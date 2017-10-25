package com.cmput401f17.eplscavengerhunt.model;

import java.util.ArrayList;
import java.util.List;

public class MultipleChoiceQuestion extends Question {
    List<String> choices;
    String multipleChoiceSolution;

    public MultipleChoiceQuestion() {
        super();
        this.choices = new ArrayList<>();
        this.multipleChoiceSolution = "";
    }

    public MultipleChoiceQuestion(List<String> choices, String multipleChoiceSolution) {
        super();
        this.choices = choices;
        this.multipleChoiceSolution = multipleChoiceSolution;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }

    public String getMultipleChoiceSolution() {
        return multipleChoiceSolution;
    }

    public void setMultipleChoiceSolution(String multipleChoiceSolution) {
        this.multipleChoiceSolution = multipleChoiceSolution;
    }
}
