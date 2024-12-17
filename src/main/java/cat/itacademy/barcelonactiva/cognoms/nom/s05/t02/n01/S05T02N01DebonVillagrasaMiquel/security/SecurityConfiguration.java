package cat.itacademy.barcelonactiva.cognoms.nom.s05.t02.n01.S05T02N01DebonVillagrasaMiquel.security;
 
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity  // Allow to use @PreAuthorize
public class SecurityConfiguration {

    private final AuthenticationProvider authProvider; // Injected automatically

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(
                        "api/mysql/auth/**",
                        "/images/**", "/css/**", "/static/**", "/error/**", "/img/**", "/json/**",
                        "/page/login", "/page/register", "/page/home", "/page/actionRegister", "/paginable")
                .permitAll()
                .requestMatchers("/page/players").authenticated()
                .requestMatchers("/admin/home").hasRole("ADMIN")
                
                .anyRequest()
                .authenticated()
                .and()
                .formLogin(form -> form
                        .loginPage("/page/login")
                        .defaultSuccessUrl("/page/home")
                        .loginProcessingUrl("/page/login")
                        .failureUrl("/page/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutRequestMatcher(new AntPathRequestMatcher("/page/logout"))
                        .logoutSuccessUrl("/page/login?logout=true")
                        .invalidateHttpSession(true)
                        .permitAll()
                )
                .authenticationProvider(authProvider); // Valid provider
        return http.build();
    }
}
