package ru.red_collar.servlet;

import java.io.IOException;
import java.util.Optional;
import static ru.red_collar.util.UrlPathUtil.FILM;
import ru.red_collar.dto.FilmDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.red_collar.service.FeedbackService;
import ru.red_collar.service.FilmService;
import ru.red_collar.util.JspHelper;

@WebServlet(FILM + "/*")
public class FilmServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getInstance();

    private final FeedbackService feedbackService = FeedbackService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final Integer filmId = Integer.parseInt(req.getParameter("filmId"));
        final Optional<FilmDto> filmById = filmService.findById(filmId);
        filmById.ifPresentOrElse(
            filmDto -> forwardFilmDto(filmDto, req, resp),
            () -> sendError(resp)
        );
    }

    @SneakyThrows
    private void sendError(final HttpServletResponse resp) {
        resp.setStatus(400);
        resp.sendError(404, "Film not found");
    }

    @SneakyThrows
    private void forwardFilmDto(final FilmDto filmDto, final HttpServletRequest req, final HttpServletResponse resp) {
        req.setAttribute("filmDto", filmDto);
        req.setAttribute("feedbacks", feedbackService.findByFilmId(filmDto.getId()));
        req.getRequestDispatcher(JspHelper.get("film"))
            .forward(req, resp);
    }
}
