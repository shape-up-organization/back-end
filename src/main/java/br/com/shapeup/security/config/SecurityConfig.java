package br.com.shapeup.security.config;

import br.com.shapeup.security.filter.JwtAuthFilter;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtAuthFilter authFilter;

    private final UserInfoUserDetailsService uds;

    public SecurityConfig(JwtAuthFilter authFilter, UserInfoUserDetailsService uds) {
        this.authFilter = authFilter;
        this.uds = uds;
    }

    private static final String[] REQUIRED_AUTHENTICATION = {
            "/users/**",
            "/friends/**",
            "/profiles/**",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .httpBasic().disable()
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .cors()
                .and()
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(REQUIRED_AUTHENTICATION).authenticated()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .requestMatchers("/").permitAll()
                        .anyRequest()
                        .permitAll()
                ).userDetailsService(uds)
                .exceptionHandling()
                .authenticationEntryPoint(
                        ((request, response, authException) ->
                                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized"))

                )
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoUserDetailsService();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public AuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProviderDao = new DaoAuthenticationProvider();
        authProviderDao.setUserDetailsService(userDetailsService());
        authProviderDao.setPasswordEncoder(passwordEncoder());

        return authProviderDao;
    }
}
