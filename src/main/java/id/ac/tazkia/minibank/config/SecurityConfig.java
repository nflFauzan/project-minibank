@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsServiceImpl userDetailsService;

   @Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
      .csrf(csrf -> csrf.disable())
      .authorizeHttpRequests(auth -> auth
          .requestMatchers("/", "/login", "/signup", "/css/**", "/js/**", "/images/**").permitAll()
          .requestMatchers("/admin/**").hasRole("ADMIN")
          .anyRequest().authenticated()
      )
      .formLogin(form -> form
          .loginPage("/login")
          .usernameParameter("username")
          .passwordParameter("password")
          .defaultSuccessUrl("/dashboard", true)
          .permitAll()
      )
      .logout(logout -> logout.logoutSuccessUrl("/login?logout").permitAll());

    return http.build();
}


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
