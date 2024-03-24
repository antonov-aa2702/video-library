package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.ADD_FILM;
import static ru.red_collar.util.UrlPathUtil.FILMS;
import ru.red_collar.dto.CreateFilmDto;
import ru.red_collar.entity.Country;
import ru.red_collar.entity.Genre;
import ru.red_collar.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.FilmService;
import ru.red_collar.util.JspHelper;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet(ADD_FILM)
public class AddFilmServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        req.setAttribute("countries", Country.values());
        req.setAttribute("genres", Genre.values());
        req.getRequestDispatcher(JspHelper.get("add-film"))
            .forward(req, resp);
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {

        final CreateFilmDto createFilmDto = CreateFilmDto.builder()
            .name(req.getParameter("name"))
            .releaseDate(req.getParameter("releaseDate"))
            .country(req.getParameter("country"))
            .genre(req.getParameter("genre"))
            .duration(req.getParameter("duration"))
            .image(req.getPart("image"))
            .build();

        try {
            filmService.create(createFilmDto);
            resp.sendRedirect(FILMS);
        } catch (final ValidationException validationException) {
            req.setAttribute("errors", validationException.getErrors());
            doGet(req, resp);
        }
    }
}
