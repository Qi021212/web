package servlet;

import domain.Action;
import domain.Cart;
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.impl.CartDAOImpl;
import service.ItemService;

import java.io.IOException;

public class AddToCartServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 从会话中获取 userId
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("loginForm");
            return;
        }

        // 获取请求参数
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        double price = Double.parseDouble(request.getParameter("price"));

        // 检查商品是否已在购物车中
        CartDAOImpl cartDAO = new CartDAOImpl();
        Cart existingCartItem = cartDAO.getCartItem(userId, itemId);

        if (existingCartItem == null) {
            // 商品不在购物车中，插入新记录
            Cart cart = new Cart(userId, itemId, price, 1, 1);
            cartDAO.addToCart(cart);
        } else {
            // 商品已在购物车中，更新 `in_cart` 为 1，`add_count` 增加
            existingCartItem.setInCart(1);
            existingCartItem.setAddCount(existingCartItem.getAddCount() + 1);
            cartDAO.updateCartItem(existingCartItem);
        }

//        Cart cart = new Cart(userId, itemId, price);
//        cartDAO.addToCart(cart);

        //日志功能
        Action action=new Action();
        ItemService itemService = new ItemService();
        Item item = itemService.getItemById(itemId);
        String itemName = item.getName();
        action.setUserId(userId);
        action.setItemName(itemName);
        action.setType("添加到购物车");
        session.setAttribute("userAction", action);

        // 跳转到购物车成功页面
        request.setAttribute("message", "添加成功！");
        request.getRequestDispatcher("/WEB-INF/views/cartSuccess.jsp").forward(request, response);
    }
}
