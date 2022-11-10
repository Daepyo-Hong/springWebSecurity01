package com.green.nowon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

// https://docs.spring.io/spring-security/reference/servlet/configuration/java.html
@EnableWebSecurity
public class SecurityConfig {

    //비밀번호 암호화 방법 : BCryptPasswordEncoder : 복호화불가,
    //  같은 값을 넣어도 매번 다르게 생성됩니다. -> 내부적으로 매치라는 코드로 암호화된 코드를 비교함!
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeRequests(
                        authorize -> authorize            //순서가 매우 중요하다.!!!
                                .antMatchers("/css/**", "/js/**", "/images/**").permitAll()  //static 자원들 경로 허용
                                .antMatchers("/", "/common/**").permitAll()       //Specify that URLs are allowed by anyone.
                                //role prefix (default "ROLE_")
                                .antMatchers("/user/**").hasRole("USER")//ROLE_USER
                                .antMatchers("/seller/**").hasRole("SELLER")//ROLE_SELLER
                                .antMatchers("/admin/**").hasRole("ADMIN")//ROLE_ADMIN
                                .anyRequest().authenticated()       //antMatchers 에 적용된 주소 이외 나머지는 최소한 인증이 필요합니다.(Login페이지로 보내버림)
                )
                .csrf().disable()       //사이트 간 요청 위조(csrf 방지 사용 안함) 히든토큰존재안함!

                .formLogin(
                        login -> login
                                .loginPage("/signin")       //로그인페이지
                                .loginProcessingUrl("/signin-action") //action="/signin-action"
                                .permitAll()
                                .usernameParameter("email") //Default is "username".
                                .passwordParameter("pass")  //Default is "password".
                                // 구조 https://docs.spring.io/spring-security/reference/servlet/authentication/passwords/dao-authentication-provider.html
                )
        ;

        return http.build();
    }
}
