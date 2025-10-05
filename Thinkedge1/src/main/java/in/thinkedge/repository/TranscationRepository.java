package in.thinkedge.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.thinkedge.model.Transcations;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface TranscationRepository extends JpaRepository<Transcations, String> {
   Transcations  findByRazorpayId(String razorpayId);
   Optional<Transcations> findByEmailAndCourseId(String email, String courseId);
}
