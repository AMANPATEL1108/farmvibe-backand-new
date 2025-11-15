package com.example.farmvibe_backand_new.api.filter;

import com.example.farmvibe_backand_new.api.service.AuthUserDetailsService;
import com.example.farmvibe_backand_new.api.service.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        // ✅ Skip JWT validation for public endpoints
        String path = request.getRequestURI();

        // FIXED: Added /public/ to the skip list
        if (path.startsWith("/auth/") || path.startsWith("/public/") || path.equals("/error")) {
            System.out.println("✅ Skipping JWT validation for: " + path);
            filterChain.doFilter(request, response);
            return;
        }

        System.out.println("🔒 Applying JWT validation for: " + path);

        final String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("⚠️ No Authorization header found");
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String jwt = authHeader.substring(7);
            final String username = jwtService.extractUsername(jwt);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                if (jwtService.isTokenValid(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                    System.out.println("✅ JWT validated successfully for user: " + username);
                } else {
                    System.out.println("❌ Invalid JWT token");
                }
            }
        } catch (ExpiredJwtException e) {
            // ✅ Handle expired token gracefully - don't crash the filter
            System.err.println("❌ JWT token expired: " + e.getMessage());
            // Let Spring Security handle the 401 response
        } catch (Exception e) {
            // ✅ Handle any other JWT errors
            System.err.println("❌ JWT validation error: " + e.getMessage());
        }

        filterChain.doFilter(request, response);
    }
}