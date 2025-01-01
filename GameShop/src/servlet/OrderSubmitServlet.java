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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            resp.sendRedirect("loginForm");
            return;
        }

        // 从Session获取选中的商品
        List<Cart> selectedItems = (List<Cart>) session.getAttribute("selectedItems");
        double totalAmount = (Double) session.getAttribute("totalAmount");

        String[] selectedItemIds = req.getParameterValues("selectedItemIds");


        // 将购物车商品和总金额传递到请求中
        req.setAttribute("selectedItemIds", selectedItemIds);
        // 将商品列表和总金额传递到请求中
        req.setAttribute("selectedItems", selectedItems);
        req.setAttribute("totalAmount", totalAmount);

        // 跳转到订单确认页面
        req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}

//        // 获取购物车商品信息
//        String[] itemIds = req.getParameterValues("itemIds");
//        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
//        double totalAmount = 0;
//        for (Cart cartItem : cartItems) {
//            totalAmount += cartItem.getPrice(); // 计算总金额
//        }
//

//        req.setAttribute("cartItems", cartItems);
//        req.setAttribute("totalAmount", totalAmount);
//
//        // 由于使用了硬编码，这里直接重定向到提交页面
//        req.getRequestDispatcher("/WEB-INF/views/order_submit.jsp").forward(req, resp);


