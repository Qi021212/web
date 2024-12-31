package servlet;

import com.alibaba.fastjson.JSON;
import domain.Item;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CatalogService;
import service.ItemService;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class FloatingWindowServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String itemSrc=req.getParameter("itemSrc");
        CatalogService catalogService =new CatalogService();
        List<Item> items = catalogService.getItemBySrc(itemSrc);

        String result = JSON.toJSONString(items);

        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        out.println(result);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
