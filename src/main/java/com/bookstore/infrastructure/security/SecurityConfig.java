package com.bookstore.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final String[] SWAGGER_RESOURCES = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };

    @Autowired
    private ApiHttpSecurityConfigurer apiHttpSecurityConfigurer;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.requestMatchers().antMatchers("/**/*").and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        apiHttpSecurityConfigurer.configure(http);
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(SWAGGER_RESOURCES);
    }

    @Bean
    @ConditionalOnMissingBean(ApiHttpSecurityConfigurer.class)
    public ApiHttpSecurityConfigurer defaultApiHttpSecurityConfigurer() {
        return http -> http
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**/*").permitAll()
                .antMatchers("/**/*").permitAll()
//                .antMatchers("/**/*").authenticated()
                .and().csrf().disable();
    }

}
