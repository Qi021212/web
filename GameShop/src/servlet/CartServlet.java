package servlet;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;


import java.io.IOException;

public class CartServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = 1; // 假设用户ID为1，实际应用中应从 session 获取
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        double price = Double.parseDouble(request.getParameter("price")); // 价格应从 item 传递

        CartDAO cartDAO = new CartDAO();
        cartDAO.addToCart(userId, itemId, quantity, price);
        response.sendRedirect("items"); // Redirect back to items page
    }

//    @Override
//    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        this.doPost(req, resp);
//    }

    //    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        int userId = (int) session.getAttribute("userId"); // 假设已登录并存储用户ID
//        int itemId = Integer.parseInt(request.getParameter("itemId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        double price = Double.parseDouble(request.getParameter("price")); // 价格应从 item 传递
//
//        CartDAO cartDAO = new CartDAO();
//        cartDAO.addToCart(userId, itemId, quantity,price);
//
////        response.sendRedirect("items");
//
//        // 计算总金额
//        double totalPrice = quantity ; /* 获取商品单价，类似上面逻辑 */;
//
//        request.setAttribute("message", "商品添加成功");
//        request.setAttribute("quantity", quantity);
//        request.setAttribute("totalPrice", totalPrice);
//        request.getRequestDispatcher("/WEB-INF/views/cartSuccess.jsp").forward(request, response);
//    }
}

