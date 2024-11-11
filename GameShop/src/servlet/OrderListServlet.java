package servlet;

import domain.ExistingGame;
import domain.Order;
import domain.OrderItem;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.ExistingGameService;
import service.OrderService;
import service.UserService;

import java.io.IOException;
import java.util.List;

public class OrderListServlet extends HttpServlet {
    private OrderService oService = new OrderService();
    private UserService userService = new UserService();
    private ExistingGameService existingGameService = new ExistingGameService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("loginForm");
            return;
        }

        // 根据 userId 获取用户信息
        User user = userService.findUserById(userId);

        // 获取用户的所有订单
        List<Order> orderList = oService.selectAll(userId);
        // 为每个订单加载对应的订单项
        for (Order order : orderList) {
            List<OrderItem> orderItems = oService.getOrderItemsByOrderId(order.getId());
            order.setItemList(orderItems);  // 将订单项列表设置到订单中
        }
        request.setAttribute("orderList", orderList);

        // 获取用户的已有游戏列表
        List<ExistingGame> existingGames = existingGameService.getExistingGames(user.getId());
        request.setAttribute("existingGames", existingGames);

        request.getRequestDispatcher("/WEB-INF/views/order_list.jsp").forward(request, response);
    }
}
