package com.example.demo.Configraition;
import com.example.demo.Service.MyUserDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SpringConfigraition {
    private final MyUserDetailService myUserDetailService;
@Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(myUserDetailService);
    //هنا يظهر عندي الباس بقاعدة البيانات
//    authenticationProvider.setPasswordEncoder(NoOpPasswordEncoder.getInstance());
    //يشفر لي الباس ورد
    authenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return authenticationProvider;
    }
    //سلسله تسوي فلتر للسيكورتي
    //يشفر لي الباس ورد

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
        http.csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //اللي فوق يخص الهجمات عندي

                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                //تأكد مين يقدر يطلب الا API هذا من الاوثرايزريكوست

                .requestMatchers("/api/v1/auth/register").permitAll()
                //اي احد يدخل هذا الاي بي لاني حطيت بريمت اولل واقدر احط كذا api
                .requestMatchers("/api/v1/auth/**").permitAll()
//                       كل شي بعد الاوث يكون مفتوح احط نجمتين
//                .requestMatchers("/api/v1/auth/**").permitAll()

                .requestMatchers("/api/v1/auth/admin").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/auth/user").hasAuthority("USER")
                .requestMatchers("/api/v1/auth/login").hasAnyAuthority("USER","ADMIN")
                //يكون مقفل اي احد غيلر اللي حددتهم موفق ومسجلين دخول
                .anyRequest().authenticated()
                //
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
                  return http.build();
    }
}