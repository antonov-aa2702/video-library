package ru.red_collar.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.dto.FilmDto;
import ru.red_collar.entity.Genre;
import ru.red_collar.entity.Order;
import ru.red_collar.exception.ValidationException;
import ru.red_collar.service.FilmService;
import ru.red_collar.util.JspHelper;

import java.io.IOException;
import java.util.List;

import static ru.red_collar.util.UrlPathUtil.FILMS;

@WebServlet(FILMS)
public class FilmsServlet extends HttpServlet {

    private final FilmService filmService = FilmService.getInstance();

    //отображение всех фильмов
    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        req.setAttribute("films", filmService.findAll());
        req.setAttribute("genres", Genre.values());
        req.setAttribute("orders", Order.values());
        req.getRequestDispatcher(JspHelper.get("films"))
                .forward(req, resp);
    }

    //отображение в нужном порядке по длительности
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String actor = req.getParameter("actor");
        final String reqYear = req.getParameter("year");
        final String nameFilm = req.getParameter("nameFilm");
        final String nameGenre = req.getParameter("nameGenre");
        final Order order = Order.find(req.getParameter("order")).orElse(Order.ASC);
        try {
            if (actor.isEmpty() && reqYear.isEmpty() && nameFilm.isEmpty() && nameGenre == null) {
                req.setAttribute("films", filmService.findAll(order));
            } else {
                final int year = reqYear == null || reqYear.isEmpty() ? -1 : Integer.parseInt(reqYear);
                List<FilmDto> films = filmService.concat(order,
                        filmService.findByNameFilm(nameFilm),
                        filmService.findByGenre(nameGenre),
                        filmService.findByYear(year),
                        filmService.findByActorName(actor));
                req.setAttribute("films", films);
            }
            req.setAttribute("orders", Order.values());
            req.setAttribute("genres", Genre.values());
            req.setAttribute("orders", Order.values());
            req.getRequestDispatcher(JspHelper.get("films"))
                    .forward(req, resp);
        } catch (final ValidationException validationException) {
            resp.sendRedirect(FILMS + "?error&actor=" + actor);
        }
    }
}

