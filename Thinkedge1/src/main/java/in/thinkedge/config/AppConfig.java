 package in.thinkedge.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.webauthn.registration.PublicKeyCredentialCreationOptionsFilter;

@Configuration
public class AppConfig {
   
//	@Bean
//	public UserDetailsService userDetailsService() {
//		UserDetails user=User.builder().username("thinkedge").password(passwordEncoder().encode("An1234")).roles("ADMIN").build();
//		return new InMemoryUserDetailsManager(user);
//	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
//	@Bean
//	public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception{
//		return builder.getAuthenticationManager();
//	}
}
