package com.cmput401f17.eplscavengerhunt.model;


public class WrittenInputQuestion extends Question {

    public WrittenInputQuestion(final int questionID,
                                final String prompt,
                                final String imageLink,
                                final String writtenInputSolution) {
        super(questionID, prompt, writtenInputSolution, imageLink);
    }
}
