package com.bmt.user.service.config;

import com.bmt.user.service.filter.JwtAuthenticationFilter;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import javax.crypto.spec.SecretKeySpec;

/*@Configuration
@EnableWebSecurity*/

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private static final String SECRET_KEY = "your-secret-key-saurabh-kumar-tyagi-140288";

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**",
                                "/register",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/v3/api-docs.yaml",
                                "/swagger-ui.html").permitAll() // open APIs
                        .anyRequest().authenticated() // all others require JWT
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults())); // ✅ non-deprecated

        return http.build();
    }

    @Bean
    public JwtDecoder jwtDecoder() {
        // If you want to validate with secret key instead of JWKS URL
        SecretKeySpec key = new SecretKeySpec(SECRET_KEY.getBytes(), SignatureAlgorithm.HS256.getJcaName());
        return NimbusJwtDecoder.withSecretKey(key).build();
    }
}
