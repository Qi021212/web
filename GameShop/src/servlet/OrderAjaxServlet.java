package servlet;

import domain.Order;
import domain.OrderItem;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.OrderService;
import com.alibaba.fastjson.JSON;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderAjaxServlet extends HttpServlet {
    private OrderService orderService = new OrderService();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 设置请求和响应编码
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json;charset=UTF-8");

        // 解析请求数据
        String jsonData = req.getReader().lines().reduce("", String::concat);
        Map<String, Object> orderData = JSON.parseObject(jsonData, Map.class);

        // 创建订单对象
        Order order = new Order();
        order.setName((String) orderData.get("name"));
        order.setPhone((String) orderData.get("phone"));
        order.setEmail((String) orderData.get("email"));
        order.setPaytype(Integer.parseInt(orderData.get("paytype").toString()));
        order.setTotal(Double.parseDouble(orderData.get("totalAmount").toString()));

        // 保存订单并返回订单 ID
        int orderId = orderService.addOrder(order);

        // 保存订单项
        List<String> itemIds = (List<String>) orderData.get("items");
        List<OrderItem> orderItems = new ArrayList<>();
        for (String itemId : itemIds) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(orderId);
            orderItem.setItemId(Integer.parseInt(itemId));
            orderItems.add(orderItem);
        }
        orderService.addOrderItems(orderItems);

        // 返回成功响应
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("orderId", orderId);
        resp.getWriter().write(JSON.toJSONString(response));
    }

}

