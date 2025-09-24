package com.tavi.tavi_mrs.security;

import com.tavi.tavi_mrs.repository.nguoi_dung.NguoiDungRepo;
import com.tavi.tavi_mrs.service_impl.nguoi_dung.JWTService;
import com.tavi.tavi_mrs.service_impl.nguoi_dung.UserDetailsService_impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.channel.ChannelProcessingFilter;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true)
public class SecurityConfig  extends WebSecurityConfigurerAdapter {


    @Autowired
    private UserDetailsService_impl userDetailsService;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private NguoiDungRepo appUserService;

    @Bean
    public RestAuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final String headers =  "Authorization, Access-Control-Allow-Headers, "+
                "Origin, Accept, X-Requested-With, Content-Type, " +
                "Access-Control-Request-Method, Custom-Filter-Header";

        CorsConfiguration config = new CorsConfiguration();

        config.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE")); // Required for PUT method
        config.addExposedHeader(headers);
        config.setAllowCredentials(true);
        config.applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", config);

        return source;
    }

    //    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
//        return source;
//    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();

        http.antMatcher("/api/**")
                .authorizeRequests()
                .antMatchers("/api/**/public/**").permitAll()
                .antMatchers("/api/*/admin/*").hasAnyRole("QUAN LY", "BAN HANG")
                .antMatchers("/api/*/admin/*/find-by-id/*").hasAnyRole("QUAN LY", "BAN HANG")
                .antMatchers("/delete/**").hasRole("QUAN LY");
//        http.authorizeRequests()
//                .antMatchers(HttpMethod.DELETE,"/api/**/admin/**/delete/**").hasAnyAuthority("QUAN LY","QUAN LY CHI NHANH")
//                .antMatchers("/api/**/admin/nhan-vien/**").hasAnyAuthority("QUAN LY","QUAN LY CHI NHANH")
//                .antMatchers("/api/**/admin/**").permitAll()

//                .anyRequest().authenticated()
//                .and().httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint())
//                .and()
//                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), appUserService, jwtService), JWTAuthorizationFilter.class)
//                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

//        http.antMatcher("/api/**")
//                .authorizeRequests()
//                .antMatchers("/api/**/public/**").permitAll()
//                .antMatchers("/api/**/validate-token/**").permitAll()
//                .antMatchers("/api/**/admin/**").hasAuthority("ADMIN")
//                .anyRequest().authenticated()
//                .and().httpBasic().authenticationEntryPoint(restAuthenticationEntryPoint())
//                .and()
//                .addFilterBefore(new JWTAuthorizationFilter(authenticationManager(), appUserService, jwtService), JWTAuthorizationFilter.class)
//                .addFilterBefore(new CORSFilter(), ChannelProcessingFilter.class)
//                .exceptionHandling().accessDeniedHandler(customAccessDeniedHandler());

        //stateless
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }


    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    @Bean
    public Docket api() throws IOException, URISyntaxException {
        final List<ResponseMessage> globalResponses = Arrays.asList(
                new ResponseMessageBuilder()
                        .code(200)
                        .message("OK")
                        .build(),
                new ResponseMessageBuilder()
                        .code(400)
                        .message("Bad Request")
                        .build(),
                new ResponseMessageBuilder()
                        .code(401)
                        .message("Unauthorized")
                        .build(),
                new ResponseMessageBuilder()
                        .code(403)
                        .message("Forbidden")
                        .build(),
                new ResponseMessageBuilder()
                        .code(404)
                        .message("Not Found")
                        .build(),
                new ResponseMessageBuilder()
                        .code(500)
                        .message("Internal Error")
                        .build());
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .useDefaultResponseMessages(false)
                .globalResponseMessage(RequestMethod.GET, globalResponses)
                .globalResponseMessage(RequestMethod.POST, globalResponses)
                .globalResponseMessage(RequestMethod.DELETE, globalResponses)
                .globalResponseMessage(RequestMethod.PATCH, globalResponses)
                .globalResponseMessage(RequestMethod.PUT, globalResponses)
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfo(
                "My REST API for My software",
                "Đây là Project tôi làm nghiêm túc, khi bạn đã truy cập vào docs api của tôi mong bạn làm việc một cách nghiêm túc cùng tôi.",
                "1.0",
                "Terms of service",
                new Contact("NGUYEN DUC QUY", "https://www.kiotmpec.cloud", "nguyenducquy.hust@gmail.com"),
                "License of API",
                "API license URL",
                Collections.emptyList());
    }

}
