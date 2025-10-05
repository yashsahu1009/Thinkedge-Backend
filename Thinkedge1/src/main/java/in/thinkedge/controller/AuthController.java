 package in.thinkedge.controller;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.parser.Part.IgnoreCaseType;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.thinkedge.exception.StudentNotFoundException;
import in.thinkedge.model.ErrorResponse;
import in.thinkedge.model.JwtRequest;
import in.thinkedge.model.JwtResponse;
import in.thinkedge.model.User;
import in.thinkedge.security.JwtHelper;
import in.thinkedge.service.Impl.CustomUserDetailService;
import in.thinkedge.service.Impl.EmailService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private CustomUserDetailService customUserDetailService;
	@Autowired
	private JwtHelper helper;
	@Autowired
	private EmailService emailService;
	
	private Logger logger=LoggerFactory.getLogger(AuthController.class);
	 @PostMapping("/login")
	 public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

	        this.doAuthenticate(request.getEmail(), request.getPassword());
            

	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        String token = this.helper.generateToken(userDetails);
	        Collection<? extends GrantedAuthority> authorities=userDetails.getAuthorities();
	        String role=authorities.toString();
	        JwtResponse response = JwtResponse.builder().jwtToken(token)
	                .username(userDetails.getUsername()).role(role.substring(1,role.length()-1)).build();
	        String subject="Successful Login to Your Account";
	        LocalDateTime dateTime = LocalDateTime.now();
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

	        String formattedDateTime = dateTime.format(formatter);
//	        ZonedDateTime dateTime = ZonedDateTime.now();
	        String text="Hi "+userDetails.getUsername()+",\r\n"
	        		+ "\r\n"
	        		+ "We wanted to let you know that your account was successfully logged in on "+formattedDateTime+" .\r\n"
	        		+ "\r\n"
	        		+ "If this was you, there's nothing to worry about! However, if you did not authorize this login, please secure your account immediately by resetting your password or contacting our support team.\r\n"
	        		+ "\r\n"
	        		+ "Thank you for being with us!\r\n"
	        		+ "\r\n"
	        		+ "Best regards,\r\n"
	        		+ "Thinkedge";
	        emailService.sendEmail(userDetails.getUsername(),subject ,text);
	        return new ResponseEntity<>(response, HttpStatus.OK);
	       
	    }

	    private void doAuthenticate(String email, String password) {

	        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
	        try {
	           authenticationManager.authenticate(authentication);


	        } catch (Exception e) {
	        	
	            throw new StudentNotFoundException(" Invalid Username or Password  !!");
	        }

	    }
	    
	    @PostMapping("/singup")
	    public ResponseEntity<?> craeteStudent(@RequestBody @Valid User user) {
	            User user2=customUserDetailService.addUser(user);
	    		Map<String,Object>response=new HashMap<>();
	    		response.put("message","Register Sucessfully");
//	    		response.put("user", user2.getRoles());
	    		response.put("status", HttpStatus.CREATED.value());
	    		String subject="Welcome to Thinkedge! 🎉";
	    		String text="Dear "+user2.getName()+",\r\n"
	    				+ "\r\n"
	    				+ "Thank you for signing up for Thinkedge! We're excited to have you on board.\r\n"
	    				+ "\r\n"
	    				+ "Your account has been successfully created. You can now explore all the features and benefits of our platform.\r\n"
	    				+ "\r\n"
	    				+ "🔹 Your Registered Email: "+user2.getEmail()+"\r\n"
	    				+ "\r\n"
	    				+ "If you have any questions, feel free to reach out to our support team at thinkedge@info.com.\r\n"
	    				+ "\r\n"
	    				+ "Welcome aboard! \r\n"
	    				+ "\r\n"
	    				+ "Best regards,\r\n"
	    				+ "Thinkedge\r\n";
	    		 emailService.sendEmail(user2.getEmail(),subject ,text);
	    		return new ResponseEntity<>(response,HttpStatus.CREATED);
	    } 
	    
	    @GetMapping("/username")
	    public ResponseEntity<Map> getName(@RequestParam("email") String email){
	    	String username=customUserDetailService.getName(email);
	    	Map<String, String> response=new HashMap<>();
	    	response.put("username", username);
	    	return ResponseEntity.ok(response);
	    }
}
