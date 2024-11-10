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

@WebServlet(name = "loginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    private String username;
    private String password;
    private String loginMsg;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userCaptcha = req.getParameter("captcha_input");
        String sessionCaptcha = (String) req.getSession().getAttribute("captcha");
        this.username = req.getParameter("username");
        this.password = req.getParameter("password");
        if (!validate()) {
            req.setAttribute("loginMsg", this.loginMsg);
            req.getRequestDispatcher("loginForm").forward(req, resp);
        } else {
            UserService userService = new UserService();
            User loginUser = userService.login(this.username, this.password);
            if (loginUser != null) {
                if (userCaptcha != null && userCaptcha.equalsIgnoreCase(sessionCaptcha)) {
                    HttpSession session = req.getSession();
                    session.setAttribute("userId", loginUser.getId()); // 将用户ID存入会话
                    session.setAttribute("loginUser", loginUser);// 将整个用户对象存入会话
                    resp.sendRedirect("mainForm");
                } else {
                    req.setAttribute("loginMsg", "验证码错误!");
                    req.getRequestDispatcher("loginForm").forward(req, resp);
                }
            }else {
                req.setAttribute("loginMsg", "用户名或密码错误");
                req.getRequestDispatcher("loginForm").forward(req, resp);
            }
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    private boolean validate() {
        if(this.username==null||this.username.equals("")){
            this.loginMsg = "用户名不能为空!";
            return false;
        }
        if(this.password==null||this.password.equals("")){
            this.loginMsg = "密码不能为空!";
            return false;
        }
        return true;
    }
}
