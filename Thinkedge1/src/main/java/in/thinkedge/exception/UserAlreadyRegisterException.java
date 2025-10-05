package in.thinkedge.exception;

public class UserAlreadyRegisterException extends RuntimeException {

	public UserAlreadyRegisterException(String message) {
		super(message);
	}
	public UserAlreadyRegisterException(String message, Throwable throwable) {
		super(message,throwable);
	}
	

}
