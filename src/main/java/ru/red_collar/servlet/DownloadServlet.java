package ru.red_collar.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.red_collar.service.ReportService;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import static ru.red_collar.util.UrlPathUtil.DOWNLOAD;

@WebServlet(DOWNLOAD)
public class DownloadServlet extends HttpServlet {

    private final ReportService reportService = ReportService.getInstance();

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {
        final String userName = String.valueOf(req.getParameter("userName"));
        resp.setHeader("Content-disposition", "attachment; filename=\"report.txt\"");
        resp.setContentType("text/plain");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());
        try (PrintWriter writer = resp.getWriter()) {
            writer.write(reportService.create(userName));
        }
    }
}
