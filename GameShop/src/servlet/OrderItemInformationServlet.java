package servlet;

import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.ItemService;

import java.io.IOException;

public class OrderItemInformationServlet extends HttpServlet {
    private ItemService itemService = new ItemService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String itemIdParam = request.getParameter("itemId");
        if (itemIdParam != null) {
            int itemId = Integer.parseInt(itemIdParam);
            Item item = itemService.getItemById(itemId);
            request.setAttribute("item", item);
            request.getRequestDispatcher("/WEB-INF/views/orderItemInformation.jsp").forward(request, response);
        } else {
            response.sendRedirect("order_list");  // 如果 itemId 不存在，重定向到订单列表
        }
    }
}
