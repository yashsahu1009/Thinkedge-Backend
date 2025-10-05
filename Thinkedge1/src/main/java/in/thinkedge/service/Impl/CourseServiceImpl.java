package in.thinkedge.service.Impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.thinkedge.exception.CourseNotAddedException;
import in.thinkedge.exception.CourseNotFoundException;
import in.thinkedge.exception.CourseRemovedException;
import in.thinkedge.model.Course;
import in.thinkedge.repository.CourseRepository;
import in.thinkedge.service.CourseService;
import jakarta.transaction.Transactional;

@Service
public class CourseServiceImpl implements CourseService {
	@Autowired
	private CourseRepository repository;

	@Override
	public String addCourse(Course course) {
		String status="Course Registration failed !";
	
	    Course course1=repository.save(course);
	    if(course1==null) {
	    	throw new CourseNotAddedException(status);
	    }
	    status="Course Registration Added Succesfully With id:"+course1.getCourseId();
		
	  return status;
	}
	
    

	
	@Override
	public String updateCourse(Course prevcourse, Course updatedCourse) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateCoursePrice(String courseId, double updatedPrice) {
		String status="update Price faild";
		Optional<Course> courseOptional=repository.findById(courseId);
		if(courseOptional.isPresent()) {
			Course course=courseOptional.get();
			course.setPrice(updatedPrice);
			repository.save(course);
			status="Product Price Updated Successfully ";
			
		}
		return status;
	}

	@Override
	public List<Course> getAllCourse() {
		List<Course> courses=repository.findAll();
		List<Course>courses2=new ArrayList<>();
		for(Course course:courses) {
			if(course.getAvailable()) {
				courses2.add(course);
			}
		}
		return courses2;
	}

	@Override
	public Course getCourseDetails(String courseId) {
		Course course=repository.findById(courseId).orElseThrow(()->new CourseNotFoundException("Enter a valid courseId!"));
		return course;
	}

	@Override
	public String updateCourseWithoutImage(String prevCourseId, Course updateCourse) {
		String status="Updation failed";
		Course course=repository.findById(prevCourseId).orElseThrow(()->new CourseNotFoundException("Course ID's Do not match Updation failed"));
		
			course.setTitle(updateCourse.getTitle());
			course.setDescription(updateCourse.getDescription());
			course.setPrice(updateCourse.getPrice());
			course.setAvailable(updateCourse.getAvailable());
			repository.save(course);
			status="Succesfully Updated";
		return status;
	}

	@Override
	public double getCoursePrice(String courseId) {
		Course course=repository.findById(courseId).get();
		if(course!=null) {
			return course.getPrice();
		}
		
      return 0.0;
	}

	@Override
	public Course getImage(String courseId) {
		Optional<Course> optional=repository.findById(courseId);
		if(!optional.isPresent()) {
			return null;
		}
		return optional.get();
	}

	@Override
	@Transactional
	public void removeCourse(String courseId) {
	Course course=repository.findById(courseId).orElseThrow(()-> new CourseNotFoundException("Enter the Valid courseId:"+courseId+" is Not Found"));
	    if(course.getAvailable()==false) {
	    	throw new CourseNotFoundException("Enter the Valid courseId:"+courseId+" is Not Found");
	    }
	    	course.setAvailable(false);
	    	Course course2=repository.save(course);
	    	if(course2==null) {
	    		throw new CourseRemovedException("Course removal failed. Please try again or contact support if the issue persists.");
	    	}
	}


	@Override
	public String updateCourseWithoutImage(Course updateCourse) {
		String status="Updation failed";
		Course course=repository.findById(updateCourse.getCourseId()).orElseThrow(()->new CourseNotFoundException("Course ID's Do not match Updation failed"));
			course.setTitle(updateCourse.getTitle());
			course.setDescription(updateCourse.getDescription());
			course.setPrice(updateCourse.getPrice());
			course.setAvailable(updateCourse.getAvailable());
			repository.save(course);
			status="Succesfully Updated";
		return status;
		
	}



	

}
