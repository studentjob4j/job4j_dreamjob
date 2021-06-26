package ru.job4j.dream.servlet;

import ru.job4j.dream.model.Candidate;
import ru.job4j.dream.model.Post;
import ru.job4j.dream.store.Store;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.time.LocalDate;

@WebServlet(name = "CandidateServlet", value = "/CandidateServlet")
public class CandidateServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        Store.instOf().saveCandidate(new Candidate(Integer.valueOf(request.getParameter("id")),
                request.getParameter("name")));
        response.sendRedirect(request.getContextPath() + "/candidates.jsp");
    }
}
