package servlet;

import dao.CartDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import model.Cart;
import model.User;
import service.CartService;


import java.io.IOException;
import java.util.List;

public class CartServlet extends HttpServlet {
    private static final int HARD_CODED_USER_ID = 1; // 硬编码的用户 ID
    CartService cartService = new CartService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        switch (action) {
            case "add":
                // 增加数量
                cartService.updateCartItem(HARD_CODED_USER_ID, itemId, 1);
                break;
            case "reduce":
                // 减少数量
                cartService.updateCartItem(HARD_CODED_USER_ID, itemId, -1);
                break;
            case "delete":
                // 删除商品
                cartService.deleteCartItem(HARD_CODED_USER_ID, itemId);
                break;
            case "addToCart":
                // 添加商品到购物车
                int quantity = Integer.parseInt(request.getParameter("quantity"));
                double price = Double.parseDouble(request.getParameter("price"));
                Cart cart = new Cart(HARD_CODED_USER_ID, itemId, quantity, price * quantity, price);
                CartDAO cartDAO = new CartDAO();
                int result = cartDAO.insertCart(cart);
                if (result > 0) {
                    response.sendRedirect("cartSuccess.jsp");
                } else {
                    response.getWriter().print("fail");
                }
                return;
        }

        // 重定向到购物车页面
        response.sendRedirect("cart.jsp");
    }

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int itemId = Integer.parseInt(request.getParameter("itemId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        double price = Double.parseDouble(request.getParameter("price")); // 获取商品价格
//        double amount = price * quantity; // 计算总金额
//
//        // 创建购物车对象
//        Cart cart = new Cart(HARD_CODED_USER_ID, itemId, quantity, amount, price);
//
//        // 将商品添加到购物车或更新数量
//        cartService.addToCart(cart);
//
//        // 跳转到购物车成功页面
//        request.getRequestDispatcher("/WEB-INF/views/cartSuccess.jsp").forward(request, response);
//    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Cart> cartItems = cartService.getCartItemsByUserId(HARD_CODED_USER_ID); // 获取购物车商品
        double totalAmount = 0;
        for (Cart item : cartItems) {
            totalAmount += item.getAmount(); // 假设 Cart 类有 getAmount() 方法
        }

        request.setAttribute("cartItems", cartItems);
        request.setAttribute("totalAmount", totalAmount); // 将总金额传递到 JSP
        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }



    // 更新购物车商品数量
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));
        int quantityChange = Integer.parseInt(request.getParameter("quantityChange")); // 增加或减少的数量

        cartService.updateCartItem(HARD_CODED_USER_ID, itemId, quantityChange);

        // 更新后重定向到购物车页面
        response.sendRedirect("cart");
    }

    // 删除购物车中的商品
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int itemId = Integer.parseInt(request.getParameter("itemId"));

        cartService.deleteCartItem(HARD_CODED_USER_ID, itemId);

        // 删除后重定向到购物车页面
        response.sendRedirect("cart");
    }


    //    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        HttpSession session = request.getSession();
//        User user = (User) session.getAttribute("user");
//
//        if (user == null) {
//            response.getWriter().print("login");
//            return;
//        }
//
//        // 获取购物车商品
//        List<Cart> cartItems = cartService.getCartItemsByUserId(HARD_CODED_USER_ID);
//        double totalAmount = 0;
//
//        // 计算总金额
//        for (Cart item : cartItems) {
//            totalAmount += item.getAmount(); // 假设 Cart 类有 getAmount() 方法
//        }
//
//        // 设置请求属性
//        request.setAttribute("cartItems", cartItems);
//        request.setAttribute("totalAmount", totalAmount); // 将总金额传递到 JSP
//
//        // 跳转到购物车页面
//        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//    }

//    private static final int HARD_CODED_USER_ID = 1; // 硬编码的用户 ID
//
//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int itemId = Integer.parseInt(request.getParameter("itemId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        double price = Double.parseDouble(request.getParameter("price")); // 获取商品价格
//        double amount = price * quantity; // 计算总金额
//
//        CartDAO cartDAO = new CartDAO();
//        // 创建购物车对象并添加到数据库
//        Cart cart = new Cart(HARD_CODED_USER_ID, itemId, quantity, amount, price);
//        cartDAO.addToCart(cart);
////        cartDAO.addToCart(HARD_CODED_USER_ID, itemId, quantity, price); // 添加到购物车
//
//        request.getRequestDispatcher("/WEB-INF/views/cartSuccess.jsp").forward(request, response);
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        CartDAO cartDAO = new CartDAO();
//        List<Cart> cartItems = cartDAO.getCartItemsByUserId(HARD_CODED_USER_ID); // 获取购物车商品
//        double totalAmount = 0;
//        for (Cart item : cartItems) {
//            totalAmount += item.getAmount(); // 假设 Cart 类有 getAmount() 方法
//        }
//
//        request.setAttribute("cartItems", cartItems);
//        request.setAttribute("totalAmount", totalAmount); // 将总金额传递到 JSP
//        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//    }

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 获取商品信息和用户ID
//        int itemId = Integer.parseInt(request.getParameter("itemId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        double price = 0.0; // 价格应根据实际情况获取
//        double amount = price * quantity; // 计算总金额
//
//        // 获取当前用户的 userId，假设在 session 中存储
//        HttpSession session = request.getSession();
//        int userId = (int) session.getAttribute("userId"); // 需要在用户登录时设置 userId
//
//        // 创建购物车对象并添加到数据库
//        Cart cart = new Cart(userId, itemId, quantity, amount, price);
//        CartDAO cartDAO = new CartDAO();
//        cartDAO.addToCart(cart);
//
//        // 返回成功的响应
//        request.setAttribute("message", "添加成功！");
//        request.getRequestDispatcher("/WEB-INF/views/cartSuccess.jsp").forward(request, response);
//    }
//
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        // 获取当前用户的 userId，假设在 session 中存储
//        HttpSession session = request.getSession();
//        int userId = (int) session.getAttribute("userId"); // 需要在用户登录时设置 userId
//
//        CartDAO cartDAO = new CartDAO();
//        List<Cart> cartItems = cartDAO.getCartItems(userId); // 获取购物车中的商品
//
//        request.setAttribute("cartItems", cartItems);
//        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
//    }

//    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        int userId = 1; // 假设用户ID为1，实际应用中应从 session 获取
//        int itemId = Integer.parseInt(request.getParameter("itemId"));
//        int quantity = Integer.parseInt(request.getParameter("quantity"));
//        double price = Double.parseDouble(request.getParameter("price")); // 价格应从 item 传递
//
//        CartDAO cartDAO = new CartDAO();
//        cartDAO.addToCart(userId, itemId, quantity, price);
//        response.sendRedirect("items"); // Redirect back to items page
//    }

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

