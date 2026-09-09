package com.foxbrain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ExamAnswer {

    private long id;
    private long attemptId;
    private long examQuestionId;

    private Long selectedOptionId;

    private String answerText;

    private BigDecimal marksObtained;

    private Boolean correct;

    private boolean evaluated;

    private String teacherRemarks;

    private LocalDateTime answeredAt;
    private LocalDateTime evaluatedAt;

    // Optional display fields
    private String questionText;
    private String questionType;
    private String selectedOptionText;

    public ExamAnswer() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getAttemptId() {
        return attemptId;
    }

    public void setAttemptId(long attemptId) {
        this.attemptId = attemptId;
    }

    public long getExamQuestionId() {
        return examQuestionId;
    }

    public void setExamQuestionId(long examQuestionId) {
        this.examQuestionId = examQuestionId;
    }

    public Long getSelectedOptionId() {
        return selectedOptionId;
    }

    public void setSelectedOptionId(Long selectedOptionId) {
        this.selectedOptionId = selectedOptionId;
    }

    public String getAnswerText() {
        return answerText;
    }

    public void setAnswerText(String answerText) {
        this.answerText = answerText;
    }

    public BigDecimal getMarksObtained() {
        return marksObtained;
    }

    public void setMarksObtained(BigDecimal marksObtained) {
        this.marksObtained = marksObtained;
    }

    public Boolean getCorrect() {
        return correct;
    }

    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

    public boolean isEvaluated() {
        return evaluated;
    }

    public void setEvaluated(boolean evaluated) {
        this.evaluated = evaluated;
    }

    public String getTeacherRemarks() {
        return teacherRemarks;
    }

    public void setTeacherRemarks(String teacherRemarks) {
        this.teacherRemarks = teacherRemarks;
    }

    public LocalDateTime getAnsweredAt() {
        return answeredAt;
    }

    public void setAnsweredAt(LocalDateTime answeredAt) {
        this.answeredAt = answeredAt;
    }

    public LocalDateTime getEvaluatedAt() {
        return evaluatedAt;
    }

    public void setEvaluatedAt(LocalDateTime evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getSelectedOptionText() {
        return selectedOptionText;
    }

    public void setSelectedOptionText(String selectedOptionText) {
        this.selectedOptionText = selectedOptionText;
    }
}