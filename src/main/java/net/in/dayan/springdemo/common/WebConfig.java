package net.in.dayan.springdemo.common;

import net.in.dayan.springdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled=true, prePostEnabled=true)
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    public void  configure(WebSecurity web) {
        web.ignoring().antMatchers("/", "/img/*", "/js/*", "/css/*");
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/login", "/").permitAll()
                .anyRequest().authenticated();
        http.csrf().disable()
                .cors()
                .configurationSource(corsConfigurationSource());
        http.formLogin().loginPage("/login").failureUrl("/login?error").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessUrl("/login").permitAll()
                .invalidateHttpSession(true);
        http.sessionManagement().invalidSessionUrl("/login");

    }

    private CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedMethod(CorsConfiguration.ALL);
        config.addAllowedHeader(CorsConfiguration.ALL);
        config.setAllowCredentials(true);
        config.addAllowedOrigin(CorsConfiguration.ALL);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
//        auth.userDetailsService(userService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new PasswordEncoder() {
            @Override
            public String encode(CharSequence cs) {
                return cs.toString();
            }

            @Override
            public boolean matches(CharSequence cs, String s) {
                return cs.toString().equals(s);
            }
        };
    }

}
