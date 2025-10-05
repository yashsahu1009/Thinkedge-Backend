 package in.thinkedge.security;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	private Logger logger=LoggerFactory.getLogger(OncePerRequestFilter.class);
	@Autowired
	private JwtHelper jwtHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
				throws ServletException, IOException {
     	String requestHeader=request.getHeader("Authorization");
//		String requestHeader="Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ0aGlua2VkZ2UiLCJpYXQiOjE3NDEwOTk1ODcsImV4cCI6MTc0MTExNzU4N30.iMtZAyIIHA0ruEWOORX2roOQunQAlfR0AVh36shASzqjZVFuzE0fanvsOFX6kxG3EookAXXVs9rv-KB6-bLOnQ";
		logger.info("Header :{}",requestHeader);
		String username=null;
		String token=null;
		if(requestHeader!=null && requestHeader.startsWith("Bearer")) {
//		lokking good
		token=requestHeader.substring(7);
		try {
			username=this.jwtHelper.getUsernameFromToken(token);
			
		}catch (IllegalArgumentException e) {
			// TODO: handle exception
		logger.info("Illegal Argument while fetching the Username!!");
//			e.printStackTrace();
		}catch (ExpiredJwtException e) {
			logger.info("Given jwt Token is Expired");
//			throw new JWTExpiredException("JWT token has expired. Please login again.");
//			e.printStackTrace();
			sendErrorResponse(response, "JWT token has expired. Please login again.", HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}catch (MalformedJwtException e) {
//			// TODO: handle exception
			logger.info("Some Changed has Done in token!! Invalid Token");
//			throw new JWTExpiredException("Some Changed has Done in token!! Invalid Token");
////			e.printStackTrace();
			sendErrorResponse(response, "Some Changed has Done in token!! Invalid Token.", HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}catch (Exception e) {
//			e.printStackTrace();
			sendErrorResponse(response, "Authentication failed. Your session token is invalid or has been tampered with. Please log in again", HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		}else {
			logger.info("Invlid Header value!!");
	}
		
		if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null) {
//			fetch user details from username
			UserDetails userDetails=this.userDetailsService.loadUserByUsername(username);
			Boolean validToken=this.jwtHelper.validateToken(token, userDetails);
		    if(validToken) {	
//		    	Set the Authentication
		    	UsernamePasswordAuthenticationToken authentication=new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
		    	authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
		    	SecurityContextHolder.getContext().setAuthentication(authentication);
		    }else {
				logger.info("Validation Fails !!!");
			}
			
		}
		filterChain.doFilter(request, response);
	}
	
	

private void sendErrorResponse(HttpServletResponse response, String message, int statusCode) throws IOException {
    response.setStatus(statusCode);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String json = String.format(
        "{ \"timestamp\": \"%s\", \"status\": %d, \"error\": \"%s\", \"message\": \"%s\", \"path\": \"%s\" }",
        java.time.LocalDateTime.now(),
        statusCode,
        HttpStatus.valueOf(statusCode).getReasonPhrase(),
        message,
        "" // optionally: request.getRequestURI()
    );

    response.getWriter().write(json);
}

}
