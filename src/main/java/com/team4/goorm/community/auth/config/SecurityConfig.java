package com.team4.goorm.community.auth.config;

import com.team4.goorm.community.global.config.CorsConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.team4.goorm.community.auth.application.CustomOAuth2UserService;
import com.team4.goorm.community.auth.handler.OAuth2AuthenticationSuccessHandler;
import com.team4.goorm.community.auth.jwt.JwtAccessDeniedHandler;
import com.team4.goorm.community.auth.jwt.JwtAuthenticationEntryPoint;
import com.team4.goorm.community.auth.jwt.filter.JwtAuthenticationFilter;
import com.team4.goorm.community.auth.jwt.filter.JwtExceptionFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService oAuth2UserService;
	private final OAuth2AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler;
	private final JwtExceptionFilter jwtExceptionFilter;
	private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
	private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

	private final String[] whitelist = {
		"/",
		"/api/v1/auth/**",
		"/swagger-ui/**",
		"/v3/api-docs/**",
		"/oauth2/authorization/**",
		"/login/oauth2/code/**",
	};

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.cors(cors -> cors.configurationSource(CorsConfig.apiConfigurationSource()))
			.csrf(AbstractHttpConfigurer::disable)
			.httpBasic(AbstractHttpConfigurer::disable)
			.formLogin(AbstractHttpConfigurer::disable)
			.logout(AbstractHttpConfigurer::disable)
			.headers(c -> c.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable).disable())
			.sessionManagement(c -> c.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(request -> request
				.requestMatchers(whitelist).permitAll()
			.anyRequest().authenticated())
			.oauth2Login(oauth ->
				oauth.userInfoEndpoint(c -> c.userService(oAuth2UserService))
					.successHandler(oAuth2AuthenticationSuccessHandler)
			)
			.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
			.addFilterBefore(jwtExceptionFilter, JwtAuthenticationFilter.class)
			.exceptionHandling(exceptions -> exceptions
				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
				.accessDeniedHandler(jwtAccessDeniedHandler)
			);

		return http.build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}