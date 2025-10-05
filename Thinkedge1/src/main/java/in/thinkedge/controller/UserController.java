 package in.thinkedge.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.thinkedge.model.Course;
import in.thinkedge.service.Impl.CustomUserDetailService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	private CustomUserDetailService customUserDetailService;
	
	@GetMapping("/start")
	public ResponseEntity<List<Map>> getEnrolleCourse(@RequestParam("email") String email){ 
		Set<Course> courses=customUserDetailService.getEntrollCourses(email);
		List<Map> courses2=new ArrayList<>();
		for(Course course:courses) {
			Map<String, Object> response=new HashMap<>();
			response.put("courseId", course.getCourseId());
			response.put("title", course.getTitle());
			response.put("description", course.getDescription());
			response.put("imageData", course.getImageData());
			courses2.add(response);
		}
		return ResponseEntity.status(HttpStatus.OK).body(courses2);
	}
	
}
