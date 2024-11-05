package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Order;
import model.User;
import service.OrderService;

import java.io.IOException;
import java.util.List;

public class OrderListServlet extends HttpServlet {
    private OrderService oService = new OrderService();
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//        User u = (User) request.getSession().getAttribute("user");
//        if(u==null)
//        {
//            response.sendRedirect("/index");
//            return;
//        }
//        List<Order> list = oService.selectAll(u.getId());

        List<Order> orderList = oService.selectAll(1);
        request.setAttribute("orderList", orderList);
        request.getRequestDispatcher("/WEB-INF/views/order_list.jsp").forward(request, response);
    }
}
