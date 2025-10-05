package in.thinkedge.exception;

import lombok.experimental.SuperBuilder;

public class CourseRemovedException extends RuntimeException {
	public CourseRemovedException(String message) {
		super(message);
	}

}
