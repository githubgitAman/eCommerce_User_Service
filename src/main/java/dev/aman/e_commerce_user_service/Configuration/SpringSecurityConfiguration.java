package dev.aman.e_commerce_user_service.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

//@Configuration
//public class SpringSecurityConfiguration {
//    //This class is used to bypass the extra security provided by Spring Security
//    //Taken from https://docs.spring.io/spring-security/reference/servlet/architecture.html
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                //Disabling csrf
//                //CSRF protection is enabled by default, which blocks POST requests unless a CSRF token is provided.
//                .csrf(csrf -> csrf.disable())
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .authorizeHttpRequests(authorize -> authorize
//                        //Here we are permitting all requests
//                        .anyRequest().permitAll()
//                );
//
//        return http.build();
//    }
//}
//	SecurityFilterChain: Represents a chain of security filters that apply to incoming HTTP requests.
//