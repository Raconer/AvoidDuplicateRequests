package com.duplicate.requests.avoid.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.duplicate.requests.avoid.service.SignService;
import com.duplicate.requests.avoid.utils.JwtUtil;

import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private static final String BEARER_START = "Bearer ";

    private SignService signService;

    private static final List<String> EXCLUDE_URL = Collections
            .unmodifiableList(Arrays.asList("^/(?!api).*", "^/(api)/(sign)(/((refresh)|(up)))?$"));

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String bearer = request.getHeader("Authorization");
        try {
            if (StringUtils.startsWith(bearer, BEARER_START)) {
                String token = bearer.substring(BEARER_START.length());
                Map<String, Object> userData = JwtUtil.getData(token);

                if (userData == null) {
                    request.setAttribute("authorization", "Please Check Auth Key");
                } else {
                    UserDetails userDetails = signService.loadUserByUsername((String) userData.get("email"));
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());

                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } else {
                request.setAttribute("authorization", "Please Check Auth Key");
            }
        } catch (Exception ex) {
            logger.error("Wrong Token ", ex);
        }

        filterChain.doFilter(request, response);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return EXCLUDE_URL.stream().anyMatch(exclude -> Pattern.matches(exclude, request.getServletPath()));
    }
}
