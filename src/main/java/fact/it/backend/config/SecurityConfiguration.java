package fact.it.backend.config;

import fact.it.backend.service.JwtFilterRequest;
import fact.it.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserService userService;

    @Autowired
    private JwtFilterRequest jwtFilterRequest;

    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(this.userService);
        return provider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().configurationSource(corsConfigurationSource());

        http.csrf().disable().authorizeRequests()
                .antMatchers(HttpMethod.GET, "/v3/**").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .antMatchers(HttpMethod.GET, "/swagger-ui/**").permitAll()

                .antMatchers(HttpMethod.GET, "/api/organizations").permitAll()
                .antMatchers(HttpMethod.GET, "/api/organizations/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/api/products").permitAll()
                .antMatchers(HttpMethod.GET, "/api/products/organization/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/products/{id}").permitAll()

                .antMatchers(HttpMethod.PUT, "/api/interactions").permitAll()

                .antMatchers(HttpMethod.GET, "/api/reviews").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reviews/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reviews/product/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/reviews/customer/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/api/categories").permitAll()
                .antMatchers(HttpMethod.GET, "/api/categories/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/api/colors").permitAll()
                .antMatchers(HttpMethod.GET, "/api/colors/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/api/sizes").permitAll()
                .antMatchers(HttpMethod.GET, "/api/sizes/{id}").permitAll()

                .antMatchers(HttpMethod.GET, "/api/stocks").permitAll()
                .antMatchers(HttpMethod.GET, "/api/stocks/{id}").permitAll()
                .antMatchers(HttpMethod.GET, "/api/stocks/product/{id}").permitAll()

                .antMatchers(HttpMethod.POST, "/api/register/customer").permitAll()
                .antMatchers(HttpMethod.POST, "/api/register/organization").permitAll()
                .antMatchers(HttpMethod.POST, "/api/authenticate").permitAll()

                .anyRequest().authenticated();


        http.addFilterBefore(jwtFilterRequest, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
