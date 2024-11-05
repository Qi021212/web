package servlet;

import domain.Cart;
import domain.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CartService;

import java.io.IOException;

public class CartBuyServlet extends HttpServlet {
    private CartService cartService = new CartService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        String itemsId = req.getParameter("itemId");
        HttpSession session = req.getSession();
        User user = (User) session.getAttribute("user");

        if (user == null) {
            resp.getWriter().print("login");
            return;
        }

        int userId = user.getId();
        if ("buy".equals(action)) {
            String quantityStr = req.getParameter("quantity");
            double price = Double.parseDouble(req.getParameter("price"));

            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setItemId(Integer.parseInt(itemsId));
            cart.setQuantity(Integer.parseInt(quantityStr));
            cart.setPrice(price);
            cart.setAmount(price * cart.getQuantity());
            cartService.addToCart(cart);
            resp.getWriter().print("ok");
        } else if ("lessen".equals(action)) {
            // 处理减少数量的逻辑
            cartService.updateCartItem(userId, Integer.parseInt(itemsId), -1);
            resp.getWriter().print("ok");
        } else if ("delete".equals(action)) {
            // 处理删除商品的逻辑
            cartService.deleteCartItem(userId, Integer.parseInt(itemsId));
            resp.getWriter().print("ok");
        }

//        String itemsId = req.getParameter("itemsId");
//        String quantityStr = req.getParameter("quantity");
//        double price = Double.parseDouble(req.getParameter("price")); // 假设前端发送价格
//
//        Cart cart = new Cart();
//        HttpSession session = req.getSession();
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            resp.getWriter().print("login");
//            return;
//        }
//
//        cart.setUserId(user.getId());
//        cart.setItemId(Integer.parseInt(itemsId));
//        cart.setQuantity(Integer.parseInt(quantityStr));
//        cart.setPrice(price);
//        cart.setAmount(price * cart.getQuantity()); // 计算总金额
//
//        // 调用 CartService 的 addToCart 方法
//        cartService.addToCart(cart);
//
//        // 返回结果
//        resp.getWriter().print("ok");

//        int result = cartService.addToCart(cart);
//
//        if(result > 0) {
//            resp.getWriter().print("ok");
//        }else{
//            resp.getWriter().print("fail");
//        }
    }
}
