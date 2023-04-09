package br.com.shapeup.security.filter;

import br.com.shapeup.common.exceptions.user.UserNotFoundException;
import br.com.shapeup.security.config.UserInfoUserDetailsService;
import br.com.shapeup.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserInfoUserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws IOException {
        try {
            String authToken = extractAuthToken(request);
            String username = null;

            if (authToken != null) {
                username = jwtService.extractUsername(authToken);
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = loadUserDetails(username);
                if (jwtService.validateToken(authToken, userDetails)) {
                    authenticateUser(userDetails, request);
                }
            }
            filterChain.doFilter(request, response);
        } catch (UserNotFoundException | ServletException e) {
            handleAuthenticationError(response, e.getMessage());
        }
    }

    private String extractAuthToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    private UserDetails loadUserDetails(String username) throws UserNotFoundException {
        return userDetailsService.loadUserByUsername(username);
    }

    private void authenticateUser(UserDetails userDetails, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities());
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authToken);
    }

    private void handleAuthenticationError(HttpServletResponse response, String errorMessage) throws IOException {
        response.sendError(HttpStatus.UNAUTHORIZED.value(), errorMessage);
    }
}
