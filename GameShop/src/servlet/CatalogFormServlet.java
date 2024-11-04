package servlet;

import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CatalogService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CatalogFormServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String Category_Form = "/WEB-INF/jsp/catalog/main.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String categoryId = req.getParameter("categoryId");
        catalogService =new CatalogService();
        HttpSession session = req.getSession();
        List<Item> itemList;
//        Category category = catalogService.getCategoryById(categoryId);
        if(categoryId.equals("all")){
            itemList = catalogService.getAllItem();

        }
        else{
            itemList = catalogService.getItemByCategory(categoryId);
        }
//        session.setAttribute("category", category);
        session.setAttribute("itemList", itemList);
        req.getRequestDispatcher(Category_Form).forward(req, resp);
    }

}
