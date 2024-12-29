package servlet;

import com.alibaba.fastjson.JSON;
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CatalogService;
import java.io.PrintWriter;

import java.io.IOException;
import java.util.List;

public class AutoCompleteServlet extends HttpServlet {
    private CatalogService catalogService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String keyword=req.getParameter("keyword");
        catalogService = new CatalogService();
        List<Item> itemList = catalogService.searchItemList(keyword);

        String result = JSON.toJSONString(itemList);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(result);
    }
}
