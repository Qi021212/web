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
import com.alibaba.fastjson.JSONObject;


import java.io.IOException;
import java.util.*;

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
                List<Map<String, Object>> selectedItems = new ArrayList<>();
                List<Integer> selectedItemIds = new ArrayList<>(); // 用来存储选中的商品ID
                double submitTotalAmount = 0;
                for (Cart cartItem2 : cartItems2) {
                    if (cartItem2 != null && cartItem2.getIsSelected() == 1) {
//                        selectedItems.add(cartItem2);
                        Map<String, Object> itemMap = new HashMap<>();
                        itemMap.put("id", cartItem2.getItemId());
                        itemMap.put("name", cartItem2.getItem().getName());  // 假设 Cart 对象有 item 属性，里面有商品名称
                        itemMap.put("price", cartItem2.getPrice());

                        selectedItems.add(itemMap);
                        selectedItemIds.add(cartItem2.getItemId());
                        submitTotalAmount += cartItem2.getPrice();
                    }
                }

                // 将选中的商品和总金额存入session，传递给订单确认页面
                session.setAttribute("selectedItems", selectedItems);
                session.setAttribute("selectedItemIds", selectedItemIds);
                session.setAttribute("totalAmount", submitTotalAmount);

                // 创建一个JSON对象，将选中的商品和总金额作为JSON返回
                JSONObject responseJson = new JSONObject();
                responseJson.put("selectedItems", selectedItems);
                responseJson.put("totalAmount", submitTotalAmount);

                // 调试输出
                System.out.println("Selected Items: " + selectedItems);
                System.out.println("Total Amount: " + submitTotalAmount);

                // 返回JSON数据给前端
                response.setContentType("application/json");
                response.getWriter().write(responseJson.toString());
                return;

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
        request.getRequestDispatcher("/WEB-INF/jsp/views/cart.jsp").forward(request, response);
    }
}

