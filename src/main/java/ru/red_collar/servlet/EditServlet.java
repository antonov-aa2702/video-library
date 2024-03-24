package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.EDIT_FILM;
import static ru.red_collar.util.UrlPathUtil.FILMS;
import ru.red_collar.dto.FilmDto;
import ru.red_collar.dto.UpdateFilmDto;
import ru.red_collar.entity.Country;
import ru.red_collar.entity.Genre;
import ru.red_collar.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.red_collar.service.FilmService;
import ru.red_collar.util.JspHelper;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(EDIT_FILM)
public class EditServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final Integer filmId = Integer.parseInt(req.getParameter("filmId"));
        filmService.findById(filmId).ifPresentOrElse(
            filmDto -> forwardFilmDto(filmDto, req, resp),
            () -> sendError(resp)
        );

    }

    @SneakyThrows
    private void sendError(final HttpServletResponse resp) {
        resp.setStatus(400);
        resp.sendError(404);
    }

    @SneakyThrows
    private void forwardFilmDto(final FilmDto filmDto, final HttpServletRequest req, final HttpServletResponse resp) {
        req.setAttribute("filmDto", filmDto);
        req.setAttribute("countries", Country.values());
        req.setAttribute("genres", Genre.values());
        req.getRequestDispatcher(JspHelper.get("edit-film"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final UpdateFilmDto createFilmDto = UpdateFilmDto.builder()
            .id(req.getParameter("filmId"))
            .name(req.getParameter("name"))
            .releaseDate(req.getParameter("releaseDate"))
            .country(req.getParameter("country"))
            .genre(req.getParameter("genre"))
            .duration(req.getParameter("duration"))
            .image(req.getPart("image"))
            .build();
        try {
            filmService.update(createFilmDto);
            resp.sendRedirect(FILMS);
        } catch (final ValidationException validationException) {
            req.setAttribute("errors", validationException.getErrors());
            doGet(req, resp);
        }
    }
}
