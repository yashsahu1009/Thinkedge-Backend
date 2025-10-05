package in.thinkedge.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import in.thinkedge.model.Course;
import in.thinkedge.model.CourseVideo;
import java.util.List;

@Repository
public interface CourseVideoRepository extends JpaRepository<CourseVideo,Integer> {
   
	 CourseVideo findByVideoUrl(String videoUrl);
//	 @Query("Select courseVideo.videoId as video_id , courseVideo.title as title, courseVideo.videoUrl as video_url from CourseVideo courseVideo  where courseVideo.courseId=:courseId")
	 @Query(value = "Select * from course_video where course_id=?1", nativeQuery = true)
	 List<CourseVideo> getAllVideosByCourseId(String courseId);
	 
	Optional<CourseVideo>  findByTitleAndCourse(String title,Course course);
}
