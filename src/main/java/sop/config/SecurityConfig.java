package sop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//            .authorizeHttpRequests(auth -> auth
//                .requestMatchers("/", "/auth/login", "/auth/chklogin", "/auth/register", "/home/**", "/static/**", "/css/**",
//						"/img/**",
//						"/js/**",
//						"/lib/**",
//						"/scss/**").permitAll() // Cho phép truy cập trang chính và các endpoint đăng nhập
//                .anyRequest().authenticated() // Các yêu cầu khác cần xác thực
//            )
//            .formLogin(form -> form
//                .loginPage("/auth/login") // Trang đăng nhập tùy chỉnh
//                .loginProcessingUrl("/auth/chklogin")
//                .defaultSuccessUrl("/user/profile", true) // Trang sẽ được chuyển hướng đến khi đăng nhập thành công
//                .permitAll() // Cho phép tất cả mọi người truy cập trang đăng nhập
//            )
//            .logout(logout -> logout
//                .permitAll() // Cho phép người dùng đăng xuất
//            )
//        .csrf().disable();
//
//        return http.build(); // Xây dựng SecurityFilterChain
//    }
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll() // Tạm thời cho phép tất cả các request
            )
            .csrf().disable(); // Tắt CSRF
        return http.build();
    }

}
