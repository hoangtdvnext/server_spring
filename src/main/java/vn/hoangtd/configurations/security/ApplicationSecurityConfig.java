package vn.hoangtd.configurations.security;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import vn.hoangtd.filters.AuthenticationTokenProcessingFilter;
import vn.hoangtd.service.AuthenticationService;
import vn.hoangtd.service.JWTCodec;
import vn.hoangtd.service.impl.AuthenticationServiceImpl;
import vn.hoangtd.service.impl.JWTCodecImpl;

/**
 * Created by hoangtd on 1/23/2017.
 */

@Configuration
@EnableWebSecurity
@ComponentScan("vn.hoangtd")
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private AuthenticationTokenProcessingFilter authenticationTokenProcessingFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable()
                .exceptionHandling()
                .authenticationEntryPoint(getRestAuthenticationEntryPoint());
        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.authorizeRequests().antMatchers("/api/auth/sign-in").permitAll();
        http.authorizeRequests().antMatchers("/api/**").authenticated();
        http.authorizeRequests().antMatchers("/api/patients/*").access("hasRole('ROLE_ADMIN')");
        http.addFilterBefore(new CORSFilter(), RequestAttributeAuthenticationFilter.class);
        http.addFilterBefore(authenticationTokenProcessingFilter, RequestAttributeAuthenticationFilter.class);
    }

    @Bean
    public RestAuthenticationEntryPoint getRestAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    @Bean
    public EmployeeRepository getEmployeeRepository(SessionFactory sessionFactory) {
        return new EmployeeRepositoryImpl(sessionFactory);
    }

    @Autowired
    @Bean
    public PasswordEncoderUtils getPasswordEncoderUtils(PasswordEncoder passwordEncoder) {
        return new PasswordEncoderUtils(passwordEncoder);
    }

    @Bean
    public JWTCodec getJwtCodec() {
        return new JWTCodecImpl();
    }

    @Bean
    public AuthenticationService getAuthenticationService() {
        return new AuthenticationServiceImpl(
                getEmployeeRepository(sessionFactory), getJwtCodec(), getPasswordEncoderUtils(passwordEncoder));
    }

    @Autowired
    @Bean
    public AuthenticationTokenProcessingFilter getAuthenticationTokenProcessingFilter(
            AuthenticationService authenticationService) {
        return new AuthenticationTokenProcessingFilter(authenticationService);
    }
}
