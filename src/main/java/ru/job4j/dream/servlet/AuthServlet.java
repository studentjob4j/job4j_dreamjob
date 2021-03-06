package ru.job4j.dream.servlet;

import ru.job4j.dream.model.User;
import ru.job4j.dream.store.DbStore;
import ru.job4j.dream.store.Store;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Shegai Evgenii
 * @since 11.01.2022
 * @version 1.0
 */

public class AuthServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
            HttpSession sc = req.getSession();
            Store store = DbStore.instOf();
            User user = store.findUserByEmail(email);
            if (user != null && user.getPassword().equals(password)) {
                sc.setAttribute("user", user);
                resp.sendRedirect(req.getContextPath() + "/posts.do");
            }
         else {
            req.setAttribute("error", "Не верный email или пароль");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}
