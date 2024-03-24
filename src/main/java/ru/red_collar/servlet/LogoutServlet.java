package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.LOGIN;
import static ru.red_collar.util.UrlPathUtil.LOGOUT;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(LOGOUT)
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect(LOGIN);
    }
}
