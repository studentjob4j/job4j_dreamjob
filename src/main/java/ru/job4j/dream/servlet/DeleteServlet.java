package ru.job4j.dream.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

/**
 * @author Shegai Evgenii
 * @since 1.01.2022
 * @version 1.0
 */

public class DeleteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        for (File file : new File("c:\\images" + File.separator + "image" + req.getParameter("id")).listFiles()) {
            if (name.equals(file.getName())) {
                file.delete();
                break;
            }
        }
        resp.sendRedirect(req.getContextPath() + "/upload?id=" + req.getParameter("id"));
    }
}
