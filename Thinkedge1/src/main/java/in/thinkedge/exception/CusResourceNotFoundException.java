package in.thinkedge.exception;

public class CusResourceNotFoundException extends RuntimeException {
 
	private String recourseName;
	private String fieldName;
	private long fielValue;
	public CusResourceNotFoundException(String recourseName, String fieldName, long fielValue) {
		super(String.format("%s not Found with %s : %s ", recourseName,fieldName,fielValue));
		this.recourseName = recourseName;
		this.fieldName = fieldName;
		this.fielValue = fielValue;
	}
	public CusResourceNotFoundException() {
		super();
	}
	public String getRecourseName() {
		return recourseName;
	}
	public void setRecourseName(String recourseName) {
		this.recourseName = recourseName;
	}
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public long getFielValue() {
		return fielValue;
	}
	public void setFielValue(long fielValue) {
		this.fielValue = fielValue;
	}
	
	
}
