package com.andr.model.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.andr.model.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(UserServlet.class.getName());

    /**
     * ?page=registration -> registration.jsp
     * ?page=login -> login.jsp
     * ?page=profile -> profile.jsp
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String page = req.getParameter("page");
        req.getRequestDispatcher(page + ".jsp").forward(req, resp);
    }

    /**
     * ?action=registration
     * ?action=login
     * ?action=logout
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        UserService service = UserService.getInstance();
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        if (!service.execute(req)) {
            throw new IllegalStateException("Exception to do this operation !");
        }
        writer.write("Operation is succeed!");
        writer.flush();
    }
}
