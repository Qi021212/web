package servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CartService;
import service.OrderService;
import domain.Cart;
import domain.Order;

import java.io.IOException;
import java.util.List;

public class OrderSubmitServlet extends HttpServlet {
    OrderService orderService = new OrderService();
    CartService cartService = new CartService();

//    int userId=1;


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        // 获取购物车信息
//        String[] itemIds = req.getParameterValues("itemIds");
//        String totalAmount = req.getParameter("totalAmount");
//
//        // 将这些信息放入请求中，传递到订单提交页面
//        req.setAttribute("itemIds", itemIds);
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("loginForm");
            return;
        }

        // 获取购物车商品信息
        String[] itemIds = req.getParameterValues("itemIds");
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
        double totalAmount = 0;
        for (Cart cartItem : cartItems) {
            totalAmount += cartItem.getPrice(); // 计算总金额
        }

        // 将购物车商品和总金额传递到请求中
        req.setAttribute("itemIds", itemIds);
        req.setAttribute("cartItems", cartItems);
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

