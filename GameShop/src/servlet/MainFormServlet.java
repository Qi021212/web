package servlet;
//主页页面跳转的servlet
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CatalogService;

import java.io.IOException;
import java.util.List;

public class MainFormServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String MAIN_Form = "/WEB-INF/jsp/catalog/main.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        catalogService =new CatalogService();
        HttpSession session = req.getSession();
        List<Item> itemList= catalogService.getAllItem();
        session.setAttribute("itemList", itemList);
        req.getRequestDispatcher(MAIN_Form).forward(req, resp);
    }

}
