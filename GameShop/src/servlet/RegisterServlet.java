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
import java.io.PrintWriter;

@WebServlet(name = "registerServlet",urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private boolean isLogin;
    private String username;
    private String password;
    private String repeatPassword;
    private String email;
    private String registerMsg;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.username=req.getParameter("username");
        this.password=req.getParameter("password");
        this.repeatPassword=req.getParameter("repeatPassword");
        this.email=req.getParameter("email");

        if(!this.validate()){
            req.setAttribute("registerMsg",this.registerMsg);
            req.getRequestDispatcher("registerForm").forward(req,resp);
        }else {
            User registerUser = new User();
            registerUser.setUsername(this.username);
            registerUser.setPassword(this.password);
            registerUser.setEmail(this.email);
            HttpSession session = req.getSession();

            Boolean isLogin = (Boolean)session.getAttribute("isLogin");
            //通过流输出一个判定注册成功的流
            resp.setContentType("text/plain;charset=UTF-8");
            PrintWriter out = resp.getWriter();
            UserService userService = new UserService();
            if(userService.addUser(registerUser)){
               resp.sendRedirect("loginForm");

            }else{
                req.setAttribute("registerMsg",userService.getMsg());
                req.getRequestDispatcher("registerForm").forward(req,resp);
            }
        }
        HttpSession session = req.getSession();
        this.isLogin=(boolean) session.getAttribute("isLogin");
    }

    private boolean validate(){
        if(this.username==null||this.username.equals("")){
            this.registerMsg = "用户名不能为空!";
            return false;
        }
        if(this.password==null||this.password.equals("")){
            this.registerMsg = "密码不能为空!";
            return false;
        }
        if(this.repeatPassword==null||this.repeatPassword.equals("")){
            this.registerMsg = "密码需要确认!";
            return false;
        }
        if(!this.repeatPassword.equals(password)){
            this.registerMsg = "前后密码不匹配!";
            return false;
        }
        if(this.email==null||this.email.equals("")){
            this.registerMsg = "电子邮箱不能为空!";
            return false;
        }
        return true;
    }


    public boolean isLogin() {
        return isLogin;
    }

    public void setLogin(boolean login) {
        isLogin = login;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
