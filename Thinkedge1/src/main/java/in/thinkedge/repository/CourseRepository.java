package in.thinkedge.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.thinkedge.model.Course;
import in.thinkedge.model.CourseVideo;
@Repository
public interface CourseRepository extends JpaRepository<Course, String> {

      List<CourseVideo> findByCourseId(String courseId);
}
