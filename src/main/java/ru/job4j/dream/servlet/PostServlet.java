package ru.job4j.dream.servlet;

/**
 * @author Evgenii Shegai
 * @since 25.06.2021
 * @version 1.0
 * Создается новая вакансия и отдается клиенту по запросу
 */

import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "PostServlet", value = "/PostServlet")
public class PostServlet extends HttpServlet {

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            request.setCharacterEncoding("UTF-8");
            Post post = new Post((request.getParameter("name")),
                    Integer.parseInt(request.getParameter("id")), request.getParameter("desc"), LocalDate.now());
            Store.instOf().save(post);
            response.sendRedirect(request.getContextPath() + "/posts.do");
        }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("posts", Store.instOf().findAllPosts());
        req.getRequestDispatcher("posts.jsp").forward(req, resp);
    }

}
