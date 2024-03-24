package ru.red_collar.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.FeedbackService;

import java.io.IOException;

import static ru.red_collar.util.UrlPathUtil.FILM;
import static ru.red_collar.util.UrlPathUtil.REMOVE_FEEDBACK;

@WebServlet(REMOVE_FEEDBACK)
public class RemoveFeedbackServlet extends HttpServlet {
    private final FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final Integer feedbackId = Integer.valueOf(req.getParameter("feedbackId"));
        final String filmId = req.getParameter("filmId");
        feedbackService.remove(feedbackId);
        resp.sendRedirect(FILM + "?filmId=" + filmId);
    }
}
