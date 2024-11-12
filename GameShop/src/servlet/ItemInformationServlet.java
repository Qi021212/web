package servlet;

import domain.Action;
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import service.CatalogService;

import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ItemInformationServlet extends HttpServlet {
    private CatalogService catalogService;
    private static final String Item_Information_Form = "/WEB-INF/jsp/catalog/itemInformation.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemPicture = req.getParameter("itemPicture");
        catalogService = new CatalogService();
        HttpSession session = req.getSession();
        List<Item> items=catalogService.getItemBySrc(itemPicture);
        session.setAttribute("items", items);


        Integer userId = (Integer) session.getAttribute("userId");
        if (userId!=null) {
            Action action = new Action();
            action.setUserId(userId);
            action.setItemName(items.get(0).getName());
            action.setType("浏览商品");
            session.setAttribute("userAction", action);
        }


        req.getRequestDispatcher(Item_Information_Form).forward(req, resp);
    }
}

