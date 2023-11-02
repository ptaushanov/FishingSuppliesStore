package com.ptaushanov.shop.config;

import com.ptaushanov.shop.exception.handlers.CustomAuthenticationFailureHandler;
import com.ptaushanov.shop.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        securedEnabled = true,
        jsr250Enabled = true,
        prePostEnabled = true
)
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final CustomAuthenticationFailureHandler customAuthenticationFailureHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeRequests(getAuthorizeRequestsCustomizer())
                .exceptionHandling(getExceptionHandlingConfigurerCustomizer());
        return http.build();
    }

    @Bean
    public Customizer<ExceptionHandlingConfigurer<HttpSecurity>> getExceptionHandlingConfigurerCustomizer() {
        return exceptionHandling -> exceptionHandling
                .authenticationEntryPoint(
                        customAuthenticationFailureHandler::onAuthenticationFailure
                );
    }

    @Bean
    public Customizer<ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry>
    getAuthorizeRequestsCustomizer() {
        return authorizeRequests -> {
            try {
                authorizeRequests
                        .requestMatchers("/api/v1/auth/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                        .and()
                        .sessionManagement(session -> session.sessionCreationPolicy(
                                SessionCreationPolicy.STATELESS))
                        .authenticationProvider(authenticationProvider)
                        .addFilterBefore(
                                jwtAuthenticationFilter,
                                UsernamePasswordAuthenticationFilter.class
                        );
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
