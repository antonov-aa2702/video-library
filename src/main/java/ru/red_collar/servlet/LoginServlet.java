package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.FILMS;
import static ru.red_collar.util.UrlPathUtil.LOGIN;
import ru.red_collar.dto.UserDto;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import ru.red_collar.service.UserService;
import ru.red_collar.util.JspHelper;


@WebServlet(LOGIN)
public class LoginServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.getRequestDispatcher(JspHelper.get("login"))
                .forward(req, resp);
        } else {
            resp.sendRedirect(FILMS);
        }
    }

    @Override
    protected void doPost(final HttpServletRequest req, final HttpServletResponse resp)
        throws ServletException, IOException {
        final String email = req.getParameter("email");
        final String password = req.getParameter("password");

        userService.login(email, password).ifPresentOrElse(
            userDto -> onLoginSuccess(userDto, req, resp),
            () -> onLoginFail(req, resp)
        );
    }

    @SneakyThrows
    private void onLoginFail(final HttpServletRequest req, final HttpServletResponse resp) {
        resp.sendRedirect(LOGIN + "?error&email=" + req.getParameter("email"));
    }

    @SneakyThrows
    private void onLoginSuccess(final UserDto userDto, final HttpServletRequest req, final HttpServletResponse resp) {
        req.getSession().setAttribute("user", userDto);
        resp.sendRedirect(FILMS);
    }
}
