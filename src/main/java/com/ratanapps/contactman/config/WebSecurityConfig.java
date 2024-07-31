package com.ratanapps.contactman.config;

import com.ratanapps.contactman.util.UserRole;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private String[] SECURED_URL = new String[]{"/admin/**","/user/**"} ;


    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService getUserDetailService() {
        return new UserDetailsServiceImpl();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }


    @SuppressWarnings("deprecation")
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers("/admin/**").hasRole(UserRole.USER_ADMIN.getValue())
                .requestMatchers("/user/**").hasRole(UserRole.USER_GENERAL.getValue())
                .requestMatchers(SECURED_URL).authenticated()
                .anyRequest().permitAll()
                .and()
                .formLogin().loginPage("/signin")
                .loginProcessingUrl("/dologin")
                .successHandler(customAuthenticationSuccessHandler())
        ;

        httpSecurity.authenticationProvider(authenticationProvider());

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(
                    HttpServletRequest request,
                    HttpServletResponse response,
                    Authentication authentication) throws IOException, ServletException {

                String redirectUrl = "/";

                if (authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(grantedAuthority ->
                                        grantedAuthority.getAuthority().equals(UserRole.USER_GENERAL.getDbValue())
                        )
                ) {
                    redirectUrl = "/user/index";
                } else if(authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(grantedAuthority ->
                                grantedAuthority.getAuthority().equals(UserRole.USER_ADMIN.getDbValue())
                        )
                ) {
                    redirectUrl = "/admin/index";
                } else {
                    redirectUrl = "/";
                }

                response.sendRedirect(redirectUrl);
            }
        };
    }

/*
 @Bean
    public UserDetailsService userDetailsService() {
        UserDetails normalUser = User.withUsername("user")
                .password(passwordEncoder().encode("user"))
                .roles(UserRole.USER_GENERAL.getValue())
                .build();

        UserDetails adminUser = User.withUsername("admin")
                .password(passwordEncoder().encode("admin"))
                .roles(UserRole.USER_ADMIN.getValue())
                .build();

        return new InMemoryUserDetailsManager(normalUser, adminUser);


        return getUserDetailService();
} */



    /*
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(getUserDetailService());
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());

        return daoAuthenticationProvider;
    }

    @Bean
    public void configure(WebSecurity webSecurity) throws Exception {
        WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer =  webSecurity.ignoring().requestMatchers("/resources/**","/static/**", "/css/**", "/js/**", "/images/**");

    }*/


    //Configure Method
   /* @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/**").hasRole(UserRole.USER_ADMIN.getValue())
                .antMatchers("/user/**").hasRole(UserRole.USER_GENERAL.getValue())
                .antMatchers("/**").permitAll()
                .and().formLogin()
                .and().csrf().disable();
    }*/
}
