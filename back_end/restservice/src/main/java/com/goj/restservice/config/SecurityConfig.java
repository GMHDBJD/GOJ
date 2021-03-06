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
                .antMatchers(HttpMethod.POST,"/api/v1/contests").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/contests/*/submissions").authenticated()
                .antMatchers(HttpMethod.GET,"/api/v1/contests/*").authenticated()
                .antMatchers(HttpMethod.PUT,"/api/v1/contests").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/contests").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/contests/*/prolems").hasRole("ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/contests/*/prolems").authenticated()
                .antMatchers(HttpMethod.DELETE,"/api/v1/contests/*/prolems").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/contests/*/users").authenticated()
                .antMatchers(HttpMethod.GET,"/api/v1/contests/*/users").authenticated()
                .antMatchers(HttpMethod.POST,"/api/v1/problems").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/problems").hasRole("ADMIN")
                .antMatchers(HttpMethod.DELETE,"/api/v1/problems").hasRole("ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/submissions").authenticated()
                .antMatchers(HttpMethod.GET,"/api/v1/submissions/*").authenticated()
                .antMatchers(HttpMethod.GET,"/api/v1/users/*/*").authenticated()
                .anyRequest().permitAll().and().cors()
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
