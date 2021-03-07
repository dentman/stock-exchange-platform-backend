package com.codecool.stockexchange.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@Component

public class RequestRouteFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String contentType = request.getContentType();
        String requestURI = req.getRequestURI();

        if (isAllowedRoute(requestURI, contentType)) {
            chain.doFilter(request, response);
            return;
        }
        request.getRequestDispatcher("").forward(request, response);
    }

    private boolean isAllowedRoute(String URI, String contentType) {
        List<String> notRedirectedList = List.of(
                "/stock-base-data/list",
                "/about/en",
                "/socket",
                "/favicon.ico"
        );
        boolean isAllowed = contentType != null && contentType.startsWith("application/json") ||
                URI.equals("/") ||
                notRedirectedList.contains(URI) ||
                URI.startsWith("/static");
        return isAllowed;
    }
}
