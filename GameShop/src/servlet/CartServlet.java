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
import java.util.ArrayList;
import java.util.List;

public class CartServlet extends HttpServlet {
    CartService cartService = new CartService();
    UserService userService = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        List<Cart> cartItems1 = cartService.getCartItemsByUserId(userId);

        if (userId == null) {
            response.sendRedirect("loginForm");
            return;
        }

        String action = request.getParameter("action");
        String itemIdStr = request.getParameter("itemId");

        switch (action) {
            case "delete":
                // 删除商品
                int itemId = Integer.parseInt(itemIdStr);
                cartService.deleteCartItem(userId, itemId);
                // 获取更新后的购物车数据
                List<Cart> cartItems = cartService.getCartItemsByUserId(userId);
                double totalAmount = 0;
                for (Cart cartItem : cartItems) {
                    if (cartItem.getIsSelected() == 1) {
                        totalAmount += cartItem.getPrice();
                    }
                }

                // 返回更新后的总金额
                response.setContentType("application/json");
                response.getWriter().write("{\"totalAmount\":" + totalAmount + "}");
                return;
            case "submitOrder":
                // 获取用户信息
                User user = userService.findUserById(userId); // 获取用户信息
                List<Cart> cartItems2 = cartService.getCartItemsByUserId(userId);

                // 获取选中的商品并计算总金额
                List<Cart> selectedItems = new ArrayList<>();
                List<Integer> selectedItemIds = new ArrayList<>(); // 用来存储选中的商品ID
                double submitTotalAmount = 0;
                for (Cart cartItem2 : cartItems2) {
                    if (cartItem2 != null && cartItem2.getIsSelected() == 1) {
                        selectedItems.add(cartItem2);
                        selectedItemIds.add(cartItem2.getItemId());
                        submitTotalAmount += cartItem2.getPrice();
                    }
                }
                // 将选中的商品和总金额存入session，传递给订单确认页面
                session.setAttribute("selectedItems", selectedItems);
                session.setAttribute("selectedItemIds", selectedItemIds);
                session.setAttribute("totalAmount", submitTotalAmount);


                // 返回更新后的总金额
                request.setAttribute("user", user);
                response.setContentType("application/json");
                response.getWriter().write("{\"selectedItems\":" + selectedItems + "}");
                response.getWriter().write("{\"totalAmount\":" + submitTotalAmount + "}");
                return;


//                // 跳转到订单确认页面
//                response.sendRedirect("order_submit");

//                // 将总金额存入 session 或 request
//                request.getSession().setAttribute("totalAmount", totalAmount);
//
//                // 获取用户信息
//                User user = userService.findUserById(userId); // 获取用户信息
//
//                // 设置用户信息到请求中
//                request.setAttribute("user", user);
//                request.setAttribute("cartItems", cartItems);
//                request.setAttribute("totalAmount", totalAmount);
//
//                // 跳转到订单提交页面
//                response.sendRedirect(request.getContextPath() + "/order_submit");
//                break;
            case "addToCart":
                // 添加商品到购物车
                int itemId1 = Integer.parseInt("itemId");
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                double price = Double.parseDouble(request.getParameter("price"));
                double totalPrice = price * quantity; // 计算总价
                Cart cart = new Cart(userId, itemId1, quantity, totalPrice, price);
                CartDAOImpl cartDAO = new CartDAOImpl();
                int result = cartDAO.insertCart(cart);
                if (result > 0) {
                    response.sendRedirect("cartSuccess.jsp");
                } else {
                    response.getWriter().print("fail");
                }
                return;
            case "updateSelection":
                // 更新选中状态
                int itemIdToUpdate = Integer.parseInt(request.getParameter("itemId"));
                int isSelected = Integer.parseInt(request.getParameter("isSelected"));
                cartService.updateCartItemSelection(userId, itemIdToUpdate, isSelected);

                // 计算总金额
                List<Cart> updatedCartItems = cartService.getCartItemsByUserId(userId);
                double updatedTotalAmount = 0;
                for (Cart cartItem : updatedCartItems) {
                    if (cartItem.getIsSelected() == 1) { // 只计算选中的商品
                        updatedTotalAmount += cartItem.getPrice();
                    }
                }

                // 返回更新后的总金额
                response.setContentType("application/json");
                response.getWriter().write("{\"totalAmount\":" + updatedTotalAmount + "}");
                return;
        }
//        response.sendRedirect("cart");
//        // 在所有操作完成后，转发回购物车页面，避免直接使用 sendRedirect
//        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
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

