package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.FILMS;
import static ru.red_collar.util.UrlPathUtil.REMOVE_FILM;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.FilmService;

@WebServlet(REMOVE_FILM)
public class RemoveFilmServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final Integer filmId = Integer.valueOf(req.getParameter("filmId"));
        filmService.delete(filmId);
        resp.sendRedirect(FILMS);
    }
}
