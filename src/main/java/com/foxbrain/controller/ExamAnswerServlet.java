package com.foxbrain.controller;

import com.foxbrain.model.ExamAnswer;
import com.foxbrain.service.ExamAnswerService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exam/answer")
public class ExamAnswerServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamAnswerService answerService;

    @Override
    public void init() {
        answerService =
                new ExamAnswerService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");

        try {

            long attemptId =
                    Long.parseLong(
                            request.getParameter(
                                    "attemptId"
                            )
                    );

            long examQuestionId =
                    Long.parseLong(
                            request.getParameter(
                                    "examQuestionId"
                            )
                    );

            String selectedOption =
                    request.getParameter(
                            "selectedOptionId"
                    );

            String answerText =
                    request.getParameter(
                            "answerText"
                    );

            ExamAnswer answer =
                    new ExamAnswer();

            answer.setAttemptId(attemptId);
            answer.setExamQuestionId(
                    examQuestionId
            );

            if (selectedOption != null &&
                    !selectedOption.trim().isEmpty()) {

                answer.setSelectedOptionId(
                        Long.parseLong(
                                selectedOption
                        )
                );
            }

            answer.setAnswerText(answerText);

            answerService.saveAnswer(answer);

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(
                    "{\"success\":true}"
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.setStatus(
                    HttpServletResponse.SC_BAD_REQUEST
            );

            response.setContentType(
                    "application/json"
            );

            response.getWriter().write(
                    "{\"success\":false,\"message\":\""
                            + escapeJson(e.getMessage())
                            + "\"}"
            );
        }
    }

    private String escapeJson(String text) {

        if (text == null) {
            return "Unknown error";
        }

        return text
                .replace("\\", "\\\\")
                .replace("\"", "\\\"");
    }
}