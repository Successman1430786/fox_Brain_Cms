package com.foxbrain.model;

import java.math.BigDecimal;

public class ExamQuestion {

    private long id;
    private long examId;
    private long questionId;

    private int questionOrder;

    private BigDecimal marks;
    private BigDecimal negativeMarks;

    private String sectionName;

    private boolean required;

    // Optional joined question
    private Question question;

    public ExamQuestion() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getExamId() {
        return examId;
    }

    public void setExamId(long examId) {
        this.examId = examId;
    }

    public long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(long questionId) {
        this.questionId = questionId;
    }

    public int getQuestionOrder() {
        return questionOrder;
    }

    public void setQuestionOrder(int questionOrder) {
        this.questionOrder = questionOrder;
    }

    public BigDecimal getMarks() {
        return marks;
    }

    public void setMarks(BigDecimal marks) {
        this.marks = marks;
    }

    public BigDecimal getNegativeMarks() {
        return negativeMarks;
    }

    public void setNegativeMarks(BigDecimal negativeMarks) {
        this.negativeMarks = negativeMarks;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
}