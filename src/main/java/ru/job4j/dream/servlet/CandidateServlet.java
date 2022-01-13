package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.store.DbStore;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Shegai Evgenii
 * @since 1.01.2022
 * @version 1.0
 * Добавляет кандидатов в хранилище и отдает их по запросу
 */

@WebServlet(name = "CandidateServlet", value = "/CandidateServlet")
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Candidate candidate = new Candidate(Integer.parseInt(request.getParameter("id")),
                request.getParameter("name"));
        DbStore.instOf().saveCandidate(candidate);
        response.sendRedirect(request.getContextPath() + "/candidates.do");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("candidates", DbStore.instOf().findAllCandidates());
        req.setAttribute("user", req.getSession().getAttribute("user"));
        req.getRequestDispatcher("candidates.jsp").forward(req, resp);
    }
}
