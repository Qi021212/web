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

@WebServlet (name = "updateServlet" ,urlPatterns = "/update")
public class UpdateServlet extends HttpServlet {
    private int id;
    private String username;
    private String password;

    private String repeatPassword;
    private String updateMsg;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.username = req.getParameter("username");
        this.password = req.getParameter("password");
        this.repeatPassword = req.getParameter("repeatPassword");
        if(!validate()){
            req.setAttribute("updateMsg", this.updateMsg);
            req.getRequestDispatcher("updateForm").forward(req, resp);
        }else {
            HttpSession session = req.getSession();
            User loginUser = (User) session.getAttribute("loginUser");
            UserService userService = new UserService();
            this.id = loginUser.getId();
            boolean result = userService.updateUser(this.id, this.username, this.password);
            if(result){
                session.setAttribute("username",this.username);
                session.setAttribute("password",this.password);
                req.setAttribute("updateMsg", "修改成功!");
                req.getRequestDispatcher("userinfo").forward(req, resp);
            }else {
                this.updateMsg = "修改失败!";
                req.setAttribute("updateMsg", this.updateMsg);
                req.getRequestDispatcher("updateForm").forward(req, resp);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    private boolean validate() {
        if(this.username==null||this.username.equals("")){
            this.updateMsg = "用户名不能为空!";
            return false;
        }
        if(this.password==null||this.password.equals("")){
            this.updateMsg = "密码不能为空!";
            return false;
        }
        if(this.repeatPassword==null||this.repeatPassword.equals("")){
            this.updateMsg = "密码需要确认!";
            return false;
        }
        if(!this.repeatPassword.equals(password)){
            this.updateMsg = "前后密码不匹配!";
            return false;
        }
        return true;
    }
}
