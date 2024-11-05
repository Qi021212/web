package servlet;

import dao.OrderDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.User;
import service.OrderService;
import model.Order;

import java.io.IOException;

public class OrderSubmitServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取购物车信息
        String[] itemIds = req.getParameterValues("itemIds");
        String[] quantities = req.getParameterValues("quantities");
        String totalAmount = req.getParameter("totalAmount");

        // 将这些信息放入请求中，传递到订单提交页面
        req.setAttribute("itemIds", itemIds);
        req.setAttribute("quantities", quantities);
        req.setAttribute("totalAmount", totalAmount);

        // 由于使用了硬编码，这里直接重定向到提交页面
        req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);
        //        if(req.getSession().getAttribute("user") != null) {
//            req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);
//        }else{
//            req.setAttribute("failMsg","请登录后，再提交订单！");
//            //!!!这里可能不太适合请求转发
//            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
//        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

//private OrderService orderService = new OrderService();
//private static final int HARD_CODED_USER_ID = 1; // 硬编码的用户 ID

//// 由于使用了硬编码，这里直接重定向到提交页面
//        req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);
////        if(req.getSession().getAttribute("user") != null) {
////            req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);
////        }else{
////            req.setAttribute("failMsg","请登录后，再提交订单！");
////            //!!!这里可能不太适合请求转发
////            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
////        }

// 获取订单信息
//        int userId = HARD_CODED_USER_ID; // 使用硬编码的用户ID
//        double totalAmount = Double.parseDouble(req.getParameter("totalAmount"));

//        String name = req.getParameter("name");
//        String phone = req.getParameter("phone");
//        String address = req.getParameter("address");
//        int paytype = Integer.parseInt(req.getParameter("paytype"));

//        // 创建订单对象
//        Order order = new Order();
//        order.setUser(new User(userId));
//        order.setTotal(totalAmount);
//        order.setName(name);
//        order.setPhone(phone);
//        order.setAddress(address);
//        order.setPaytype(paytype);
//        order.setStatus(0); // 订单初始状态
//
//        // 调用服务方法插入订单
//        orderService.addOrder(order);
//
//        // 订单提交后重定向到订单确认页面
//        req.setAttribute("msg", "订单提交成功！");

//        if(req.getSession().getAttribute("user") != null) {
//            //获取订单信息
//            int userId = Integer.parseInt(req.getParameter("userId"));
//            double totalAmount = Double.parseDouble(req.getParameter("totalAmount"));
//
//            String name = req.getParameter("name"); // 需要从 order_submit.jsp 获取
//            String phone = req.getParameter("phone"); // 需要从 order_submit.jsp 获取
//            String address = req.getParameter("address"); // 需要从 order_submit.jsp 获取
//            int paytype = Integer.parseInt(req.getParameter("paytype")); // 需要从 order_submit.jsp 获取
//
//            // 创建订单对象
//            Order order = new Order();
//            order.setUser(new User(userId)); // 假设User对象中设置ID
//            order.setTotal(totalAmount);
//            order.setName(name);
//            order.setPhone(phone);
//            order.setAddress(address);
//            order.setPaytype(paytype);
//            order.setStatus(0); // 订单初始状态
//
//            // 调用服务方法插入订单
//            orderService.addOrder(order);
//
//            // 订单提交后重定向到订单确认页面
//            req.setAttribute("msg", "订单提交成功！");
//            req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);
//        }else{
//            req.setAttribute("failMsg","请登录后，再提交订单！");
//            //!!!这里可能不太适合请求转发
//            req.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(req, resp);
