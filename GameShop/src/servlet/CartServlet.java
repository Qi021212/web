package servlet;

import domain.Cart;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.CartDAO;
import service.CartService;
import service.UserService;

import java.io.IOException;
import java.util.List;

public class CartServlet extends HttpServlet {
    CartService cartService = new CartService();
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("loginForm");
            return;
        }

        String action = request.getParameter("action");
        String itemIdStr = request.getParameter("itemId");

        if (itemIdStr == null || action == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST);
            return;
        }

        int itemId = Integer.parseInt(itemIdStr);

        switch (action) {
            case "delete":
                // 删除商品
                cartService.deleteCartItem(userId, itemId);
                break;
            case "submitOrder":
                // 获取购物车商品信息
                List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
                double totalAmount = 0;
                for (Cart cartItem : cartItems) {
                    totalAmount += cartItem.getPrice(); // 计算总金额
                }

                // 将总金额存入 session 或 request
                request.getSession().setAttribute("totalAmount", totalAmount);

                // 获取用户信息
                User user = userService.findUserById(userId); // 获取用户信息

                // 设置用户信息到请求中
                request.setAttribute("user", user);
                request.setAttribute("cartItems", cartItems);
                request.setAttribute("totalAmount", totalAmount);

                // 跳转到订单提交页面
                response.sendRedirect(request.getContextPath() + "/order_submit");
                break;
            case "addToCart":
                // 添加商品到购物车
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                double price = Double.parseDouble(request.getParameter("price"));
                Cart cart = new Cart(userId, itemId, quantity, price * quantity, price);
                CartDAO cartDAO = new CartDAO();
                int result = cartDAO.insertCart(cart);
                if (result > 0) {
                    response.sendRedirect("cartSuccess.jsp");
                } else {
                    response.getWriter().print("fail");
                }
                return;
        }
        response.sendRedirect("cart");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("loginForm");
            return;
        }

        // 获取用户的购物车商品信息
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId); // 获取购物车商品

        // 计算总金额
        double totalAmount = 0;
        for (Cart cartItem : cartItems) {
            totalAmount += cartItem.getPrice(); // 每个商品只有一个价格
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalAmount", totalAmount); // 将总金额传递到 JSP
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
}

