package com.foxbrain.controller;

import com.foxbrain.model.ExamResult;
import com.foxbrain.service.ExamResultService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/exam-results")
public class ExamResultServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamResultService resultService;

    @Override
    public void init() {
        resultService =
                new ExamResultService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String id =
                    request.getParameter("id");

            String examId =
                    request.getParameter("examId");

            String studentId =
                    request.getParameter(
                            "studentId"
                    );

            if (id != null &&
                    !id.trim().isEmpty()) {

                viewResult(request, response);

            } else if (examId != null &&
                    !examId.trim().isEmpty()) {

                listExamResults(
                        request,
                        response
                );

            } else if (studentId != null &&
                    !studentId.trim().isEmpty()) {

                listStudentResults(
                        request,
                        response
                );

            } else {

                listResults(
                        request,
                        response
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "/admin/exams/results.jsp"
            ).forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            String action =
                    request.getParameter(
                            "action"
                    );

            long id =
                    Long.parseLong(
                            request.getParameter("id")
                    );

            if ("publish".equalsIgnoreCase(action)) {

                resultService.publishResult(id);

            } else if (
                    "unpublish"
                            .equalsIgnoreCase(action)) {

                resultService.unpublishResult(id);

            } else {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid action."
                );

                return;
            }

            response.sendRedirect(
                    request.getContextPath()
                            + "/admin/exam-results?id="
                            + id
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    // =====================================================
    // ALL RESULTS
    // =====================================================

    private void listResults(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<ExamResult> results =
                resultService.getAllResults();

        request.setAttribute(
                "results",
                results
        );

        request.getRequestDispatcher(
                "/admin/exams/results.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // EXAM RESULTS
    // =====================================================

    private void listExamResults(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long examId =
                Long.parseLong(
                        request.getParameter("examId")
                );

        List<ExamResult> results =
                resultService.getResultsByExam(
                        examId
                );

        request.setAttribute(
                "results",
                results
        );

        request.setAttribute(
                "examId",
                examId
        );

        request.getRequestDispatcher(
                "/admin/exams/results.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // STUDENT RESULTS
    // =====================================================

    private void listStudentResults(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long studentId =
                Long.parseLong(
                        request.getParameter(
                                "studentId"
                        )
                );

        List<ExamResult> results =
                resultService.getResultsByStudent(
                        studentId
                );

        request.setAttribute(
                "results",
                results
        );

        request.setAttribute(
                "studentId",
                studentId
        );

        request.getRequestDispatcher(
                "/admin/exams/results.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // VIEW RESULT
    // =====================================================

    private void viewResult(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id =
                Long.parseLong(
                        request.getParameter("id")
                );

        ExamResult result =
                resultService.getResult(id);

        if (result == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Result not found."
            );

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
}