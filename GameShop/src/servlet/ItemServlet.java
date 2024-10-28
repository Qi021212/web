package servlet;

import dao.ItemDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Item;

import java.io.IOException;
import java.util.List;

public class ItemServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ItemDAO itemDAO = new ItemDAO();
//        List<Item> items = itemDAO.getAllItems();
        List<Item> items = null;

        try {
            items = itemDAO.getAllItems();
            System.out.println("查询到的商品数量: " + items.size()); // 打印查询结果数量
        } catch (Exception e) {
            System.out.println("获取商品时出错: " + e.getMessage());
            e.printStackTrace();
        }

        request.setAttribute("items", items);
        request.getRequestDispatcher("/WEB-INF/views/itemList.jsp").forward(request, response);
    }
}
