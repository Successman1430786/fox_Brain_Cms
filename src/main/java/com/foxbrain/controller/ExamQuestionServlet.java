package com.foxbrain.controller;

import com.foxbrain.model.Exam;
import com.foxbrain.model.ExamQuestion;
import com.foxbrain.model.Question;
import com.foxbrain.service.ExamQuestionService;
import com.foxbrain.service.ExamService;
import com.foxbrain.service.QuestionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/exam-questions")
public class ExamQuestionServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamQuestionService examQuestionService;
    private ExamService examService;
    private QuestionService questionService;

    @Override
    public void init() {

        examQuestionService =
                new ExamQuestionService();

        examService =
                new ExamService();

        questionService =
                new QuestionService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            long examId =
                    Long.parseLong(
                            request.getParameter("examId")
                    );

            Exam exam =
                    examService.getExamById(examId);

            List<ExamQuestion> examQuestions =
                    examQuestionService
                            .getQuestionsByExam(examId);

            List<Question> questions =
                    questionService.getAllQuestions();

            request.setAttribute(
                    "exam",
                    exam
            );

            request.setAttribute(
                    "examQuestions",
                    examQuestions
            );

            request.setAttribute(
                    "questions",
                    questions
            );

            request.getRequestDispatcher(
                    "/admin/exams/exam-questions.jsp"
            ).forward(request, response);

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

        String action =
                request.getParameter("action");

        try {

            if ("add".equalsIgnoreCase(action)) {

                addQuestion(request, response);

            } else if ("update".equalsIgnoreCase(action)) {

                updateQuestion(request, response);

            } else if ("delete".equalsIgnoreCase(action)) {

                deleteQuestion(request, response);

            } else if ("reorder".equalsIgnoreCase(action)) {

                reorderQuestion(request, response);

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

            doGet(request, response);
        }
    }

    private void addQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long examId =
                Long.parseLong(
                        request.getParameter("examId")
                );

        long questionId =
                Long.parseLong(
                        request.getParameter("questionId")
                );

        double marks =
                Double.parseDouble(
                        request.getParameter("marks")
                );

        String negative =
                request.getParameter("negativeMarks");

        double negativeMarks = 0;

        if (negative != null &&
                !negative.trim().isEmpty()) {

            negativeMarks =
                    Double.parseDouble(negative);
        }

        String section =
                request.getParameter("sectionName");

        boolean required =
                request.getParameter("isRequired")
                        != null;

        ExamQuestion eq =
                new ExamQuestion();

        eq.setExamId(examId);
        eq.setQuestionId(questionId);
        eq.setMarks(marks);
        eq.setNegativeMarks(negativeMarks);
        eq.setSectionName(section);
        eq.setRequired(required);

        examQuestionService.addQuestion(eq);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exam-questions?examId="
                        + examId
        );
    }

    private void updateQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id =
                Long.parseLong(
                        request.getParameter("id")
                );

        long examId =
                Long.parseLong(
                        request.getParameter("examId")
                );

        double marks =
                Double.parseDouble(
                        request.getParameter("marks")
                );

        double negativeMarks = 0;

        String negative =
                request.getParameter("negativeMarks");

        if (negative != null &&
                !negative.trim().isEmpty()) {

            negativeMarks =
                    Double.parseDouble(negative);
        }

        String section =
                request.getParameter("sectionName");

        boolean required =
                request.getParameter("isRequired")
                        != null;

        ExamQuestion eq =
                examQuestionService.getById(id);

        if (eq == null) {
            throw new IllegalArgumentException(
                    "Exam question not found."
            );
        }

        eq.setMarks(marks);
        eq.setNegativeMarks(negativeMarks);
        eq.setSectionName(section);
        eq.setRequired(required);

        examQuestionService.updateQuestion(eq);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exam-questions?examId="
                        + examId
        );
    }

    private void deleteQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id =
                Long.parseLong(
                        request.getParameter("id")
                );

        long examId =
                Long.parseLong(
                        request.getParameter("examId")
                );

        examQuestionService.removeQuestion(id);

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exam-questions?examId="
                        + examId
        );
    }

    private void reorderQuestion(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long id =
                Long.parseLong(
                        request.getParameter("id")
                );

        long examId =
                Long.parseLong(
                        request.getParameter("examId")
                );

        int order =
                Integer.parseInt(
                        request.getParameter("order")
                );

        examQuestionService.updateQuestionOrder(
                id,
                order
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/exam-questions?examId="
                        + examId
        );
    }
}