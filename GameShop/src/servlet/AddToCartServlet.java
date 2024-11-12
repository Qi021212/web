package servlet;

import domain.Action;
import domain.Cart;
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import persistence.CartDAO;
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

        // 创建购物车对象并保存
        Cart cart = new Cart(userId, itemId, price);
        CartDAO cartDAO = new CartDAO();
        cartDAO.addToCart(cart);

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
