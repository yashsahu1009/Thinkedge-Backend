 package in.thinkedge.config;

import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//
//import in.thinkedge.security.JwtAuthenticationEnteryPoint;
//import in.thinkedge.security.JwtAuthenticationFilter;
//
//@Configuration
//public class SecurityConfig {
//	
//	@Autowired
//	private JwtAuthenticationEnteryPoint point;
//	
//	@Autowired
//	private JwtAuthenticationFilter jwtAuthenticationFilter;
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http)throws Exception{
//
//        http.csrf(csrf -> csrf.disable()).cors(cors->cors.disable()).authorizeHttpRequests(auth-> auth.requestMatchers("/home/**").authenticated().requestMatchers("/auth/login").permitAll().anyRequest().authenticated()).exceptionHandling(ex->ex.authenticationEntryPoint(point)).sessionManagement(session-> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));    
//        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//	}
//
//}

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import in.thinkedge.security.JwtAuthenticationEnteryPoint;
import in.thinkedge.security.JwtAuthenticationFilter;
import in.thinkedge.service.Impl.CustomUserDetailService;
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthenticationEnteryPoint point;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    private CustomUserDetailService userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .cors() // Enable CORS here
            .and()
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/auth/login","/login","/auth/singup","/admin/AllCourse","/getVideo","/auth/username").permitAll()
                .requestMatchers("/home/**").authenticated()
                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
    
    

    //CORS Configuration Bean (Required for Spring Security)
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        CorsConfiguration config = new CorsConfiguration();
//        config.addAllowedOrigin("http://localhost:5173");
//        config.addAllowedOrigin("http://localhost:3000");
//        config.addAllowedOrigin("http://localhost:5174");
//       
//        
//        config.addAllowedHeader("*");
//        config.addAllowedMethod("*");
//        config.setAllowCredentials(true);
//        source.registerCorsConfiguration("/**", config);
//        return new CorsFilter(source);
//    }
//    @Bean
//    public DaoAuthenticationProvider dodaoAuthenticationProvider() {
//    	DaoAuthenticationProvider daoAuthenticationProvider =new DaoAuthenticationProvider();
//    	daoAuthenticationProvider.setUserDetailsService(userDetailsService);
//    	daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
//    	return daoAuthenticationProvider;
//    }

	    protected void configure(AuthenticationManagerBuilder auth)throws Exception{
	    	auth.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
	    }
	    
//	    public AuthenticationManager authenticationManagerBean()throws Exception{
//	    	return super.authenticationManagerBean();
//	    }
	    @Bean
	    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
	        AuthenticationManagerBuilder authenticationManagerBuilder = 
	                http.getSharedObject(AuthenticationManagerBuilder.class);
	        authenticationManagerBuilder.userDetailsService(this.userDetailsService).passwordEncoder(passwordEncoder);
	        return authenticationManagerBuilder.build();
	    }
}

