import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Entry {

	private String entryId;
	private String entryRecordTypeId;
	private String entryRecordTypeName;
	private String entrySubject;
	private String entryProjectId;
	private String entryProjectName;
	private String entryProjectNr;
	private String entryWorkPackageId;
	private String entryWorkPackageName;
	private String entryDate;
	private String entryStartTime;
	private String entryEndTime;
	private String entryHours;
	

	// Getter
	public String getEntryId() { return entryId; }
	
	public String getEntryProjectId() { return entryProjectId; }
	
	public String getEntryProjectName() { return entryProjectName; }
	
	public String getEntryProjectNr() { return entryProjectNr; }
	
	public String getEntrySubject() { return entrySubject; }
	
	public String getEntryWorkPackageId() { return entryWorkPackageId; }
	
	public String getEntryWorkPackageName() { return entryWorkPackageName; }
	
	public String getEntryStartTime() { return entryStartTime; }
	
	public String getEntryDate() { return entryDate; }
	
	public String getEntryEndTime() { return entryEndTime; }
	
	public String getEntryHours() { return entryHours; }
	
	public String getEntryRecordTypeId() { return entryRecordTypeId; }
	
	public String getEntryRecordTypeName() { return entryRecordTypeName; }
	
	// Setter
	public void setEntryId(String entryId) { this.entryId = entryId; }
	
	public void setEntryProjectId(String entryProjectId) { this.entryProjectId = entryProjectId; }
	
	public void setEntryProjectName(String entryProjectName) { this.entryProjectName = entryProjectName; }
	
	public void setEntryProjectNr(String entryProjectNr) { this.entryProjectNr = entryProjectNr; }
	
	public void setEntrySubject(String entrySubject) { this.entrySubject = entrySubject; }
	
	public void setEntryWorkPackageId(String entryWorkPackageId) { this.entryWorkPackageId = entryWorkPackageId; }
	
	public void setEntryWorkPackageName(String entryWorkPackageName) { this.entryWorkPackageName = entryWorkPackageName; }
	
	public void setEntryStartTime(String entryStartTime) throws ParseException {
		String formattedTime;
		String oldFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'+'SSSS";
		String newFormat = "HH:mm";
		
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = sdf.parse(entryStartTime);
		
		sdf.applyPattern(newFormat);
		formattedTime = sdf.format(d);
		this.entryStartTime = formattedTime;
	}
	
	public void setEntryDate(String entryDate) throws ParseException { 
		
		String formattedTime;
		String oldFormat = "yyyy-MM-dd";
		String newFormat = "dd.MM.yyyy";
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = sdf.parse(entryDate);
		sdf.applyPattern(newFormat);
		formattedTime = sdf.format(d);
		this.entryDate = formattedTime;
	}
	
	public void setEntryEndTime(String entryEndTime) throws ParseException { 
		
		String formattedTime;
		String oldFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'+'SSSS";
		String newFormat = "HH:mm";
		
		SimpleDateFormat sdf = new SimpleDateFormat(oldFormat);
		Date d = sdf.parse(entryEndTime);
		
		sdf.applyPattern(newFormat);
		formattedTime = sdf.format(d);
		this.entryEndTime = formattedTime;
	}
	
	public void setEntryHours(double entryHours) {
		this.entryHours = String.valueOf(entryHours);
	}	
	
	public void setEntryRecordTypeId(String entryRecordTypeId) { this.entryRecordTypeId = entryRecordTypeId; }
	
	public void setEntryRecordTypeName(String entryRecordTypeName) { this.entryRecordTypeName = entryRecordTypeName; }	
}
