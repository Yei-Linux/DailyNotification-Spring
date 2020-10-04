package pe.yeilinux.notification.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Configuration
@EnableWebSecurity
@Order(1)
public class APISecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String SECURED_PATTERN = "/**";

    @Value("${credentials.auth-header-name}")
    private String principalRequestHeader;

    @Value("${credentials.auth-header-key}")
    private String principalRequestValue;

    @Autowired
    private Environment environment;

    private Boolean useSecurity() {
        return !environment.getProperty("disable-security", Boolean.class, Boolean.FALSE);
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ApiKeyAuthFilter filter = new ApiKeyAuthFilter(principalRequestHeader);
        filter.setAuthenticationManager(new AuthenticationManager() {
            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                String principal = (String) authentication.getPrincipal();
                if (!principalRequestValue.equals(principal))
                {
                    throw new BadCredentialsException("The API key was not found or not the expected value.");
                }
                authentication.setAuthenticated(true);
                return authentication;
            }
        });

        if (useSecurity()) {
            httpSecurity.requestMatchers().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/actuator/**", "/v2/api-docs/**","/swagger-ui.html","/swagger-resources/**","/webjars/**").permitAll()
                    .and().
                    antMatcher("/**").
                    csrf().disable().
                    sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).
                    and().addFilter(filter).authorizeRequests().anyRequest().authenticated();
        }else {
            httpSecurity.requestMatchers().and().authorizeRequests().antMatchers(HttpMethod.OPTIONS).permitAll().antMatchers("/**/").permitAll()
                    .antMatchers(SECURED_PATTERN).authenticated();
        }
    }
}