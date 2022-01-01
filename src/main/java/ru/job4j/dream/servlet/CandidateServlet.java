package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.Store;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

/**
 * @author Shegai Evgenii
 * @since 1.01.2022
 * @version 1.0
 */

@WebServlet(name = "CandidateServlet", value = "/CandidateServlet")
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Candidate candidate = new Candidate(Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"));
        Store.instOf().save2(candidate);
        response.sendRedirect(request.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", Store.instOf().findAllCandidates());
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }
}
