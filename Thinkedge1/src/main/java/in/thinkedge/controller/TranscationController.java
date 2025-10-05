package in.thinkedge.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.RazorpayException;

import in.thinkedge.model.Transcations;
import in.thinkedge.service.Impl.TranscationServiceImpl;
import in.thinkedge.service.Impl.UserCourseService;

@RestController
@CrossOrigin
public class TranscationController {
	@Autowired
	private TranscationServiceImpl transcationServiceImpl;
	@Autowired
	private UserCourseService userCourseService;

	@PostMapping(value = "/createTranscation")
	public ResponseEntity<?> createTranscation(@RequestBody Transcations transcations) throws RazorpayException{
		Transcations transcations2=transcationServiceImpl.createTranscations(transcations);
		if(transcations!=null) {
			Boolean status=userCourseService.addUserCourse(transcations2.getEmail(), transcations.getCourseId());
			if(status) {
				Map<String, Object> response=new HashMap<>();
				response.put("message", "Thank you For your Payment. Your transcation has been completed Successfully");
				response.put("status", HttpStatus.OK.value());
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		}
		
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(transcations2);
	}
	

	@PostMapping("/paymentCallback")
	public String paymentCallback(@RequestParam Map<String, String> response) {
		 	transcationServiceImpl.updateStatus(response);
		 	return "success";
		
	}
	
	
}
