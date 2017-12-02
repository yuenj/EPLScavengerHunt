package com.cmput401f17.eplscavengerhunt.model;

import java.util.List;

/**
 * Question subclass that requires user to take a picture with device's camera.
 * For now identical to MultipleChoiceQuestion because we aren't required to
 * do anything with the picture, and the way the player chooses their response
 * is by selecting from a list of choices.
 */
public class PicInputQuestion extends Question {
    private List<String> choices;

    public PicInputQuestion(final int questionID,
                            final String prompt,
                            final String imageLink,
                            final List<String> choices,
                            final String picInputSolution) {
        super(questionID, prompt, picInputSolution, imageLink);
        this.choices = choices;
    }

    public List<String> getChoices() {
        return choices;
    }

    public void setChoices(List<String> choices) {
        this.choices = choices;
    }
}
