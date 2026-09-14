package com.foxbrain.controller;

import com.foxbrain.model.Exam;
import com.foxbrain.service.ExamService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/exams")
public class ExamServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamService examService;

    @Override
    public void init() {
        examService = new ExamService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        try {

            if ("view".equalsIgnoreCase(action)) {
                viewExam(request, response);

            } else if ("edit".equalsIgnoreCase(action)) {
                editExam(request, response);

            } else if ("delete".equalsIgnoreCase(action)) {
                deleteExam(request, response);

            } else if ("questions".equalsIgnoreCase(action)) {
                questions(request, response);

            } else {
                listExams(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "/admin/exams/index.jsp"
            ).forward(request, response);
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        try {

            if ("create".equalsIgnoreCase(action)) {
                createExam(request, response);

            } else if ("update".equalsIgnoreCase(action)) {
                updateExam(request, response);

            } else {
                response.sendRedirect(
                        request.getContextPath()
                                + "/admin/exams"
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "/admin/exams/index.jsp"
            ).forward(request, response);
        }
    }

    // =====================================================
    // LIST
    // =====================================================

    private void listExams(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        List<Exam> exams =
                examService.getAllExams();

        request.setAttribute(
                "exams",
                exams
        );

        request.getRequestDispatcher(
                "/admin/exams/index.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // VIEW
    // =====================================================

    private void viewExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = parseId(
                request.getParameter("id")
        );

        Exam exam =
                examService.getExamById(id);

        if (exam == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Exam not found."
            );
            return;
        }

        request.setAttribute(
                "exam",
                exam
        );

        request.getRequestDispatcher(
                "/admin/exams/view.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // EDIT
    // =====================================================

    private void editExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = parseId(
                request.getParameter("id")
        );

        Exam exam =
                examService.getExamById(id);

        if (exam == null) {
            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Exam not found."
            );
            return;
        }

        request.setAttribute(
                "exam",
                exam
        );

        request.getRequestDispatcher(
                "/admin/exams/edit.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // CREATE
    // =====================================================

    private void createExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Exam exam =
                buildExamFromRequest(request);

        long id =
                examService.createExam(exam);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exams?action=view&id="
                        + id
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    private void updateExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id = parseId(
                request.getParameter("id")
        );

        Exam exam =
                buildExamFromRequest(request);

        exam.setId(id);

        examService.updateExam(exam);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exams?action=view&id="
                        + id
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    private void deleteExam(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id = parseId(
                request.getParameter("id")
        );

        examService.deleteExam(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exams"
        );
    }

    // =====================================================
    // QUESTIONS
    // =====================================================

    private void questions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id = parseId(
                request.getParameter("id")
        );

        Exam exam =
                examService.getExamById(id);

        request.setAttribute(
                "exam",
                exam
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exam-questions?examId="
                        + id
        );
    }

    // =====================================================
    // BUILD EXAM
    // =====================================================

    private Exam buildExamFromRequest(
            HttpServletRequest request) {

        Exam exam = new Exam();

        exam.setBatchId(
                parseLong(
                        request.getParameter("batchId")
                )
        );

        exam.setTitle(
                request.getParameter("title")
        );

        exam.setExamType(
                request.getParameter("examType")
        );

        exam.setExamMode(
                request.getParameter("examMode")
        );

        String examDate =
                request.getParameter("examDate");

        if (examDate != null &&
                !examDate.trim().isEmpty()) {

            exam.setExamDate(
                    java.sql.Date.valueOf(examDate)
            );
        }

        String startTime =
                request.getParameter("startTime");

        if (startTime != null &&
                !startTime.trim().isEmpty()) {

            exam.setStartTime(
                    java.sql.Time.valueOf(
                            startTime.length() == 5
                                    ? startTime + ":00"
                                    : startTime
                    )
            );
        }

        String endTime =
                request.getParameter("endTime");

        if (endTime != null &&
                !endTime.trim().isEmpty()) {

            exam.setEndTime(
                    java.sql.Time.valueOf(
                            endTime.length() == 5
                                    ? endTime + ":00"
                                    : endTime
                    )
            );
        }

        String duration =
                request.getParameter(
                        "durationMinutes"
                );

        if (duration != null &&
                !duration.trim().isEmpty()) {

            exam.setDurationMinutes(
                    Integer.parseInt(duration)
            );
        }

        String totalMarks =
                request.getParameter(
                        "totalMarks"
                );

        if (totalMarks != null &&
                !totalMarks.trim().isEmpty()) {

            exam.setTotalMarks(
                    Double.parseDouble(totalMarks)
            );
        }

        String passingMarks =
                request.getParameter(
                        "passingMarks"
                );

        if (passingMarks != null &&
                !passingMarks.trim().isEmpty()) {

            exam.setPassingMarks(
                    Double.parseDouble(passingMarks)
            );
        }

        exam.setRoomName(
                request.getParameter("roomName")
        );

        exam.setInstructions(
                request.getParameter("instructions")
        );

        exam.setAllowNavigation(
                request.getParameter(
                        "allowNavigation"
                ) != null
        );

        exam.setShuffleQuestions(
                request.getParameter(
                        "shuffleQuestions"
                ) != null
        );

        exam.setShuffleOptions(
                request.getParameter(
                        "shuffleOptions"
                ) != null
        );

        String status =
                request.getParameter("status");

        if (status == null ||
                status.trim().isEmpty()) {

            status = "DRAFT";
        }

        exam.setStatus(status);

        return exam;
    }

    // =====================================================
    // ID PARSING
    // =====================================================

    private long parseId(String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "ID is required."
            );
        }

        try {
            long id = Long.parseLong(value);

            if (id <= 0) {
                throw new IllegalArgumentException(
                        "Invalid ID."
                );
            }

            return id;

        } catch (NumberFormatException e) {

            throw new IllegalArgumentException(
                    "Invalid ID."
            );
        }
    }

    private long parseLong(String value) {

        return parseId(value);
    }
}