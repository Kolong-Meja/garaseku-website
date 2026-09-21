package com.faisalrmdhn.GaraseKu.security;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
  private final UserDetailsService userDetailsService;
  private final JwtAuthenticationHandler jwtAuthenticationHandler;

  private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

  public JwtAuthenticationFilter(
      UserDetailsService userDetailsService,
      JwtAuthenticationHandler jwtAuthenticationHandler) {
    this.userDetailsService = userDetailsService;
    this.jwtAuthenticationHandler = jwtAuthenticationHandler;
  }

  @Override
  protected void doFilterInternal(
      @NonNull HttpServletRequest request,
      @NonNull HttpServletResponse response,
      @NonNull FilterChain filterChain)
      throws ServletException, IOException {
    final String header = request.getHeader("Authorization");

    if (header == null || !header.startsWith("Bearer ")) {
      filterChain.doFilter(request, response);
      return;
    }

    try {
      final String jwt = header.substring(7);
      final String userEmail = jwtAuthenticationHandler.extractUsername(jwt);

      if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        var userDetails = userDetailsService.loadUserByUsername(userEmail);

        if (!jwtAuthenticationHandler.isTokenValid(jwt, userDetails)) {
          LOGGER.warn("[GARASEKU LOG]: Invalid or expired access token for user: {}", userEmail);
          response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired access token.");
          return;
        }

        var token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

        token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(token);
      }
    } catch (RuntimeException exception) {
      SecurityContextHolder.clearContext();
      LOGGER.warn("[GARASEKU LOG]: Access token validation failed.", exception);
      response.sendError(HttpStatus.UNAUTHORIZED.value(), "Invalid or expired access token.");
      return;
    }
    filterChain.doFilter(request, response);
  }

  @Override
  protected boolean shouldNotFilter(@NonNull HttpServletRequest request) {
    String path = request.getServletPath();
    return "/api/auth/login".equals(path)
        || "/api/auth/register".equals(path)
        || "/api/auth/refresh".equals(path);
  }
}