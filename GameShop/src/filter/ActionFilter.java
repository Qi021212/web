package filter;

import domain.Action;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import service.ActionService;

import java.io.IOException;

public class ActionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpSession session = httpRequest.getSession();
        Action action = (Action) session.getAttribute("userAction");

        if (action != null) {
            ActionService actionService=new ActionService();
            actionService.insertAction(action);
            session.removeAttribute("userAction");
            System.out.println("...");
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
