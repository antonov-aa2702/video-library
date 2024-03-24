package ru.red_collar.servlet;

import java.io.IOException;
import java.time.LocalDateTime;
import static ru.red_collar.util.UrlPathUtil.ADD_FEEDBACK;
import static ru.red_collar.util.UrlPathUtil.FILM;
import ru.red_collar.dto.CreateFeedbackDto;
import ru.red_collar.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.FeedbackService;
import ru.red_collar.util.JspHelper;

@WebServlet(ADD_FEEDBACK + "/*")
public class AddFeedbackServlet extends HttpServlet {

    private final FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("filmId", req.getParameter("filmId"));
        req.getRequestDispatcher(JspHelper.get("add-feedback"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final UserDto userDto = (UserDto) req.getSession().getAttribute("user");
        final String filmId = req.getParameter("filmId");
        final CreateFeedbackDto createFeedbackDto = CreateFeedbackDto.builder()
            .filmId(Integer.parseInt(filmId))
            .userId(userDto.getId())
            .text(req.getParameter("comment"))
            .rating(Short.parseShort(req.getParameter("rating")))
            .date(LocalDateTime.now())
            .build();
        feedbackService.create(createFeedbackDto);
        resp.sendRedirect(FILM + "?filmId=" + filmId);
    }
}
