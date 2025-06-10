package com.evaluacion.config;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


import java.io.IOException;
import java.util.Enumeration;

@Component
public class HeaderLogFilter implements Filter {

  private static final Logger LOG = LoggerFactory.getLogger(HeaderLogFilter.class);


    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain)
            throws IOException, ServletException {


        if (req instanceof HttpServletRequest httpRequest) {

            LOG.info("*** Request Headers ***");
            Enumeration<String> headersData = httpRequest.getHeaderNames();
            while (headersData.hasMoreElements()) {

                String name = headersData.nextElement();
                String value = httpRequest.getHeader(name);
                LOG.info("{}: {}", name, value);

            }

            LOG.info("*** End Headers ***");
        }
        filterChain.doFilter(req, resp);
    }
}
