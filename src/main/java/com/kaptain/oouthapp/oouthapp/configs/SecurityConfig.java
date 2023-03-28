package com.kaptain.oouthapp.oouthapp.configs;

import com.kaptain.oouthapp.oouthapp.security.AdminDetailsService;
import com.kaptain.oouthapp.oouthapp.security.DoctorDetailsService;
import com.kaptain.oouthapp.oouthapp.security.PatientDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.oauth2.server.resource.web.access.BearerTokenAccessDeniedHandler;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminDetailsService adminDetailsService;
    private final DoctorDetailsService doctorDetailsService;
    private final PatientDetailsService patientDetailsService;

    @Bean
    PasswordEncoder passwordEncoder () {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Autowired
    protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
                .userDetailsService(patientDetailsService)
                .and()
                .userDetailsService(doctorDetailsService)
                .and()
                .userDetailsService(adminDetailsService);
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/auth/**")
                            .permitAll()
                            .requestMatchers(HttpMethod.OPTIONS, "/**")
                            .permitAll()
                            .requestMatchers(
                                    "/v2/api-docs",
                                    "/configuration/ui",
                                    "/swagger-resources/**",
                                    "/configuration/security",
                                    "/swagger-ui/index.html",
                                    "/webjars/**"
                            )
                            .permitAll();
                });

        http
                .authorizeHttpRequests(authorize -> {
                    authorize
                            .requestMatchers("/patient/**").hasAuthority("SCOPE_PATIENT")
                            .requestMatchers("/doctor/**").hasAuthority("SCOPE_DOCTOR")
                            .requestMatchers("/admin/**").hasAuthority("SCOPE_ADMIN")
                            .anyRequest()
                            .authenticated();
                });

        http.oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);

        http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                .accessDeniedHandler(new BearerTokenAccessDeniedHandler())
        );

        return http.build();
    }

}
