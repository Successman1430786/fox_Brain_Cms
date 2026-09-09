package com.foxbrain.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.foxbrain.model.ExamResult;
import com.foxbrain.service.ExamResultService;

@WebServlet("/admin/exam-results")
public class ExamResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamResultService resultService;

    @Override
    public void init() throws ServletException {
        resultService = new ExamResultService();
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
                list(request, response);
                break;

            case "view":
                view(request, response);
                break;

            case "publish":
                publish(request, response);
                break;

            case "unpublish":
                unpublish(request, response);
                break;

            default:
                list(request, response);
                break;
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if ("create".equalsIgnoreCase(action)) {

            create(request, response);

        } else if ("createFromAttempt".equalsIgnoreCase(action)) {

            createFromAttempt(request, response);

        } else if ("update".equalsIgnoreCase(action)) {

            update(request, response);

        } else {

            list(request, response);
        }
    }

    private void list(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String examId = request.getParameter("examId");

        if (examId != null && !examId.trim().isEmpty()) {

            try {

                long id = Long.parseLong(examId);

                request.setAttribute(
                        "results",
                        resultService.getResultsByExam(id)
                );

            } catch (NumberFormatException e) {

                request.setAttribute(
                        "results",
                        resultService.getAllResults()
                );
            }

        } else {

            request.setAttribute(
                    "results",
                    resultService.getAllResults()
            );
        }

        request.getRequestDispatcher(
                "/admin/exams/results.jsp"
        ).forward(request, response);
    }

    private void view(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        Long id = parseLong(
                request.getParameter("id")
        );

        if (id == null) {
            redirectList(request, response);
            return;
        }

        ExamResult result =
                resultService.getResultById(id);

        if (result == null) {
            redirectList(request, response);
            return;
        }

        request.setAttribute(
                "result",
                result
        );

        request.getRequestDispatcher(
                "/admin/exams/result-view.jsp"
        ).forward(request, response);
    }

    private void create(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            ExamResult result =
                    buildResult(request);

            boolean success =
                    resultService.createResult(result);

            redirectResult(
                    request,
                    response,
                    result.getExamId(),
                    success ? "created" : "false"
            );

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to create result."
            );
        }
    }

    private void createFromAttempt(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long attemptId =
                    Long.parseLong(
                            request.getParameter("attemptId")
                    );

            boolean success =
                    resultService.createResultFromAttempt(
                            attemptId
                    );

            response.sendRedirect(
                    request.getContextPath()
                    + "/admin/exam-results?action=list&success="
                    + (success ? "created" : "false")
            );

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to generate result."
            );
        }
    }

    private void update(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long id =
                    Long.parseLong(
                            request.getParameter("id")
                    );

            ExamResult result =
                    buildResult(request);

            result.setId(id);

            boolean success =
                    resultService.updateResult(result);

            redirectResult(
                    request,
                    response,
                    result.getExamId(),
                    success ? "updated" : "false"
            );

        } catch (Exception e) {

            e.printStackTrace();

            redirectError(
                    request,
                    response,
                    "Unable to update result."
            );
        }
    }

    private void publish(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Long id =
                parseLong(
                        request.getParameter("id")
                );

        if (id == null) {
            redirectList(request, response);
            return;
        }

        boolean success =
                resultService.publishResult(id);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exam-results?action=list&success="
                + (success ? "published" : "false")
        );
    }

    private void unpublish(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Long id =
                parseLong(
                        request.getParameter("id")
                );

        if (id == null) {
            redirectList(request, response);
            return;
        }

        boolean success =
                resultService.unpublishResult(id);

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exam-results?action=list&success="
                + (success ? "unpublished" : "false")
        );
    }

    private ExamResult buildResult(
            HttpServletRequest request) {

        ExamResult result =
                new ExamResult();

        result.setExamId(
                Long.parseLong(
                        request.getParameter("examId")
                )
        );

        result.setStudentId(
                Long.parseLong(
                        request.getParameter("studentId")
                )
        );

        String marks =
                request.getParameter("marksObtained");

        if (marks != null &&
                !marks.trim().isEmpty()) {

            result.setMarksObtained(
                    new BigDecimal(marks.trim())
            );
        }

        result.setGrade(
                trim(
                        request.getParameter("grade")
                )
        );

        result.setResultStatus(
                trim(
                        request.getParameter("resultStatus")
                )
        );

        result.setRemarks(
                trim(
                        request.getParameter("remarks")
                )
        );

        return result;
    }

    private Long parseLong(String value) {

        try {

            if (value == null ||
                    value.trim().isEmpty()) {

                return null;
            }

            return Long.parseLong(
                    value.trim()
            );

        } catch (NumberFormatException e) {

            return null;
        }
    }

    private String trim(String value) {

        return value == null
                ? ""
                : value.trim();
    }

    private void redirectList(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exam-results?action=list"
        );
    }

    private void redirectResult(
            HttpServletRequest request,
            HttpServletResponse response,
            long examId,
            String result)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exam-results?action=list&examId="
                + examId
                + "&success="
                + result
        );
    }

    private void redirectError(
            HttpServletRequest request,
            HttpServletResponse response,
            String message)
            throws IOException {

        response.sendRedirect(
                request.getContextPath()
                + "/admin/exam-results?action=list&error="
                + URLEncoder.encode(
                        message,
                        StandardCharsets.UTF_8
                )
        );
    }
}