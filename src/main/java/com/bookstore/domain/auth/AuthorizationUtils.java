package com.bookstore.domain.auth;

import lombok.NoArgsConstructor;


import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class AuthorizationUtils {

    public static final String JWT_SECRET = "jwt-secret";

    public static final String CUSTOMER_ID = "customerId";

}
