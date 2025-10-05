package in.thinkedge.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.UuidGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Course {
	    @Id
	    @UuidGenerator
		private String courseId;
	    @NotBlank(message = "title must be required")
		private String title;
        
		private Double price;
		
		@Column(length = 1500)
		@NotBlank(message = "Description must be required")
		private String Description;
		@Lob
		private byte[] imageData;
		private String imageName;
		private String imageType;
		@JsonIgnore
		@ManyToMany(mappedBy = "course" , fetch = FetchType.LAZY)
	    private Set<User> users=new HashSet<>();
		@JsonIgnore
		@OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
		private List<CourseVideo> videos=new ArrayList<>();
		private boolean available;

		public Course(String title, Double price, String description, byte[] imageData, String imageName,
				String imageType, Set<User> users, Boolean available) {
			super();
			this.title = title;
			this.price = price;
			Description = description;
			this.imageData = imageData;
			this.imageName = imageName;
			this.imageType = imageType;
			this.users= users;
			this.available = available;
		}

		public List<CourseVideo> getVideos() {
			return videos;
		}

		public void setVideos(List<CourseVideo> videos) {
			this.videos = videos;
		}

		public void setAvailable(boolean available) {
			this.available = available;
		}

		public Course() {
			super();
		}

		public String getCourseId() {
			return courseId;
		}

		public void setCourseId(String courseId) {
			this.courseId = courseId;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Double getPrice() {
			return price;
		}

		public void setPrice(Double price) {
			this.price = price;
		}

		public String getDescription() {
			return Description;
		}

		public void setDescription(String description) {
			Description = description;
		}

		public byte[] getImageData() {
			return imageData;
		}

		public void setImageData(byte[] imageData) {
			this.imageData = imageData;
		}

		public String getImageName() {
			return imageName;
		}

		public void setImageName(String imageName) {
			this.imageName = imageName;
		}

		public String getImageType() {
			return imageType;
		}

		public void setImageType(String imageType) {
			this.imageType = imageType;
		}

		public Set<User> getUsers() {
			return users;
		}

		public void setUsers(Set<User> users) {
			this.users = users;
		}

		public Boolean getAvailable() {
			return available;
		}

		public void setAvailable(Boolean available) {
			this.available = available;
		}
		
		 public void addUser(User user) {
		        this.users.add(user);
		        user.getCourse().add(this);
		    }

		    public void removeUser(User user) {
		        this.users.remove(user);
		        user.getCourse().remove(this);
		    }
		    
		    public void addVideo(CourseVideo video) {
		    	videos.add(video);
		    	video.setCourse(this);// Setting the back reference
		    }
		    public void removeVideo(CourseVideo video) {
		    	videos.remove(video);
		    	video.setCourse(null);// Clearing the back reference
		    }

			@Override
			public String toString() {
				return "Course [courseId=" + courseId + ", title=" + title + ", price=" + price + ", Description="
						+ Description + ", imageData=" + Arrays.toString(imageData) + ", imageName=" + imageName
						+ ", imageType=" + imageType + ", users=" + users + ", videos=" + videos + ", available="
						+ available + "]";
			}

}
