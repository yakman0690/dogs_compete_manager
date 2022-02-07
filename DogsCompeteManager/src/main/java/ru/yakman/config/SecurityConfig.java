/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.yakman.config;

import java.io.IOException;
import java.util.Arrays;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.endpoint.DefaultAuthorizationCodeTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.http.OAuth2ErrorResponseErrorHandler;
import org.springframework.security.oauth2.core.http.converter.OAuth2AccessTokenResponseHttpMessageConverter;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.client.RestTemplate;
import ru.yakman.service.impl.CustomOAuth2UserService;
import ru.yakman.service.impl.UserDetailsServiceImpl;
import ru.yakman.utils.CustomTokenResponseConverter;

/**
 *
 * @author Test
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Configuration
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public static class RemoteSecurityConfig extends WebSecurityConfigurerAdapter {

        protected void configure(HttpSecurity http) throws Exception {
            http
                    .csrf().disable()
                    .antMatcher("/remote/**")
                    .authorizeRequests(a -> a.anyRequest().authenticated())
                    .httpBasic();
        }
    }

    @Override
    public void init(WebSecurity web) throws Exception {
        logger.info("Security config init called");
        super.init(web);
    }

    @Autowired
    private UserDetailsServiceImpl userService;

    Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        logger.info("call test service from SecurityConfig: configure");
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());

    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http)
            throws Exception {
        http
                .csrf().disable()
                //.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
                //.and()
                // .csrf().csrfTokenRepository(csrfTokenRepository())
                //.and()
                .antMatcher("/**")
                .authorizeRequests()
                .antMatchers("/").permitAll()
                //.antMatchers("/registration.html").permitAll()
                //.antMatchers("/getPassword.html").permitAll()
                .antMatchers("/login*").permitAll()
                .antMatchers("/index.html").permitAll()
                .antMatchers("/error.html").permitAll()
                .antMatchers("/admin.html").hasRole("ADMIN")
                .antMatchers("/user.html").hasRole("USER")
                .antMatchers("/css/**").permitAll()
                .antMatchers("/users/**").permitAll()
                .antMatchers("/public/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/images/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login.html")
                .usernameParameter("username")
                .passwordParameter("password")
                .loginProcessingUrl("/login/check")
                .successHandler(new SimpleUrlAuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                        if (authentication != null) {
                            logger.info(authentication.getName());
                            logger.info(authentication.getDetails());
                            logger.info(authentication.getCredentials());
                            logger.info(authentication.getPrincipal());
                            System.out.println(authentication.getName());
                            System.out.println(authentication.getDetails().toString());

                        }
                        super.onAuthenticationSuccess(request, response, authentication); //To change body of generated methods, choose Tools | Templates.
                    }

                    @Override
                    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
                        if (authentication != null) {
                            if (authentication.getPrincipal() instanceof User) {
                                User u = (User) authentication.getPrincipal();
                                for (GrantedAuthority ga : u.getAuthorities()) {
                                    if (ga.getAuthority().equals("ROLE_ADMIN")) {
                                        return "/admin.html";
                                    } else {
                                        return "/user.html";
                                    }
                                }
                            }
                        }
                        return "/";
                    }

                })
                //.defaultSuccessUrl("/", true)
                .failureUrl("/login.html?error=true")
                .permitAll()
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .deleteCookies("JSESSIONID")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .and()
                //.addFilterAfter(new CsrfHeaderFilter(), CsrfFilter.class)
                /*.and()
                .httpBasic()
                //убрать всплывающее окно логи-пароль
                .authenticationEntryPoint(new AuthenticationEntryPoint() { //<< implementing this interface
                    @Override
                    public void commence(HttpServletRequest request, HttpServletResponse response,
                            AuthenticationException authException) throws IOException, ServletException {
                        //>>> response.addHeader("WWW-Authenticate", "Basic realm=\"" + realmName + "\""); <<< (((REMOVED)))
                        response.sendError(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase());
                    }
                })*/
                .oauth2Login()
                //Access token Endpoint
                .tokenEndpoint()
                .accessTokenResponseClient(accessTokenResponseClient())
                //Userinfo endpoint

                .and()
                .userInfoEndpoint()
                .userService(customOAuth2UserService);
    }

    @Autowired
    private CustomOAuth2UserService customOAuth2UserService;

    @Bean
    public OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> accessTokenResponseClient() {
        DefaultAuthorizationCodeTokenResponseClient accessTokenResponseClient
                = new DefaultAuthorizationCodeTokenResponseClient();

        OAuth2AccessTokenResponseHttpMessageConverter tokenResponseHttpMessageConverter
                = new OAuth2AccessTokenResponseHttpMessageConverter();

        tokenResponseHttpMessageConverter.setTokenResponseConverter(new CustomTokenResponseConverter());

        RestTemplate restTemplate = new RestTemplate(Arrays.asList(
                new FormHttpMessageConverter(), tokenResponseHttpMessageConverter));

        restTemplate.setErrorHandler(new OAuth2ErrorResponseErrorHandler());

        accessTokenResponseClient.setRestOperations(restTemplate);
        return accessTokenResponseClient;
    }

}
