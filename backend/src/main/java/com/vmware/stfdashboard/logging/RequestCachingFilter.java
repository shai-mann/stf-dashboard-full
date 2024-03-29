package com.vmware.stfdashboard.logging;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * A {@link javax.servlet.Filter} that logs API requests.
 * @see CachedServletInputStream
 * @see CachedHttpServletRequest
 */
@Order(value = Ordered.HIGHEST_PRECEDENCE)
@Component
@WebFilter(filterName = "RequestCachingFilter", urlPatterns = "/*")
public class RequestCachingFilter extends OncePerRequestFilter {

    private final static Logger LOGGER = LoggerFactory.getLogger(RequestCachingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        CachedHttpServletRequest cachedHttpServletRequest = new CachedHttpServletRequest(request);
        LOGGER.info("[{}]: '{}'", request.getMethod(),
                request.getRequestURI().substring(request.getContextPath().length()));

        String data = IOUtils.toString(cachedHttpServletRequest.getInputStream(),
                StandardCharsets.UTF_8);
        if (!data.isBlank()) LOGGER.info("REQUEST DATA: " + data);

        filterChain.doFilter(cachedHttpServletRequest, response);
    }
}
