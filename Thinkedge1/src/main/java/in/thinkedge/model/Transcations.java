package in.thinkedge.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Transcations {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String transId;
	@NotBlank(message ="email is required")
	@Email(message = "email enter valid formate")
	private String email;
	private Date transDate;
	
	private double amount;
	private String transStatus;
	private String razorpayId;
	@NotBlank(message = "name is required")
	private String name;
	@NotBlank(message = "mobile Number is required")
	private String mobile;
	@NotBlank(message = "courseId must be required")
	private String courseId;
	public Transcations(String transId, String email, Date transDate, double amount, String transStatus,
			String razorpayId, String name, String mobile, String courseId) {
		super();
		this.transId = transId;
		this.email = email;
		this.transDate = transDate;
		this.amount = amount;
		this.transStatus = transStatus;
		this.razorpayId = razorpayId;
		this.name = name;
		this.mobile = mobile;
		this.courseId=courseId;
	}
	public String getCourseId() {
		return courseId;
	}
	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}
	public Transcations() {
		super();
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getTransDate() {
		return transDate;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getTransStatus() {
		return transStatus;
	}
	public void setTransStatus(String transStatus) {
		this.transStatus = transStatus;
	}
	public String getRazorpayId() {
		return razorpayId;
	}
	public void setRazorpayId(String razorpayId) {
		this.razorpayId = razorpayId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	public String toString() {
		return "Transcations [transId=" + transId + ", email=" + email + ", transDate=" + transDate + ", amount="
				+ amount + ", transStatus=" + transStatus + ", razorpayId=" + razorpayId + ", name=" + name
				+ ", mobile=" + mobile + ",courseId="+courseId+"]";
	}
	
	

}
