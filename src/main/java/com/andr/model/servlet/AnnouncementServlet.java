package com.andr.model.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.andr.model.User;
import com.andr.model.annoucement.Announcement;
import com.andr.model.service.AnnouncementService;
import com.andr.model.store.HbmStore;
import com.andr.model.store.Store;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Optional;

/**
 * обрабатывает запросы связанные с одним объявлением
 */
@WebServlet("/announcement")
public class AnnouncementServlet extends HttpServlet {
    private static final Logger LOG = LoggerFactory.getLogger(AnnouncementServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        StringBuilder url = new StringBuilder("announcement/");
        String page = req.getParameter("page");
        url.append(page);
        url.append(".jsp");
        String id = req.getParameter("id");
        if (id != null) {
            Store store = HbmStore.instOf();
            Announcement announcement = store.findAnnouncementById(Integer.parseInt(id));
            req.setAttribute("announcement", announcement);
            HttpSession session = req.getSession();
            User currentUser = (User) session.getAttribute("user");
            req.setAttribute("user", currentUser);
        }
        req.getRequestDispatcher(url.toString()).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        AnnouncementService service = AnnouncementService.getInstance();
        req.setCharacterEncoding("UTF-8");
        Optional<String> rsl = service.execute(req);
        if (rsl.isEmpty()) {
            throw new IllegalStateException("Exception  to do this operation!");
        }
        resp.setContentType("application/json");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter writer = new PrintWriter(resp.getOutputStream());
        writer.write(rsl.get());
        writer.flush();
    }
}
