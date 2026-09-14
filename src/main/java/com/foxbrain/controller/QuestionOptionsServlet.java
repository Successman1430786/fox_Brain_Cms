package com.foxbrain.controller;

import com.foxbrain.model.Question;
import com.foxbrain.model.QuestionOption;
import com.foxbrain.service.QuestionService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin/question-options")
public class QuestionOptionsServlet extends HttpServlet {

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

            long questionId =
                    parseId(
                            request.getParameter(
                                    "questionId"
                            )
                    );

            Question question =
                    questionService.getQuestionById(
                            questionId
                    );

            if (question == null) {

                response.sendError(
                        HttpServletResponse.SC_NOT_FOUND,
                        "Question not found."
                );

                return;
            }

            List<QuestionOption> options =
                    questionService.getOptions(
                            questionId
                    );

            request.setAttribute(
                    "question",
                    question
            );

            request.setAttribute(
                    "options",
                    options
            );

            request.getRequestDispatcher(
                    "/admin/exams/question-options.jsp"
            ).forward(request, response);

        } catch (Exception e) {

            e.printStackTrace();

            request.setAttribute(
                    "error",
                    e.getMessage()
            );

            request.getRequestDispatcher(
                    "/admin/exams/question-bank.jsp"
            ).forward(request, response);
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

            if ("add".equalsIgnoreCase(action)) {

                addOption(request, response);

            } else if ("update".equalsIgnoreCase(action)) {

                updateOption(request, response);

            } else if ("delete".equalsIgnoreCase(action)) {

                deleteOption(request, response);

            } else {

                throw new IllegalArgumentException(
                        "Invalid option action."
                );
            }

        } catch (Exception e) {

            e.printStackTrace();

            response.sendError(
                    HttpServletResponse.SC_BAD_REQUEST,
                    e.getMessage()
            );
        }
    }

    // =====================================================
    // ADD OPTION
    // =====================================================

    private void addOption(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long questionId =
                parseId(
                        request.getParameter(
                                "questionId"
                        )
                );

        String optionText =
                request.getParameter(
                        "optionText"
                );

        int optionOrder = 1;

        String order =
                request.getParameter(
                        "optionOrder"
                );

        if (order != null &&
                !order.trim().isEmpty()) {

            optionOrder =
                    Integer.parseInt(order);
        }

        boolean isCorrect =
                request.getParameter(
                        "isCorrect"
                ) != null;

        QuestionOption option =
                new QuestionOption();

        option.setQuestionId(
                questionId
        );

        option.setOptionText(
                optionText
        );

        option.setOptionOrder(
                optionOrder
        );

        option.setCorrect(
                isCorrect
        );

        questionService.addOption(
                option
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/question-options?questionId="
                        + questionId
        );
    }

    // =====================================================
    // UPDATE OPTION
    // =====================================================

    private void updateOption(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long optionId =
                parseId(
                        request.getParameter("id")
                );

        long questionId =
                parseId(
                        request.getParameter(
                                "questionId"
                        )
                );

        String optionText =
                request.getParameter(
                        "optionText"
                );

        int optionOrder =
                Integer.parseInt(
                        request.getParameter(
                                "optionOrder"
                        )
                );

        boolean isCorrect =
                request.getParameter(
                        "isCorrect"
                ) != null;

        QuestionOption option =
                new QuestionOption();

        option.setId(optionId);

        option.setQuestionId(
                questionId
        );

        option.setOptionText(
                optionText
        );

        option.setOptionOrder(
                optionOrder
        );

        option.setCorrect(
                isCorrect
        );

        questionService.updateOption(
                option
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/question-options?questionId="
                        + questionId
        );
    }

    // =====================================================
    // DELETE OPTION
    // =====================================================

    private void deleteOption(
            HttpServletRequest request,
            HttpServletResponse response)
            throws IOException {

        long optionId =
                parseId(
                        request.getParameter("id")
                );

        long questionId =
                parseId(
                        request.getParameter(
                                "questionId"
                        )
                );

        questionService.deleteOption(
                optionId
        );

        response.sendRedirect(
                request.getContextPath()
                        + "/admin/question-options?questionId="
                        + questionId
        );
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