package ru.job4j.dream.servlet;

/**
 * @author Evgenii Shegai
 * @since 25.06.2021
 * @version 1.0
 * Создается новая вакансия и отдается клиенту по запросу
 */

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "PostServlet", value = "/PostServlet")
public class PostServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            Post post = new Post(Integer.parseInt(request.getParameter("id")),
                    request.getParameter("name") ,request.getParameter("desc"), LocalDate.now());
            DbStore.instOf().savePost(post);
            response.sendRedirect(request.getContextPath() + "/posts.do");
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", DbStore.instOf().findAllPosts());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

}
