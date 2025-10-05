package in.thinkedge.model;


public class JwtResponse {
  private String jwtToken;
  private String username;
  private String role;
public JwtResponse(String jwtToken, String username,String role) {
	super();
	this.jwtToken = jwtToken;
	this.username = username;
	this.role=role;
}
public JwtResponse() {
	super();
}
public String getJwtToken() {
	return jwtToken;
}
public void setJwtToken(String jwtToken) {
	this.jwtToken = jwtToken;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}
public String getRole() {
	return role;
}
public void setRole(String role) {
	this.role = role;
}
@Override
public String toString() {
	return "JwtResponse [jwtToken=" + jwtToken + ", username=" + username + "]";
}

  
  public static JwtResponseBuilder builder() {
      return new JwtResponseBuilder();
  }

  public static class JwtResponseBuilder {
      private String jwtToken;
      private String username;
      private String role;

      JwtResponseBuilder() {
      }

      public JwtResponseBuilder jwtToken(String jwtToken) {
          this.jwtToken = jwtToken;
          return this;
      }

      public JwtResponseBuilder username(String username) {
          this.username = username;
          return this;
      }
      
      public JwtResponseBuilder role(String role) {
    	  this.role=role;
    	  return this;
      }

      public JwtResponse build() {
          return new JwtResponse(this.jwtToken, this.username,this.role);
      }

	@Override
	public String toString() {
		return "JwtResponseBuilder [jwtToken=" + jwtToken + ", username=" + username + ", role=" + role + "]";
	}

      
  }
  
}
