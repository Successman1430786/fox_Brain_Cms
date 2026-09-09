package com.foxbrain.model;

public class QuestionOption {

    private long id;
    private long questionId;

    private String optionText;
    private int optionOrder;
    private boolean correct;

    public QuestionOption() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public String getOptionText() {
        return optionText;
    }

    public void setOptionText(String optionText) {
        this.optionText = optionText;
    }

    public int getOptionOrder() {
        return optionOrder;
    }

    public void setOptionOrder(int optionOrder) {
        this.optionOrder = optionOrder;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }
}