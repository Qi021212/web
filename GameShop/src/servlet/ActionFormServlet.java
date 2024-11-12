package servlet;

import domain.Action;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ActionService;
import service.UserService;

import java.io.IOException;
import java.util.List;

public class ActionFormServlet extends HttpServlet {
    private ActionService actionService;
    private UserService userService;
    public static final String ACTION_FORM = "/WEB-INF/jsp/catalog/action.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        actionService =new ActionService();
        userService =new UserService();
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            resp.sendRedirect("loginForm");
            return;
        }
        List<Action> actionList=actionService.getActionListByUserId(userId);
        User user = userService.findUserById(userId);
        session.setAttribute("actionList",actionList);
        session.setAttribute("user", user);
        req.getRequestDispatcher(ACTION_FORM).forward(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
