/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icsa.filters;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Алексей
 */
@WebFilter(filterName = "COR",
urlPatterns = {"/*"})
public class HeaderFilter implements javax.servlet.Filter {

    /*
    static class ICSARequestWrapper extends HttpServletRequestWrapper{
        
        public ICSARequestWrapper(HttpServletRequest request) {
            super(request);
        }
        
        public String getHeader(String name) {
            String header = super.getHeader(name);
            return (header != null) ? header : super.getParameter(name); // Note: you can't use getParameterValues() here.
        }

        public Enumeration getHeaderNames() {
            List<String> names = Collections.list(super.getHeaderNames());
            names.add("");
            return Collections.enumeration(names);
        }
        
    }*/


    @Override
    public void init(FilterConfig fc) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }
    
}
