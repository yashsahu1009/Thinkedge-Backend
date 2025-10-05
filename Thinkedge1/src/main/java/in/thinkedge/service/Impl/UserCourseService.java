package in.thinkedge.service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.thinkedge.model.Course;
import in.thinkedge.model.Transcations;
import in.thinkedge.model.User;
import in.thinkedge.repository.CourseRepository;
import in.thinkedge.repository.TranscationRepository;
import in.thinkedge.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
public class UserCourseService {
	@Autowired
	private UserRepository userRepository;
    @Autowired
	private CourseRepository courseRepository;
    @Autowired
	private TranscationRepository transcationRepository;
    @Transactional
    public Boolean addUserCourse(String email, String courseId) {
    	Optional<Transcations> transcations=transcationRepository.findByEmailAndCourseId(email, courseId);
    	if(transcations.isPresent()) {
    	    User user=userRepository.findById(email).orElse(null);
    	    if(user!=null) {
    	    	Course course=courseRepository.findById(courseId).orElse(null);
    	    	if(course!=null)
    	    	user.addCourse(course);
    	    	userRepository.save(user);
    	    	return true;
    	    }
    		
    	}
    	return false;
    	
    }

}
