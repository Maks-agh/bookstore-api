package com.bookstore.infrastructure.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.bookstore.domain.auth.CustomerAuthority;
import com.bookstore.domain.customer.CustomerEntity;
import com.bookstore.domain.customer.CustomerRepository;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;


import static com.bookstore.domain.auth.AuthorizationUtils.CUSTOMER_ID;
import static com.bookstore.domain.auth.AuthorizationUtils.JWT_SECRET;
import static java.util.Collections.singletonList;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_HEADER = "Authorization";

    private static final String TOKEN_PREFIX = "Bearer ";

    private final CustomerRepository customerRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CustomerRepository customerRepository) {
        super(authenticationManager);
        this.customerRepository = customerRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
        if (authentication == null) {
            filterChain.doFilter(request, response);
            return;
        }
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_HEADER);
        if (token != null && token.startsWith(TOKEN_PREFIX)) {
            String userId = JWT.require(Algorithm.HMAC256(JWT_SECRET))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getClaim(CUSTOMER_ID).asString();
            Optional<CustomerEntity> user = customerRepository.findById(UUID.fromString(userId));
            if (user.isPresent()) {
                GrantedAuthority authority = new CustomerAuthority(user.get().getId().toString());
                return new UsernamePasswordAuthenticationToken(userId, null, singletonList(authority));
            }
        }
        return null;
    }
}