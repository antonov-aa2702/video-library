package ru.red_collar.servlet;

import java.io.IOException;
import java.util.Optional;
import static ru.red_collar.util.UrlPathUtil.SEE_FEEDBACKS;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.FeedbackService;
import ru.red_collar.service.UserService;
import ru.red_collar.util.JspHelper;

@WebServlet(SEE_FEEDBACKS)
public class SeeFeedbacksServlet extends HttpServlet {

    private final FeedbackService feedbackService = FeedbackService.getInstance();

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("users", userService.findAll());
        req.getRequestDispatcher(JspHelper.get("see-feedbacks"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final String userName = req.getParameter("userName");
        final Optional<Integer> idByName = userService.findIdByName(userName);
        if (idByName.isPresent()) {
            req.setAttribute("feedbacks", feedbackService.findByUserName(userName));
        } else {
            req.setAttribute("error", String.format("%s нет зарегестрирован в системе!", userName));
        }
        doGet(req, resp);
    }
}
