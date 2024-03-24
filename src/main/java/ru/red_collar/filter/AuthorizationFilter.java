package ru.red_collar.filter;

import java.io.IOException;
import java.util.Objects;
import java.util.Set;
import static ru.red_collar.util.UrlPathUtil.ADD_FEEDBACK;
import static ru.red_collar.util.UrlPathUtil.ADD_FILM;
import static ru.red_collar.util.UrlPathUtil.DOWNLOAD;
import static ru.red_collar.util.UrlPathUtil.EDIT_FILM;
import static ru.red_collar.util.UrlPathUtil.FILM;
import static ru.red_collar.util.UrlPathUtil.FILMS;
import static ru.red_collar.util.UrlPathUtil.IMAGES;
import static ru.red_collar.util.UrlPathUtil.LOCALE;
import static ru.red_collar.util.UrlPathUtil.LOGIN;
import static ru.red_collar.util.UrlPathUtil.LOGOUT;
import static ru.red_collar.util.UrlPathUtil.REGISTRATION;
import static ru.red_collar.util.UrlPathUtil.REMOVE_FEEDBACK;
import static ru.red_collar.util.UrlPathUtil.REMOVE_FILM;
import static ru.red_collar.util.UrlPathUtil.SEE_FEEDBACKS;
import static ru.red_collar.util.UrlPathUtil.USER_FEEDBACKS;
import ru.red_collar.dto.UserDto;
import ru.red_collar.entity.UserRole;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebFilter("/*")
public class AuthorizationFilter implements Filter {

    private static final Set<String> PUBLIC_PATH = Set.of(
        LOGIN, REGISTRATION, FILMS, FILM, IMAGES, LOCALE
    );

    private static final Set<String> USER_PATH = Set.of(
        ADD_FEEDBACK, REMOVE_FEEDBACK, LOGOUT
    );

    private static final Set<String> ADMIN_PATH = Set.of(
        ADD_FILM, SEE_FEEDBACKS, USER_FEEDBACKS, ADD_FEEDBACK, REMOVE_FILM, REMOVE_FEEDBACK, DOWNLOAD, LOGOUT, EDIT_FILM
    );

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse,
                         final FilterChain filterChain)
        throws IOException, ServletException {
        final String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();
        if (isPublicPath(requestURI)) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else if (isUserLoggedIn(servletRequest)) {
            if (isUser(servletRequest)) {
                if (isUserPath(requestURI)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else if (isAdminPath(requestURI)) {
                    ((HttpServletResponse) servletResponse).setStatus(403);
                } else {
                    ((HttpServletResponse) servletResponse).setStatus(404);
                }
            } else if (isAdmin(servletRequest)) {
                if (isAdminPath(requestURI)) {
                    filterChain.doFilter(servletRequest, servletResponse);
                } else {
                    ((HttpServletResponse) servletResponse).setStatus(404);
                }
            }
        } else if (isAdminPath(requestURI) || isUserPath(requestURI)) {
            ((HttpServletResponse) servletResponse).setStatus(401);
        } else {
            ((HttpServletResponse) servletResponse).setStatus(404);
        }
    }

    private boolean isAdmin(final ServletRequest servletRequest) {
        return ((UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user")).getRole() ==
               UserRole.ADMIN;
    }

    private boolean isAdminPath(final String requestURI) {
        return ADMIN_PATH.stream()
            .anyMatch(requestURI::startsWith);
    }

    private boolean isUserPath(final String requestURI) {
        return USER_PATH.stream()
            .anyMatch(requestURI::startsWith);
    }

    private boolean isUser(final ServletRequest servletRequest) {
        return ((UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user")).getRole() ==
               UserRole.USER;
    }

    private boolean isUserLoggedIn(final ServletRequest servletRequest) {
        return Objects.nonNull(((HttpServletRequest) servletRequest).getSession().getAttribute("user"));
    }

    private boolean isPublicPath(final String requestURI) {
        return PUBLIC_PATH.stream()
            .anyMatch(requestURI::startsWith);
    }
}
