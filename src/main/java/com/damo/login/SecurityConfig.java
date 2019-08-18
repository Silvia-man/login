package com.damo.login;


import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public SecurityConfig() {
        super();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("damo").password("damo").roles("DAMO").and().withUser("ashton").password("ashton").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().
                antMatchers("/admin/**").hasRole("DAMO")
                .antMatchers("/anonymous*").anonymous()
                .antMatchers("/login*").permitAll()
                .anyRequest().authenticated()

                .and()

                .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/dharma_login")
                .defaultSuccessUrl("/home.html", true)
                .failureForwardUrl("/login.html?error=true")

                .and()

                .logout()
                .logoutUrl("/dharma_logout")
                .logoutSuccessUrl("/login.html")
                .deleteCookies("JSESSIONID")

                .and()

                .rememberMe()
                .key("dharmaRememberMe")
                .tokenValiditySeconds(500)

                .and()

                .sessionManagement()
                .sessionFixation().migrateSession()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .invalidSessionUrl("/invaildSession.html")
                .maximumSessions(2)
                .expiredUrl("/sessionExpired.html");
    }
}
