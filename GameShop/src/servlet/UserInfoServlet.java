package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import domain.User;
import service.UserService;

import java.io.IOException;

@WebServlet(name = "UserInfoServlet",urlPatterns = {"/userinfo"})
public class UserInfoServlet extends HttpServlet {

    private int id;

    private static final String USER_INFO="/WEB-INF/jsp/catalog/userInfo.jsp";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        UserService userService = new UserService();
        this.id = loginUser.getId();
        session.setAttribute("userInfo", userService.findUserById(id));
        req.getRequestDispatcher(USER_INFO).forward(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
