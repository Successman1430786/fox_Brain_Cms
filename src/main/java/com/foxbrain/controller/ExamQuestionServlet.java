package com.foxbrain.controller;

import java.io.IOException;
import java.math.BigDecimal;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.foxbrain.model.ExamQuestion;
import com.foxbrain.service.ExamQuestionService;

@WebServlet("/admin/exam-questions")
public class ExamQuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamQuestionService service;

    @Override
    public void init() throws ServletException {
        service = new ExamQuestionService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if (action == null ||
            action.trim().isEmpty()) {

            action = "list";
        }

        switch (action) {

            case "list":
                list(request, response);
                break;

            case "remove":
                remove(request, response);
                break;

            default:
                list(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action =
                request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {

            add(request, response);

        } else if ("update".equalsIgnoreCase(action)) {

            update(request, response);

        } else if ("reorder".equalsIgnoreCase(action)) {

            reorder(request, response);

        } else if ("removeAll".equalsIgnoreCase(action)) {

            removeAll(request, response);

        } else {

            list(request, response);
        }
    }

    private void list(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long examId =
                parseLong(
                        request.getParameter("examId"));

        if (examId == null) {
            redirectExam(request, response);
            return;
        }

        request.setAttribute(
                "examId",
                examId);

        request.setAttribute(
                "examQuestions",
                service.getQuestionsByExam(examId));

        request.getRequestDispatcher(
                "/admin/exams/exam-questions.jsp")
                .forward(request, response);
    }

    private void add(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            ExamQuestion eq =
                    buildExamQuestion(request);

            boolean success =
                    service.addQuestionToExam(eq);

            redirectResult(
                    request,
                    response,
                    eq.getExamId(),
                    success ? "added" : "false");

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to add question to exam.");
        }
    }

    private void update(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long id =
                    Long.parseLong(
                            request.getParameter("id"));

            ExamQuestion eq =
                    buildExamQuestion(request);

            eq.setId(id);

            boolean success =
                    service.updateExamQuestion(eq);

            redirectResult(
                    request,
                    response,
                    eq.getExamId(),
                    success ? "updated" : "false");

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to update exam question.");
        }
    }

    private void remove(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Long id =
                parseLong(
                        request.getParameter("id"));

        Long examId =
                parseLong(
                        request.getParameter("examId"));

        if (id == null || examId == null) {
            redirectError(
                    request,
                    response,
                    "Invalid question information.");
            return;
        }

        boolean success =
                service.removeQuestionFromExam(id);

        redirectResult(
                request,
                response,
                examId,
                success ? "removed" : "false");
    }

    private void removeAll(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Long examId =
                parseLong(
                        request.getParameter("examId"));

        if (examId == null) {
            redirectError(
                    request,
                    response,
                    "Invalid exam ID.");
            return;
        }

        boolean success =
                service.removeAllQuestions(examId);

        redirectResult(
                request,
                response,
                examId,
                success ? "removed-all" : "false");
    }

    private void reorder(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long id =
                    Long.parseLong(
                            request.getParameter("id"));

            int order =
                    Integer.parseInt(
                            request.getParameter(
                                    "questionOrder"));

            long examId =
                    Long.parseLong(
                            request.getParameter(
                                    "examId"));

            boolean success =
                    service.reorderQuestion(
                            id,
                            order);

            redirectResult(
                    request,
                    response,
                    examId,
                    success ? "reordered" : "false");

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to reorder question.");
        }
    }

    private ExamQuestion buildExamQuestion(
            HttpServletRequest request) {

        ExamQuestion eq =
                new ExamQuestion();

        eq.setExamId(
                Long.parseLong(
                        request.getParameter(
                                "examId")));

        eq.setQuestionId(
                Long.parseLong(
                        request.getParameter(
                                "questionId")));

        String order =
                request.getParameter(
                        "questionOrder");

        if (order != null &&
            !order.trim().isEmpty()) {

            eq.setQuestionOrder(
                    Integer.parseInt(order));
        }

        String marks =
                request.getParameter(
                        "marks");

        if (marks != null &&
            !marks.trim().isEmpty()) {

            eq.setMarks(
                    new BigDecimal(marks));
        }

        String negative =
                request.getParameter(
                        "negativeMarks");

        if (negative != null &&
            !negative.trim().isEmpty()) {

            eq.setNegativeMarks(
                    new BigDecimal(negative));
        }

        eq.setSectionName(
                trim(
                        request.getParameter(
                                "sectionName")));

        eq.setRequired(
                request.getParameter(
                        "isRequired") != null);

        return eq;
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

    private void redirectExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exams?action=list");
    }

    private void redirectResult(
            HttpServletRequest request,
            HttpServletResponse response,
            long examId,
            String result)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exam-questions?action=list&examId="
                + examId
                + "&success="
                + result);
    }

    private void redirectError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exams?action=list&error="
                + java.net.URLEncoder.encode(
                        message,
                        java.nio.charset.StandardCharsets.UTF_8));
    }
}