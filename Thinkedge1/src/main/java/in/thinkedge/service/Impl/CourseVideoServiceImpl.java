 package in.thinkedge.service.Impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import in.thinkedge.exception.CourseNotFoundException;
import in.thinkedge.exception.CustomIOException;
import in.thinkedge.exception.VideoAlreadyExistException;
import in.thinkedge.exception.VideoContentTypeException;
import in.thinkedge.exception.VideoNotFoundException;
import in.thinkedge.model.Course;
import in.thinkedge.model.CourseVideo;
import in.thinkedge.repository.CourseRepository;
import in.thinkedge.repository.CourseVideoRepository;

import jakarta.transaction.Transactional;

@Service
public class CourseVideoServiceImpl {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CourseVideoRepository videoRepository;

    @Value("${project.manager}")
    private String FOlDER_URL;

    @Transactional
    public Boolean addCourseVideo(String courseId, String videoTitle, MultipartFile file) {
        if (!file.getContentType().equals("video/mp4")) {
            throw new VideoContentTypeException("Invalid content type. Only MP4 is allowed.");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));

        CourseVideo existingVideo = videoRepository.findByTitleAndCourse(videoTitle, course).orElse(null);
        if (existingVideo != null) {
            throw new VideoAlreadyExistException("Video with this title already exists.");
        }

        String filename = file.getOriginalFilename();
        String filePath = FOlDER_URL + filename;

        CourseVideo video = new CourseVideo();
        video.setTitle(videoTitle);
        video.setVideoUrl(filename); // Save only filename

        try {
            file.transferTo(new File(filePath));
        } catch (IOException e) {
            throw new CustomIOException("File save failed: " + e.getMessage());
        }

        course.addVideo(video);
        courseRepository.save(course);
        return true;
    }

    public List<CourseVideo> getAllCourseVideos(String courseId) {
        List<CourseVideo> courseVideos = videoRepository.getAllVideosByCourseId(courseId);
        return courseVideos;
    }

    public byte[] getVideoByUrl(String videoUrl) throws IOException {
        return Files.readAllBytes(new File(FOlDER_URL + videoUrl).toPath());
    }

    @Transactional
    public Boolean removeCourseVideo(String title, String courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new CourseNotFoundException("Course not found."));

        CourseVideo courseVideo = videoRepository.findByTitleAndCourse(title, course)
                .orElseThrow(() -> new VideoNotFoundException("Video not found with the given title."));

        course.removeVideo(courseVideo);
        courseRepository.save(course);
        videoRepository.delete(courseVideo);
        return true;
    }
}
