package com.ssp.usermgmt.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
   @Autowired
   private CustomDetailsService customDetailsService;

   @Bean
   public PasswordEncoder encoder() {
      return new BCryptPasswordEncoder();
   }
   
   @Override
   @Autowired
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(customDetailsService).passwordEncoder(encoder());
   }
   
   @Override
   protected void configure(HttpSecurity http) throws Exception {
      //http.cors();
      http.cors().and().authorizeRequests().anyRequest().authenticated().and().sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.NEVER);
      
      /*http
      .csrf().disable()
      .anonymous().disable()
      .authorizeRequests()
      .antMatchers("/api-docs/**").permitAll();*/
   }
   
   @Override
   public void configure(WebSecurity web) throws Exception {
      //web.ignoring();
	   web.ignoring().antMatchers("/v2/api-docs", "/configuration/**", "/swagger-resources/**",  "/swagger-ui.html", "/webjars/**", "/api-docs/**");
   }
   
   @Bean
   public org.springframework.web.filter.CorsFilter corsFilter() {
       UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
       CorsConfiguration config = new CorsConfiguration();
       config.setAllowCredentials(true);
       config.addAllowedOrigin("*");
       config.addAllowedHeader("*");
       config.addAllowedMethod("*");
       source.registerCorsConfiguration("/**", config);
       CorsFilter bean = new CorsFilter(source);
       return bean;
   }
   @Override
   @Bean
   public AuthenticationManager authenticationManagerBean() throws Exception {
      return super.authenticationManagerBean();
   }
   
}