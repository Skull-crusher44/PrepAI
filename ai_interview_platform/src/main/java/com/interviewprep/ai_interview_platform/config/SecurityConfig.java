package com.interviewprep.ai_interview_platform.config;

import com.interviewprep.ai_interview_platform.security.JwtAuthenticationFilter;
import com.interviewprep.ai_interview_platform.security.JwtTokenProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider tokenProvider;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtTokenProvider tokenProvider, UserDetailsService userDetailsService) {
        this.tokenProvider = tokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(tokenProvider, userDetailsService);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        System.out.println("--- Loading Custom Security Configuration ---");

        http
                // 1. Disable CSRF for H2 console and our API endpoints
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers("/h2-console/**", "/api/auth/**", "/api/resume/**")
                )
                // 2. Set up authorization rules
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/health", "/h2-console/**", "/api/auth/**").permitAll()
                        .requestMatchers("/api/resume/**").authenticated()
                        .anyRequest().authenticated()
                )
                // 3. Make the security stateless - a critical step for JWTs
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                // 4. Add JWT filter before UsernamePasswordAuthenticationFilter
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                // 5. Disable the default formLogin and httpBasic authentication methods
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable());

        // This is needed to allow the H2 console to be displayed in a frame
        http.headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin()));

        return http.build();
    }
}
