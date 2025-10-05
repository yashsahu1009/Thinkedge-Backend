 package in.thinkedge.service.Impl;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.thinkedge.exception.CusResourceNotFoundException;
import in.thinkedge.exception.UserAlreadyRegisterException;
import in.thinkedge.model.Course;
import in.thinkedge.model.Role;
import in.thinkedge.model.User;
import in.thinkedge.repository.RoleRepository;
import in.thinkedge.repository.UserRepository;
import in.thinkedge.utility.OTPUtility;
import jakarta.transaction.Transactional;
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private OTPUtility otpUtility;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Loading user From database by name
	   User user=repository.findById(username).orElseThrow(()->new CusResourceNotFoundException("User","email:"+username,0));
       return user;
        
		
	}
	

	@Transactional
	public User addUser(User user) {
	String opt=otpUtility.generateOtp();
			User user2=repository.findById(user.getEmail()).orElse(null);
			if(user2!=null) {
				throw new UserAlreadyRegisterException("Email already registered!");
			}
			Role role=roleRepository.findById(2).orElse(null);
				user.addRole(role);
				return repository.save(user);
	}
	@Transactional
	public Set<Course> getEntrollCourses(String email){
		Optional<User> optional=repository.findById(email);
		Set<Course>courses=new HashSet<>();
		if(optional.isPresent()) {
			User user=optional.get();
			courses=user.getCourse();
			
			
		}
		return courses;
		
	}
	
	public String getName(String email) {
		String name="";
		Optional<User>optional=repository.findById(email);
		if(optional.isPresent()) {
			User user=optional.get();
			name=user.getName();
		}
		return name;
	}
	

	
	

	
}
