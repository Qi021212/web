package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Item;
import model.Order;
import model.OrderItem;
import model.User;
import service.OrderService;
import service.UserService;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

public class OrderConfirmServlet extends HttpServlet {
    private OrderService oService = new OrderService();
    private UserService userService = new UserService(); // 假设你有 UserService 来获取用户信息

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从请求中获取 userId
        int userId = Integer.parseInt(request.getParameter("userId"));

        // 根据 userId 获取用户信息
        User user = userService.getUserByUserId(userId);

        //创建订单对象
        Order o = new Order();
        o.setUser(user);
        o.setDatetime(new Date());
        o.setStatus(2);// 状态：1-待支付，2-已支付等
        //o.setAmount(request.(quantities).length); // 或根据你的需求计算总商品数量
        o.setName(request.getParameter("name"));
        o.setPhone(request.getParameter("phone")); // This may need to be added to the Order model
        o.setAddress(request.getParameter("address"));
        o.setPaytype(Integer.parseInt(request.getParameter("paytype")));
        o.setTotal(Double.parseDouble(request.getParameter("totalAmount")));

        // 保存订单并获取订单ID
        int orderId = oService.addOrder(o);
        o.setId(orderId); // 确保订单对象的 ID 是最新的
//        oService.addOrder(o);

        // 插入订单项
        String[] itemIds = request.getParameterValues("itemIds");
        String[] quantities = request.getParameterValues("quantities");

        if (itemIds != null && quantities != null) {
            for (int i = 0; i < itemIds.length; i++) {
                int itemId = Integer.parseInt(itemIds[i]);
                int quantity = Integer.parseInt(quantities[i]);

                // 从数据库或其他地方获取商品信息
                Item item = oService.getItemById(itemId); // 假设您有这个方法来获取 Item
                if (item != null) {
//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setOrder(o);
//                    orderItem.setOrderId(orderId); // 设置 orderId
//                    orderItem.setItemId(itemId);
//                    orderItem.setItem(item);
//                    orderItem.setPrice(item.getPrice()); // 获取商品价格
//                    orderItem.setAmount(quantity);
                    OrderItem orderItem = new OrderItem(itemId, item, item.getPrice(), quantity, o);

//                    OrderItem orderItem = new OrderItem();
//                    orderItem.setOrderId(orderId);
//                    orderItem.setItemId(Integer.parseInt(itemIds[i]));
//                    orderItem.setPrice(Double.parseDouble(request.getParameter("price_" + itemIds[i]))); // 假设你在表单中有价格
//                    orderItem.setAmount(Integer.parseInt(quantities[i]));
//                    oService.addOrderItem(orderItem);

                    // 保存订单项
                    oService.addOrderItem(orderItem);
                }
            }
        }

        request.setAttribute("msg", "订单支付成功！");
        request.getRequestDispatcher("/WEB-INF/views/order_success.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
