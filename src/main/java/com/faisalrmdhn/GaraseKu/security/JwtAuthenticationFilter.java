package com.faisalrmdhn.GaraseKu.security;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.faisalrmdhn.GaraseKu.exception.UnauthorizedException;

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

    final String jwt = header.substring(7);
    final String userEmail = jwtAuthenticationHandler.extractUsername(jwt);

    if (userEmail != null && SecurityContextHolder.getContext().getAuthentication() == null) {
      var userDetails = userDetailsService.loadUserByUsername(userEmail);

      if (!jwtAuthenticationHandler.isTokenValid(jwt, userDetails)) {
        LOGGER.warn("[GARASEKU LOG]: Invalid or expired refresh token for user: {}", userEmail);
        throw new UnauthorizedException("Invalid or expired refresh token.");
      }

      var token = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

      token.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
      SecurityContextHolder.getContext().setAuthentication(token);
    }
    filterChain.doFilter(request, response);
  }
}
