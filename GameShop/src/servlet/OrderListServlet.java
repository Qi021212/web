package servlet;

import domain.ExistingGame;
import domain.Order;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
        // 从请求中获取 userId
        int userId = Integer.parseInt(request.getParameter("userId"));

        // 根据 userId 获取用户信息
        User user = userService.getUserByUserId(userId);

        // 获取用户的所有订单
        List<Order> orderList = oService.selectAll(1);
        request.setAttribute("orderList", orderList);

        // 获取用户的已有游戏列表
        List<ExistingGame> existingGames = existingGameService.getExistingGames(user.getId());
        request.setAttribute("existingGames", existingGames);

        request.getRequestDispatcher("/WEB-INF/views/order_list.jsp").forward(request, response);
    }
}

//        User u = (User) request.getSession().getAttribute("user");
//        if(u==null)
//        {
//            response.sendRedirect("/index");
//            return;
//        }
//        List<Order> list = oService.selectAll(u.getId());