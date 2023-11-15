package com.poten.attackongoose.config;

import com.poten.attackongoose.config.jwt.TokenAuthenticationFilter;
import com.poten.attackongoose.config.oauth.CustomAuthorizationRequestRepository;
import com.poten.attackongoose.config.oauth.OAuth2SuccessHandler;
import com.poten.attackongoose.service.user.OAuth2UserCustomService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final TokenAuthenticationFilter tokenAuthenticationFilter;
    private final OAuth2UserCustomService oAuth2UserCustomService;
    private final OAuth2SuccessHandler oAuth2SuccessHandler;
    private final CustomAuthorizationRequestRepository authorizationRequestRepository;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);
        http.httpBasic(AbstractHttpConfigurer::disable);
        http.formLogin(AbstractHttpConfigurer::disable);
        http.logout(AbstractHttpConfigurer::disable);
        http.logout(logout -> logout.logoutSuccessUrl("/")
                .invalidateHttpSession(true));
        http.sessionManagement(sessionManagement -> sessionManagement
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(tokenAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        http.authorizeHttpRequests().requestMatchers(
                        new AntPathRequestMatcher("/resources/**"),
                        new AntPathRequestMatcher("/login"),
                        new AntPathRequestMatcher("/api/token"),
                        new AntPathRequestMatcher("/oauth2/**")
                ).permitAll()
                .anyRequest().authenticated();

        http.oauth2Login(login -> login
                .loginPage("/login")
                .authorizationEndpoint(endpoint -> endpoint
                        .authorizationRequestRepository(authorizationRequestRepository))
                .successHandler(oAuth2SuccessHandler)
                .userInfoEndpoint(endpoint -> endpoint.userService(oAuth2UserCustomService))
        );

        http.exceptionHandling(exception -> exception.defaultAuthenticationEntryPointFor(
                new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED),
                new AntPathRequestMatcher("/**")
        ));

        return http.build();
    }
}
