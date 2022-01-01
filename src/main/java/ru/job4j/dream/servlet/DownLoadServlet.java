package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author Shegai Evgenii
 * @since 1.01.2022
 * @version 1.0
 */

public class DownLoadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        File users = null;
        String name = req.getParameter("name");
        for (File file : new File("c:\\images" + File.separator + "image" + req.getParameter("id")).listFiles()) {
            if (name.equals(file.getName())) {
                users = file;
                break;
            }
        }
        resp.setContentType("application/octet-stream");
        resp.setHeader("Content-Disposition", "attachment; filename=\"" + users.getName() + "\"");
        try (FileInputStream stream = new FileInputStream(users)) {

            resp.getOutputStream().write(stream.readAllBytes());
        }
    }
}
