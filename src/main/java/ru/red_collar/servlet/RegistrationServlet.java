package ru.red_collar.servlet;

import java.io.IOException;
import static ru.red_collar.util.UrlPathUtil.LOGIN;
import static ru.red_collar.util.UrlPathUtil.REGISTRATION;
import ru.red_collar.dto.CreateUserDto;
import ru.red_collar.entity.UserRole;
import ru.red_collar.exception.ValidationException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.UserService;
import ru.red_collar.util.JspHelper;

@WebServlet(REGISTRATION)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("user") == null) {
            req.setAttribute("roles", UserRole.values());
            req.getRequestDispatcher(JspHelper.get("registration"))
                .forward(req, resp);
        } else {
            resp.sendRedirect(LOGIN);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        final CreateUserDto createUserDto = CreateUserDto.builder()
            .name(req.getParameter("name"))
            .birthday(req.getParameter("birthday"))
            .password(req.getParameter("password"))
            .email(req.getParameter("email"))
            .role(req.getParameter("role"))
            .build();
        try {
            final Integer createUserId = userService.create(createUserDto);
            resp.sendRedirect(LOGIN);
        } catch (final ValidationException validationException) {
            req.setAttribute("errors", validationException.getErrors());
            doGet(req, resp);
        }
    }
}
