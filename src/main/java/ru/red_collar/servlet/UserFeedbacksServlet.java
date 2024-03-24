package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.USER_FEEDBACKS;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.FeedbackService;
import ru.red_collar.util.JspHelper;


@WebServlet(USER_FEEDBACKS)
public class UserFeedbacksServlet extends HttpServlet {

    private final FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("userFeedbacks", feedbackService.findByUserName(req.getParameter("userName")));
        req.getRequestDispatcher(JspHelper.get("user-feedbacks"))
            .forward(req, resp);
    }
}
