package com.tomateunmate.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;

import com.tomateunmate.entitie.Usuario;
import com.tomateunmate.repository.UserRepository;



@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf
                .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
            )
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/login", "/registro", "/registrar-usuario", "/recuperar", 
                               "/resources/**", "/static/**", "/css/**", "/js/**", "/media/**").permitAll()
                .requestMatchers("/index", "/productos", "/ventas", "/compras", 
                               "/clientes", "/proveedores").authenticated()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username") // coincide con name del input
                .passwordParameter("password") // coincide con name del input
                .defaultSuccessUrl("/index", true)
                .failureHandler(authenticationFailureHandler())
                .permitAll()
            )
            .logout(logout -> logout
                .logoutUrl("/logout")  // Coincide con el href en tu HTML
                .logoutSuccessUrl("/login?logout=true")  // Redirección después de logout
                .invalidateHttpSession(true)  // Invalida la sesión
                .deleteCookies("JSESSIONID")  // Elimina la cookie de sesión
                .clearAuthentication(true)  // Limpia la autenticación
                .permitAll()
            )
            .exceptionHandling(exceptions -> exceptions
                .authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/login"))
            );
        
        return http.build();
    }

    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return (request, response, exception) -> {
            request.getSession().setAttribute("AUTH_ERROR", "Usuario o contraseña incorrectos");
            response.sendRedirect("/login");
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> {
            Usuario usuario = userRepository.findByEmail(username);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            return User.withUsername(usuario.getEmail())
                     .password(usuario.getContraseña())
                     .roles(usuario.getRol())
                     .build();
        };
    }
}