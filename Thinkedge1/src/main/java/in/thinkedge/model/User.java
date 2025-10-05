 package in.thinkedge.model;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
@Entity
public class User implements UserDetails {

	    @Id
	    @NotBlank(message = "Email is required")
	    @Email(message = "Please enter a valid email address")
		private String email;
	    @NotBlank(message = "Name is required")
		private String name;
	    @NotBlank(message = "mobile no. is rquired")
	    @Pattern(regexp = "\\d{10}", message = "Mobile number must contain exactly 10 digits.")
		private String mobile;
	    @NotBlank(message = "Password is required")
	    @Size(min = 8,max = 200, message = "Password must be at least 8 characters long. ")
		private String password;
		@ManyToMany
	    @JoinTable(
	      name = "student_course", 
	      joinColumns=@JoinColumn(name = "email"), 
	      inverseJoinColumns = @JoinColumn(name = "course_id"))
		private Set<Course> course=new HashSet<>();
		@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
		@JoinTable(
				name = "user_role",
				joinColumns = @JoinColumn(name = "email",referencedColumnName = "email"),
				inverseJoinColumns = @JoinColumn(name="role",referencedColumnName = "id")
				)
		private Set<Role>roles=new HashSet<>();
		
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public String getPassword() {
			return password;
		}
		 private String encodePassword(String password) {
		        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		        return encoder.encode(password);
		    }
		public void setPassword(String password) {
			this.password = encodePassword(password);
		}
		public Set<Course> getCourse() {
			return course;
		}
		public void setCourse(Set<Course> course) {
			this.course = course;
		}
		public Set<Role> getRoles() {
			return roles;
		}
		public void setRoles(Set<Role> roles) {
			this.roles = roles;
		}
		@Override
		public String toString() {
			return "User [email=" + email + ", name=" + name + ", mobile=" + mobile + ", password=" + password
					+ ", course=" + course + "]";
		}
		@Override
		public Collection<? extends GrantedAuthority> getAuthorities() {
			// TODO Auto-generated method stub
		List<SimpleGrantedAuthority>authorities=this.roles.stream().map((role)-> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
			return authorities;
		}
		@Override
		public String getUsername() {
			// TODO Auto-generated method stub
			return this.email;
		}
		
	    public void addCourse(Course course) {
	        this.course.add(course);
	        course.getUsers().add(this);
	    }

	    public void removeCourse(Course course) {
	        this.course.remove(course);
	        course.getUsers().remove(this);
	    }
		
		public void addRole(Role role) {
			this.roles.add(role);
			role.getUsers().add(this);
		}
		
		public void removeRole(Role role) {
			this.roles.remove(role);
			role.getUsers().remove(this);
		}
}
