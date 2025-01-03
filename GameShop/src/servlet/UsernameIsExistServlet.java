package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.UserDao;
import persistence.impl.UserDaoImpl;
import service.UserService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "usernameIsExist",urlPatterns = {"/usernameIsExist"})
public class UsernameIsExistServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        PrintWriter out = resp.getWriter();
        UserDao userDao = new UserDaoImpl();
        if(userDao.findUserByName(username)){
            out.print("Exist");
        }else{
            out.print("Not Exist");
        }
    }
}
