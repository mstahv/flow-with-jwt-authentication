package com.example.application.security;

import com.example.application.views.login.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurityConfigurerAdapter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithms;

import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurityConfigurerAdapter {

    public static final String LOGOUT_URL = "/";

    @Value("${jwt.auth.secret}")
    private String authSecret;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        super.configure(http);
        setLoginView(http, LoginView.class, LOGOUT_URL);

        setStatelessAuthentication(http, new SecretKeySpec(Base64.getDecoder().decode(authSecret), JwsAlgorithms.HS256), "com.example.application");
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
        web.ignoring().antMatchers("/images/*.png");
    }
}
