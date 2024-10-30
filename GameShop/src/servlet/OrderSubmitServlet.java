package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class OrderSubmitServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(req.getSession().getAttribute("user") != null) {
            req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);
        }else{
            req.setAttribute("failMsg","请登录后，再提交订单！");
            //!!!这里可能不太适合请求转发
            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
        }
    }
}
