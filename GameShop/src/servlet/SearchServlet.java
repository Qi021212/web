package servlet;

import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CatalogService;

import java.io.IOException;
import java.util.List;

public class SearchServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String SEARCH_FORM = "/WEB-INF/jsp/catalog/main.jsp";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword=req.getParameter("keyword");
        catalogService = new CatalogService();
        HttpSession session = req.getSession();
        List<Item> itemList = catalogService.searchItemList(keyword);
        session.setAttribute("itemList", itemList);
        req.getRequestDispatcher(SEARCH_FORM).forward(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }
}
