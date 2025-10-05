package in.thinkedge.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
@Entity
public class CourseVideo {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer videoId;
	@NotBlank(message = "title must be required")
	private String title;
	private String  videoUrl;
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "course_id", nullable = false)
	private Course course;
	public CourseVideo(Integer videoId, String title, String videoUrl,Course course ) {
		super();
		this.videoId=videoId;
		this.title = title;
		this.videoUrl = videoUrl;
		this.course=course;
	
	}
	public CourseVideo() {
		
	}
	public Integer getVideoId() {
		return videoId;
	}
	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getVideoUrl() {
		return videoUrl;
	}
	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	
	  @Override
	public String toString() {
		return "CourseVideo [videoId=" + videoId + ", title=" + title + ", videoUrl=" + videoUrl + ", course=" + course
				+ "]";
	}


}
