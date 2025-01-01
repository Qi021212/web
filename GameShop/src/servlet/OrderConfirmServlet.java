package servlet;

import domain.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.impl.CartDAOImpl;
import service.*;

import java.io.IOException;
import java.util.Date;

public class OrderConfirmServlet extends HttpServlet {
    private OrderService oService = new OrderService();
    CartService cartService = new CartService();
    private UserService userService = new UserService(); // 假设你有 UserService 来获取用户信息
    private CartDAOImpl cartDAO = new CartDAOImpl();
    private ExistingGameService existingGameService = new ExistingGameService();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("loginForm");
            return;
        }

        // 根据 userId 获取用户信息
        User user = userService.findUserById(userId);

        //创建订单对象
        Order o = new Order();
        o.setUserId(userId);
        o.setUser(user);
        o.setDatetime(new Date());
        o.setStatus(2);// 状态：1-待支付，2-已支付等
        o.setName(request.getParameter("name"));
        o.setPhone(request.getParameter("phone"));
        o.setEmail(request.getParameter("email"));
        o.setPaytype(Integer.parseInt(request.getParameter("paytype")));
        o.setTotal(Double.parseDouble(request.getParameter("totalAmount")));

        // 保存订单并获取订单ID
        int orderId = oService.addOrder(o);
        o.setId(orderId); // 确保订单对象的 ID 是最新的

        // 插入订单项
        String[] itemIds = request.getParameterValues("selectedItemIds");

        if (itemIds != null) {
            for (int i = 0; i < itemIds.length; i++) {
                int itemId = Integer.parseInt(itemIds[i]);

                //更新购物车中内容，将刚已买物品删去
                cartService.deleteCartItem(userId, itemId);

                // 从数据库或其他地方获取商品信息
                Item item = oService.getItemById(itemId); // 假设您有这个方法来获取 Item
                if (item != null) {
                    OrderItem orderItem = new OrderItem(itemId, item, item.getPrice(), o);
                    orderItem.setOrderId(orderId);

                    // 保存订单项
                    oService.addOrderItem(orderItem);

                    if (!existingGameService.isGameAlreadyExist(userId, itemId)) {
                        existingGameService.addExistingGame(userId, itemId, item.getName());
                    }
                }
            }
        }

        //添加日志功能
        Action action = new Action();
        ItemService itemService = new ItemService();
        int itemId = Integer.parseInt(itemIds[0]);
        Item item = itemService.getItemById(itemId);
        String itemName = item.getName();
        action.setUserId(userId);
        action.setItemName(itemName);
        action.setType("生成订单");
        session.setAttribute("userAction", action);

//         //清空购物车：删除用户所有购物车项
//        cartDAO.deleteAllItemsByUserId(userId);



        request.setAttribute("msg", "订单支付成功！");
        request.getRequestDispatcher("/WEB-INF/views/order_success.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}