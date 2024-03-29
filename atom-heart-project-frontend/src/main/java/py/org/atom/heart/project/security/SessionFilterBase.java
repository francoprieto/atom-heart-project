package py.org.atom.heart.project.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class SessionFilterBase implements Filter{
	
	protected String page;
	
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        String user = req.getUserPrincipal() == null ? null : req.getUserPrincipal().getName();
        if(user == null )
            res.sendRedirect(req.getContextPath() + this.page);
        else
            chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
    }

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
}
