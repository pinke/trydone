package com.trydone.core.filters;

import java.io.IOException;
import javax.servlet.*;

public class SetCharacterEncodingFilter implements Filter
{

    public SetCharacterEncodingFilter()
    {
        encoding = null;
        filterConfig = null;
        ignore = true;
    }

    public void destroy()
    {
        encoding = null;
        filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException
    {
        if(ignore || request.getCharacterEncoding() == null)
        {
            String encoding = selectEncoding(request);
            if(encoding != null)
                request.setCharacterEncoding(encoding);
        }
        try
        {
            chain.doFilter(request, response);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
        }
    }

    public void init(FilterConfig filterConfig)
        throws ServletException
    {
        this.filterConfig = filterConfig;
        encoding = filterConfig.getInitParameter("encoding");
        String value = filterConfig.getInitParameter("ignore");
        if(value == null)
            ignore = true;
        else
        if(value.equalsIgnoreCase("true"))
            ignore = true;
        else
        if(value.equalsIgnoreCase("yes"))
            ignore = true;
        else
            ignore = false;
    }

    protected String selectEncoding(ServletRequest request)
    {
        return encoding;
    }
    protected String encoding;
    protected FilterConfig filterConfig;
    protected boolean ignore;
}