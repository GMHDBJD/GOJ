package com.goj.restservice.config;

import com.goj.restservice.security.JwtConfigurer;
import com.goj.restservice.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable().csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
                .antMatchers("/v1/auth/**").permitAll().antMatchers(HttpMethod.GET, "/v1/contests").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/contests/**").authenticated()
                .antMatchers(HttpMethod.POST, "/v1/contests/*/users").authenticated()
                .antMatchers(HttpMethod.GET, "/v1/problems/**").permitAll()
                .antMatchers(HttpMethod.GET, "/v1/submissions").permitAll().antMatchers("/v1/submissions/**")
                .authenticated().antMatchers("/v1/users/**").authenticated().anyRequest().hasRole("ADMIN").and().cors()
                .and()
                /*
                 * .antMatchers("/auth/signin").permitAll(). antMatchers(HttpMethod.GET,
                 * "/vehicles/**").permitAll() .antMatchers(HttpMethod.DELETE,
                 * "/vehicles/**").hasRole("ADMIN") .antMatchers(HttpMethod.GET,
                 * "/v1/vehicles/**").permitAll() .anyRequest().authenticated() .and()
                 */
                .apply(new JwtConfigurer(jwtTokenProvider));

    }
}
