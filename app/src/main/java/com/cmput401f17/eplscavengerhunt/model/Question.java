package com.cmput401f17.eplscavengerhunt.model;

public abstract class Question {

    private int questionID;
    private String prompt;
    private String answer;
    private String imageLink;
    private String soundLink;
    private boolean skipped;

    public Question(final int questionID,
                    final String prompt,
                    final String answer,
                    final String imageLink) {
        this.questionID = questionID;
        this.prompt = prompt;
        this.answer = answer;
        this.imageLink = imageLink;
        this.soundLink = "";
        this.skipped = false;
    }

    public int getQuestionID() {
        return questionID;
    }

    public void setQuestionID(int questionID) {
        this.questionID = questionID;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(final String answer) {
        this.answer = answer;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getSoundLink() {
        return soundLink;
    }

    public void setSoundLink(String soundLink) {
        this.soundLink = soundLink;
    }

    public Boolean isSkipped() {
        return skipped;
    }

    public void skip() {
        this.skipped = true;
    }

}
