package com.foxbrain.controller;

import com.foxbrain.model.ExamAttempt;
import com.foxbrain.service.ExamAnswerService;
import com.foxbrain.service.ExamAttemptService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exam/submit")
public class ExamSubmitServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamAttemptService attemptService;
    private ExamAnswerService answerService;

    @Override
    public void init() {

        attemptService =
                new ExamAttemptService();

        answerService =
                new ExamAnswerService();
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            long attemptId =
                    Long.parseLong(
                            request.getParameter(
                                    "attemptId"
                            )
                    );

            String auto =
                    request.getParameter(
                            "autoSubmitted"
                    );

            boolean autoSubmitted =
                    "true".equalsIgnoreCase(auto);

            ExamAttempt attempt =
                    attemptService.getAttempt(
                            attemptId
                    );

            if (attempt == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Attempt not found."
                );

                return;
            }

            // -------------------------------------------------
            // Auto evaluate objective answers
            // -------------------------------------------------

            answerService
                    .getAttemptAnswers(attemptId)
                    .forEach(answer -> {

                        try {

                            if (!answer.isEvaluated()) {

                                answerService
                                        .autoEvaluate(
                                                answer.getId()
                                        );
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });

            // -------------------------------------------------
            // Submit attempt
            // -------------------------------------------------

            attemptService.submitExam(
                    attemptId,
                    autoSubmitted
            );

            response.sendRedirect(
                    request.getContextPath()
                            + "/exam/submitted?attemptId="
                            + attemptId
            );

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }
}