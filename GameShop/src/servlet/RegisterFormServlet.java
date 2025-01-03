package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import domain.User;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "registerFormServlet",urlPatterns = {"/registerForm"})
public class RegisterFormServlet extends HttpServlet {
    private static final String REGISTER_FORM = "/WEB-INF/jsp/catalog/register.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        User loginUser = (User) session.getAttribute("loginUser");
        if(loginUser == null){
            session.setAttribute("isLogin", false);
        }else {
            session.setAttribute("isLogin", true);
        }
        req.getRequestDispatcher(REGISTER_FORM).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
