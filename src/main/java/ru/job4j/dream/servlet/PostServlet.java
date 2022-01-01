package ru.job4j.dream.servlet;

/**
 * @author Evgenii Shegai
 * @since 25.06.2021
 * @version 1.0
 * Принцип работы - выводится в браузере файл index.jsp - далее editCandidate.jsp - вводим имя и описание новой заявки
 * - в методе PortServlet создается новая заявка сохраняется в хранилище далее создаем еще один запрос к файлу post.jsp
 * получаем все заявки из хранилища
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
