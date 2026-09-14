package com.foxbrain.controller;

import com.foxbrain.model.ExamAttempt;
import com.foxbrain.service.ExamAttemptService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/exam/attempt")
public class ExamAttemptServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private ExamAttemptService attemptService;

    @Override
    public void init() {
        attemptService =
                new ExamAttemptService();
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {

        try {

            long id =
                    Long.parseLong(
                            request.getParameter("id")
                    );

            ExamAttempt attempt =
                    attemptService.getAttempt(id);

            if (attempt == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Exam attempt not found."
                );

                return;
            }

            request.setAttribute(
                    "attempt",
                    attempt
            );

            request.getRequestDispatcher(
                    "/exam/take.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        try {

            long examId =
                    Long.parseLong(
                            request.getParameter("examId")
                    );

            long studentId =
                    Long.parseLong(
                            request.getParameter("studentId")
                    );

            long attemptId =
                    attemptService.startExam(
                            examId,
                            studentId
                    );

            response.sendRedirect(
                    request.getContextPath()
                            + "/exam/attempt?id="
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