package servlet;

import domain.Cart;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.impl.CartDAOImpl;
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

        int itemId = Integer.parseInt(itemIdStr);

        switch (action) {
            // 每当点击某个复选框时
            case "updateSelection":
                String selectedItemIdStr = request.getParameter("selectedItem");
                if (selectedItemIdStr != null) {
                    int selectedItemId = Integer.parseInt(selectedItemIdStr);

                    // 获取复选框的选中状态
                    String isSelectedStr = request.getParameter("isSelected");
                    int isSelected = (isSelectedStr != null && isSelectedStr.equals("on")) ? 1 : 0;

                    // 更新数据库中的选中状态
                    cartService.updateCartSelection(userId, selectedItemId, isSelected);
                }
                break;
            //每当删除某个在购物车中的商品时
            case "delete":
                // 删除商品
                cartService.deleteCartItem(userId, itemId);
                break;
            case "submitOrder":
                // 获取购物车商品信息
                List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
                double totalAmount = 0;
                for (Cart cartItem : cartItems) {
                    if (cartItem.getIsSelected() == 1) { // 只计算选中的商品
                        totalAmount += cartItem.getPrice();
                    }
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
                double totalPrice = price * quantity; // 计算总价
                Cart cart = new Cart(userId, itemId, quantity, totalPrice, price);
                CartDAOImpl cartDAO = new CartDAOImpl();
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
        List<Cart> cartItems = cartService.getCartItemsByUserId(userId);

        // 计算总金额
        double totalAmount = 0;
        for (Cart cartItem : cartItems) {
            if (cartItem.getIsSelected() == 1) {
                totalAmount += cartItem.getPrice();
            }
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalAmount", totalAmount);
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }
}

