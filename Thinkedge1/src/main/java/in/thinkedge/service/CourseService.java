package in.thinkedge.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import in.thinkedge.model.Course;
import io.jsonwebtoken.io.IOException;

public interface CourseService {
	String addCourse(Course course);
//	String addCourse(Course course,MultipartFile file);
    String updateCourse(Course prevcourse , Course updatedCourse);
    String updateCoursePrice(String courseId, double updatedPrice);
    List<Course> getAllCourse();
     Course getCourseDetails(String courseId);
    String updateCourseWithoutImage(String prevCourseId,Course updateCourse);
    double getCoursePrice(String courseId);
     Course getImage(String courseId);
     void removeCourse(String courseId);
    
   String updateCourseWithoutImage(Course course);
    
   
}
