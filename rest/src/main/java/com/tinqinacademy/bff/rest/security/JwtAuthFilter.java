package com.tinqinacademy.bff.rest.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tinqinacademy.authentication.api.operations.getuserdetails.GetUserDetailsInput;
import com.tinqinacademy.authentication.api.operations.getuserdetails.GetUserDetailsOutput;
import com.tinqinacademy.authentication.api.operations.validate.ValidateInput;
import com.tinqinacademy.authentication.restexport.AuthenticationClient;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final AuthenticationClient authClient;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        final String header = request.getHeader("Authorization");

        if (Objects.isNull(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String token = header.substring(7);

        ValidateInput input = ValidateInput.builder()
                .token(token)
                .build();

        boolean isTokenValid = authClient.validate(input).isValid();

        if (StringUtils.hasText(token) && isTokenValid) {
            Map<String, String> payload = getPayload(token);

            String username = payload.get("username");
            log.info("username: {}", username);

            GetUserDetailsInput userInput = GetUserDetailsInput.builder()
                    .username(username)
                    .build();

            GetUserDetailsOutput userDetailsOutput = authClient.getUserDetails(userInput);
            log.info("user details output: {}", userDetailsOutput);

            Set<GrantedAuthority> authorities = userDetailsOutput.getAuthorities()
                    .stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());

            User user = new User(userDetailsOutput.getUsername(), userDetailsOutput.getPassword(), authorities);

            UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
            userToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(userToken);
        }

        filterChain.doFilter(request, response);
    }

    private Map<String, String> getPayload(String jwt) throws JsonProcessingException {
        String[] parts = jwt.split("\\.");

        String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

        return objectMapper.readValue(payload, Map.class);
    }
}
