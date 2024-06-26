package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.LOCALE;
import static ru.red_collar.util.UrlPathUtil.LOGIN;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(LOCALE)
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final String lang = req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);
        final String prevPage = req.getHeader("referer");
        final String page = prevPage != null ? prevPage : LOGIN;
        resp.sendRedirect(page);
    }
}
