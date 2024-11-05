package servlet;

import domain.Cart;
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.CartDAO;
import persistence.ItemDAO;

import java.io.IOException;
import java.util.List;

public class ItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = itemDAO.getAllItems();//这个方法用不了，有点问题。在这里修改直接硬编码，将已有的items信息传下去

        request.setAttribute("items", items);
        request.getRequestDispatcher("/WEB-INF/views/itemList.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取参数
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price"));
        //double price = 0.0; // 从 item 获取价格（这里假设是固定的，可以从 items 获取）
        double amount = price * quantity; // 计算总金额

        // 假设当前用户的 userId 为 1（你可以根据实际情况动态获取）
        int userId = 1; // 需要从 session 或数据库获取当前用户 ID

        // 创建购物车对象并添加到数据库
        Cart cart = new Cart(userId, itemId, quantity, amount, price);
        CartDAO cartDAO = new CartDAO();
        cartDAO.addToCart(cart);

        // 返回成功的响应
        request.setAttribute("message", "添加成功！");
        request.getRequestDispatcher("/WEB-INF/views/cartSuccess.jsp").forward(request, response);
    }
}
