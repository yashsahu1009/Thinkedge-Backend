package in.thinkedge.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import io.jsonwebtoken.ExpiredJwtException;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

	
	@ExceptionHandler(value = CourseNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public handleErrorResponse handleCourseNotFoundException(CourseNotFoundException exception) {
		return new handleErrorResponse();
	}
	
	@ExceptionHandler(value = CourseNotAddedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public handleErrorResponse handleCourseNotAddedException(CourseNotAddedException exception) {
		return new handleErrorResponse();
	}
	
	@ExceptionHandler(value = CustomIOException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public handleErrorResponse handleCustomIOException(CustomIOException exception) {
		return new handleErrorResponse();
	}
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentException(MethodArgumentNotValidException exception) {
		Map<String, String>response=new HashMap<>();
		BindingResult bindingResult=exception.getBindingResult();
		List<FieldError> errorList=bindingResult.getFieldErrors();
		for(FieldError error:errorList) {
			response.put(error.getField(), error.getDefaultMessage());
		}
		return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
		
	}
	
	@ExceptionHandler(value = VideoNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public handleErrorResponse handleVideoNOtFoundException(VideoNotFoundException exception) {
		return new handleErrorResponse();
	}
	
	@ExceptionHandler(value = VideoContentTypeException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public handleErrorResponse handleVideoContentTypeException(VideoContentTypeException exception) {
		return new handleErrorResponse();
	}
	
	@ExceptionHandler(value = VideoAlreadyExistException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public handleErrorResponse handleVideoAlreadyExistException(VideoAlreadyExistException exception) {
		return new handleErrorResponse();
	}
	
	@ExceptionHandler(value = UserAlreadyRegisterException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
//	public handleErrorResponse handleUserAlreadyExisttException(UserAlreadyRegisterException exception) {
//		return new handleErrorResponse();
//	    }
	
	public ResponseEntity<Map<String, String>> handleEmailExists(UserAlreadyRegisterException ex) {
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

	
	@ExceptionHandler(value = CourseRemovedException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public handleErrorResponse handleCourseRemovalException(CourseRemovedException exception) {
		return new handleErrorResponse();
	}
	
}
