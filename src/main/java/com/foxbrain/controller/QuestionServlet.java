package com.foxbrain.controller;

import com.foxbrain.model.Question;
import com.foxbrain.service.QuestionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin/questions")
public class QuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private QuestionService questionService;

    @Override
    public void init() {
        questionService =
                new QuestionService();
    }

    // =====================================================
    // GET
    // =====================================================

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            String action =
                    request.getParameter("action");

            if ("view".equalsIgnoreCase(action)) {

                viewQuestion(request, response);

            } else if ("edit".equalsIgnoreCase(action)) {

                editQuestion(request, response);

            } else if ("delete".equalsIgnoreCase(action)) {

                deleteQuestion(request, response);

            } else if ("options".equalsIgnoreCase(action)) {

                options(request, response);

            } else {

                listQuestions(request, response);
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            listQuestions(request, response);
        }
    }

    // =====================================================
    // POST
    // =====================================================

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            String action =
                    request.getParameter("action");

            if ("create".equalsIgnoreCase(action)) {

                createQuestion(
                        request,
                        response
                );

            } else if ("update".equalsIgnoreCase(action)) {

                updateQuestion(
                        request,
                        response
                );

            } else {

                response.sendError(
                        HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid action."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            listQuestions(request, response);
        }
    }

    // =====================================================
    // LIST
    // =====================================================

    private void listQuestions(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute(
                "questions",
                questionService.getAllQuestions()
        );

        request.getRequestDispatcher(
                "/admin/exams/question-bank.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // VIEW
    // =====================================================

    private void viewQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id =
                parseId(
                        request.getParameter("id")
                );

        Question question =
                questionService.getQuestionById(id);

        if (question == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Question not found."
            );

            return;
        }

        request.setAttribute(
                "question",
                question
        );

        request.getRequestDispatcher(
                "/admin/exams/question-view.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // EDIT
    // =====================================================

    private void editQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        long id =
                parseId(
                        request.getParameter("id")
                );

        Question question =
                questionService.getQuestionById(id);

        if (question == null) {

            response.sendError(
                    HttpServletResponse.SC_NOT_FOUND,
                    "Question not found."
            );

            return;
        }

        request.setAttribute(
                "question",
                question
        );

        request.getRequestDispatcher(
                "/admin/exams/question-edit.jsp"
        ).forward(request, response);
    }

    // =====================================================
    // CREATE
    // =====================================================

    private void createQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        Question question =
                buildQuestion(request);

        long id =
                questionService.createQuestion(
                        question
                );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/questions?action=view&id="
                        + id
        );
    }

    // =====================================================
    // UPDATE
    // =====================================================

    private void updateQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id =
                parseId(
                        request.getParameter("id")
                );

        Question question =
                buildQuestion(request);

        question.setId(id);

        questionService.updateQuestion(
                question
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/questions?action=view&id="
                        + id
        );
    }

    // =====================================================
    // DELETE
    // =====================================================

    private void deleteQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id =
                parseId(
                        request.getParameter("id")
                );

        questionService.deleteQuestion(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/questions"
        );
    }

    // =====================================================
    // OPTIONS
    // =====================================================

    private void options(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long questionId =
                parseId(
                        request.getParameter(
                                "id"
                        )
                );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/question-options?questionId="
                        + questionId
        );
    }

    // =====================================================
    // BUILD QUESTION
    // =====================================================

    private Question buildQuestion(
            HttpServletRequest request) {

        Question question =
                new Question();

        String courseId =
                request.getParameter("courseId");

        if (courseId != null &&
                !courseId.trim().isEmpty()) {

            question.setCourseId(
                    Long.parseLong(courseId)
            );
        }

        question.setQuestionText(
                request.getParameter(
                        "questionText"
                )
        );

        question.setQuestionType(
                request.getParameter(
                        "questionType"
                )
        );

        question.setDifficulty(
                request.getParameter(
                        "difficulty"
                )
        );

        String marks =
                request.getParameter(
                        "defaultMarks"
                );

        if (marks != null &&
                !marks.trim().isEmpty()) {

            question.setDefaultMarks(
                    Double.parseDouble(marks)
            );
        }

        String negative =
                request.getParameter(
                        "negativeMarks"
                );

        if (negative != null &&
                !negative.trim().isEmpty()) {

            question.setNegativeMarks(
                    Double.parseDouble(negative)
            );
        }

        question.setExplanation(
                request.getParameter(
                        "explanation"
                )
        );

        return question;
    }

    // =====================================================
    // ID
    // =====================================================

    private long parseId(String value) {

        if (value == null ||
                value.trim().isEmpty()) {

            throw new IllegalArgumentException(
                    "ID is required."
            );
        }

        try {

            long id =
                    Long.parseLong(value);

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
}