package in.thinkedge.controller;



import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.event.NamespaceChangeListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.server.util.matcher.NegatedServerWebExchangeMatcher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import in.thinkedge.exception.CusResourceNotFoundException;
import in.thinkedge.exception.CustomIOException;
import in.thinkedge.model.Course;
import in.thinkedge.model.ErrorResponse;
import in.thinkedge.model.Message;
import in.thinkedge.service.Impl.CourseServiceImpl;
import jakarta.validation.Valid;

@RestController
@RequestMapping("admin")
@CrossOrigin
public class CourseController {
	@Autowired
	private CourseServiceImpl courseServiceImpl;
	
////	Add Course Contoller
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addcourse")
	public ResponseEntity<Map> createCourse(@RequestParam("title") String title ,@RequestParam("description") String description, @RequestParam("price")double price, @RequestParam("available")Boolean available , @RequestParam("file")MultipartFile file){
		
		Course course=new  Course();
		try {
		course.setImageData(file.getBytes());
		}catch (IOException e) {
			throw new CustomIOException("Failed to added course: "+e.getMessage());
		}
		course.setImageName(file.getOriginalFilename());
		course.setImageType(file.getContentType());
		course.setDescription(description);
		course.setTitle(title);
		course.setPrice(price);
		course.setAvailable(available);
	   String status=courseServiceImpl.addCourse(course);
			HashMap<String, Object> response=new HashMap<>();
			response.put("message", status);
			response.put("status", HttpStatus.CREATED.value());
			return new ResponseEntity<>(response,HttpStatus.CREATED);
			
		}
    
// Get All Course Controller
    @GetMapping("/AllCourse")
    public ResponseEntity<List<Course>> getAllCourse(){
    	List<Course> courses=courseServiceImpl.getAllCourse();
    	return new ResponseEntity<>(courses,HttpStatus.OK);
    }
    

//    update Course Controller
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/updateCourseWithoutimage")
    public ResponseEntity<Map> updatedCourse(@RequestBody @Valid Course course){
    	String result=courseServiceImpl.updateCourseWithoutImage(course);
    		Map<String,Object> response=new HashMap<>();
    		response.put("message", result);
    		response.put("status", HttpStatus.OK.value());
    		return new ResponseEntity<>(response,HttpStatus.OK);
    	}
    
//    Get Price Based on the CourseId
    @GetMapping("/getPrice")
    public ResponseEntity<?> getPrice(@RequestParam("courseId")String courseId){
    	double price=courseServiceImpl.getCoursePrice(courseId);
    		Message message=new Message();
    		return  ResponseEntity.status(HttpStatus.OK).body(message);
    	
    }
//    Fetch all the Details of based on the CourseId 
    @GetMapping("/getCourseDetails")
    public ResponseEntity<Course> getCourseDetails(@RequestParam("courseId")String courseId){
    	Course course=courseServiceImpl.getCourseDetails(courseId);
    		return ResponseEntity.ok(course); 
    }
 
    
//    Get image of Course
    @GetMapping("/{courseId}/image")
    public ResponseEntity<?> getImage(@PathVariable String courseId){
    	Course course=courseServiceImpl.getImage(courseId);
    	if(course!=null) {
    		return ResponseEntity.ok().contentType(MediaType.valueOf(course.getImageType())).body(course.getImageData());
    	}
    	ErrorResponse errorResponse=new ErrorResponse(HttpStatus.BAD_REQUEST.value(),"Enter Valid Course Id");
    	return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse); 
    }
    
//    Remove Course Controller 
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/removeCourse")
    public ResponseEntity<Map> removeCoures(@RequestParam("courseId")String courseId){
       courseServiceImpl.removeCourse(courseId);
       Map<String, Object>response=new HashMap<>();
       response.put("message", "Course removed successfully courseId:"+courseId);
       response.put("status", HttpStatus.OK.value());
       return new ResponseEntity<>(response,HttpStatus.OK);
    }
 
	
}
