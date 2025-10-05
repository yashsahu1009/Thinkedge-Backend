 package in.thinkedge.controller;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import in.thinkedge.model.CourseVideo;
import in.thinkedge.service.Impl.CourseVideoServiceImpl;

import jakarta.validation.Valid;

@RestController
@CrossOrigin
public class CourseVideoController {

    @Value("${project.manager}")
    private String VIDEO_URL;

    @Autowired
    private CourseVideoServiceImpl courseVideoServiceImpl;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/addVideo")
    public ResponseEntity<?> addVideo(
            @RequestParam("courseId") String courseId,
            @RequestParam("title") @Valid String title,
            @RequestParam("video") MultipartFile file) {

        Boolean status = courseVideoServiceImpl.addCourseVideo(courseId, title, file);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Course Video Successfully Added");
        response.put("status", HttpStatus.CREATED.value());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/showVideo")
    public ResponseEntity<?> getVideos(@RequestParam("courseId") String courseId) {
        List<CourseVideo> courseVideos = courseVideoServiceImpl.getAllCourseVideos(courseId);
        List<Map<String, Object>> responseList = new ArrayList<>();

        for (CourseVideo courseVideo : courseVideos) {
            Map<String, Object> response = new HashMap<>();
            response.put("video_id", courseVideo.getVideoId());
            response.put("title", courseVideo.getTitle());
            response.put("video_url", "http://localhost:8081/api/getVideo?filename=" + courseVideo.getVideoUrl());
            responseList.add(response);
        }

        return ResponseEntity.status(HttpStatus.OK).body(responseList);
    }

    @GetMapping("/getVideo")
    public ResponseEntity<Resource> getVideo(@RequestParam("filename") String filename) {
        try {
            Path filePath = Paths.get(VIDEO_URL).resolve(filename).normalize();
            Resource resource = new UrlResource(filePath.toUri());

            if (!resource.exists()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            return ResponseEntity.ok()
                    .contentType(MediaTypeFactory.getMediaType(resource).orElse(MediaType.APPLICATION_OCTET_STREAM))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/deleteVideo")
    public ResponseEntity<Map<String, Object>> removeVideo(
            @RequestParam("title") String title,
            @RequestParam("courseId") String courseId) {

        Boolean status = courseVideoServiceImpl.removeCourseVideo(title, courseId);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Video Removed Successfully with title: " + title);
        response.put("status", HttpStatus.OK.value());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
