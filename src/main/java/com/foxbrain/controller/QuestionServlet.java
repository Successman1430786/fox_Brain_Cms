package com.foxbrain.controller;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;
import com.foxbrain.service.QuestionService;

@WebServlet("/admin/question-bank")
public class QuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private QuestionService questionService;

    @Override
    public void init() throws ServletException {
        questionService = new QuestionService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.trim().isEmpty()) {
            action = "list";
        }

        switch (action) {

            case "list":
                listQuestions(request, response);
                break;

            case "view":
                viewQuestion(request, response);
                break;

            case "edit":
                editQuestion(request, response);
                break;

            case "delete":
                deleteQuestion(request, response);
                break;

            case "options":
                listOptions(request, response);
                break;

            default:
                redirectList(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {

            createQuestion(request, response);

        } else if ("update".equalsIgnoreCase(action)) {

            updateQuestion(request, response);

        } else if ("addOption".equalsIgnoreCase(action)) {

            addOption(request, response);

        } else if ("updateOption".equalsIgnoreCase(action)) {

            updateOption(request, response);

        } else if ("deleteOption".equalsIgnoreCase(action)) {

            deleteOption(request, response);

        } else {

            redirectList(request, response);
        }
    }

    private void listQuestions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "questions",
                questionService.getAllQuestions());

        request.getRequestDispatcher(
                "/admin/exams/question-bank.jsp")
                .forward(request, response);
    }

    private void viewQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long id = parseLong(
                request.getParameter("id"));

        if (id == null) {
            redirectList(request, response);
            return;
        }

        Question question =
                questionService.getQuestionById(id);

        if (question == null) {
            redirectList(request, response);
            return;
        }

        request.setAttribute(
                "question",
                question);

        request.getRequestDispatcher(
                "/admin/exams/question-view.jsp")
                .forward(request, response);
    }

    private void editQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long id = parseLong(
                request.getParameter("id"));

        if (id == null) {
            redirectList(request, response);
            return;
        }

        Question question =
                questionService.getQuestionById(id);

        if (question == null) {
            redirectList(request, response);
            return;
        }

        request.setAttribute(
                "question",
                question);

        request.getRequestDispatcher(
                "/admin/exams/question-edit.jsp")
                .forward(request, response);
    }

    private void createQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Question question =
                    buildQuestion(request);

            boolean success =
                    questionService.createQuestion(
                            question);

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/question-bank?action=list&success=created");

            } else {

                redirectError(
                        request,
                        response,
                        "Unable to create question.");
            }

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Invalid question information.");
        }
    }

    private void updateQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            Long id =
                    parseLong(
                            request.getParameter("id"));

            if (id == null) {
                redirectError(
                        request,
                        response,
                        "Invalid question ID.");
                return;
            }

            Question question =
                    buildQuestion(request);

            question.setId(id);

            boolean success =
                    questionService.updateQuestion(
                            question);

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/question-bank?action=list&success=updated");

            } else {

                redirectError(
                        request,
                        response,
                        "Unable to update question.");
            }

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Invalid question information.");
        }
    }

    private void deleteQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Long id =
                parseLong(
                        request.getParameter("id"));

        if (id == null) {
            redirectList(request, response);
            return;
        }

        boolean success =
                questionService.deleteQuestion(id);

        if (success) {

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/question-bank?action=list&success=deleted");

        } else {

            redirectError(
                    request,
                    response,
                    "Unable to delete question.");
        }
    }

    private void listOptions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long questionId =
                parseLong(
                        request.getParameter("questionId"));

        if (questionId == null) {
            redirectList(request, response);
            return;
        }

        request.setAttribute(
                "options",
                questionService.getOptions(questionId));

        request.setAttribute(
                "question",
                questionService.getQuestionById(questionId));

        request.getRequestDispatcher(
                "/admin/exams/question-options.jsp")
                .forward(request, response);
    }

    private void addOption(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            QuestionOption option =
                    buildOption(request);

            boolean success =
                    questionService.addOption(option);

            long questionId =
                    option.getQuestionId();

            if (success) {

                response.sendRedirect(
                        request.getContextPath()
                        + "/admin/question-bank?action=options&questionId="
                        + questionId
                        + "&success=added");

            } else {

                redirectError(
                        request,
                        response,
                        "Unable to add option.");
            }

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Invalid option.");
        }
    }

    private void updateOption(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            QuestionOption option =
                    buildOption(request);

            option.setId(
                    Long.parseLong(
                            request.getParameter("id")));

            boolean success =
                    questionService.updateOption(option);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/question-bank?action=options&questionId="
                    + option.getQuestionId()
                    + "&success="
                    + (success ? "updated" : "false"));

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to update option.");
        }
    }

    private void deleteOption(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long optionId =
                    Long.parseLong(
                            request.getParameter("id"));

            long questionId =
                    Long.parseLong(
                            request.getParameter("questionId"));

            boolean success =
                    questionService.deleteOption(
                            optionId);

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/question-bank?action=options&questionId="
                    + questionId
                    + "&success="
                    + (success ? "deleted" : "false"));

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to delete option.");
        }
    }

    private Question buildQuestion(
            HttpServletRequest request) {

        Question question =
                new Question();

        String courseId =
                request.getParameter("courseId");

        if (courseId != null &&
            !courseId.trim().isEmpty()) {

            question.setCourseId(
                    Long.parseLong(courseId));
        }

        question.setQuestionText(
                trim(
                        request.getParameter(
                                "questionText")));

        question.setQuestionType(
                trim(
                        request.getParameter(
                                "questionType")));

        question.setDifficulty(
                trim(
                        request.getParameter(
                                "difficulty")));

        String marks =
                trim(
                        request.getParameter(
                                "defaultMarks"));

        if (!marks.isEmpty()) {

            question.setDefaultMarks(
                    new BigDecimal(marks));
        }

        String negativeMarks =
                trim(
                        request.getParameter(
                                "negativeMarks"));

        if (!negativeMarks.isEmpty()) {

            question.setNegativeMarks(
                    new BigDecimal(
                            negativeMarks));
        }

        question.setExplanation(
                trim(
                        request.getParameter(
                                "explanation")));

        question.setStatus(
                trim(
                        request.getParameter(
                                "status")));

        return question;
    }

    private QuestionOption buildOption(
            HttpServletRequest request) {

        QuestionOption option =
                new QuestionOption();

        String questionId =
                request.getParameter(
                        "questionId");

        if (questionId != null &&
            !questionId.trim().isEmpty()) {

            option.setQuestionId(
                    Long.parseLong(questionId));
        }

        option.setOptionText(
                trim(
                        request.getParameter(
                                "optionText")));

        String order =
                trim(
                        request.getParameter(
                                "optionOrder"));

        if (!order.isEmpty()) {

            option.setOptionOrder(
                    Integer.parseInt(order));
        }

        option.setCorrect(
                request.getParameter(
                        "isCorrect") != null);

        return option;
    }

    private Long parseLong(String value) {

        try {

            if (value == null ||
                value.trim().isEmpty()) {

                return null;
            }

            return Long.parseLong(value.trim());

        } catch (NumberFormatException e) {

            return null;
        }
    }

    private String trim(String value) {

        return value == null ? "" : value.trim();
    }

    private void redirectList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/question-bank?action=list");
    }

    private void redirectError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/question-bank?action=list&error="
                + java.net.URLEncoder.encode(
                        message,
                        java.nio.charset.StandardCharsets.UTF_8));
    }
}