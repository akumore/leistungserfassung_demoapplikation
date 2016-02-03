public class Entry {

	private String entryId;
	private String entryRecordTypeId;
	private String entryRecordTypeName;
	private String entrySubject;
	private String entryProject;
	private String entryWorkPackage;
	private String entryDate;
	private String entryStartTime;
	private String entryEndTime;
	private String entryHours;
	

	// Getter
	public String getEntryId() { return entryId; }
	public String getEntryProject() { return entryProject; }
	public String getEntrySubject() { return entrySubject; }
	public String getEntryWorkPackage() { return entryWorkPackage; }
	public String getEntryStartTime() { return entryStartTime; }
	public String getEntryDate() { return entryDate; }
	public String getEntryEndTime() { return entryEndTime; }
	public String getEntryHours() { return entryHours; }
	public String getEntryRecordTypeId() { return entryRecordTypeId; }
	public String getEntryRecordTypeName() { return entryRecordTypeName; }
	
	// Setter
	public void setEntryId(String entryId) { this.entryId = entryId; }
	public void setEntryProject(String entryProject) { this.entryProject = entryProject; }
	public void setEntrySubject(String entrySubject) { this.entrySubject = entrySubject; }
	public void setEntryWorkPackage(String entryWorkPackage) { this.entryWorkPackage = entryWorkPackage; }
	public void setEntryStartTime(String entryStartTime) { this.entryStartTime = entryStartTime; }
	public void setEntryDate(String entryDate) { this.entryDate = entryDate; }
	public void setEntryEndTime(String entryEndTime) { this.entryEndTime = entryEndTime; }
	public void setEntryHours(int entryHours) {
		String hoursInString = Integer.toString(entryHours);
		this.entryHours = hoursInString;
	}	
	public void setEntryRecordTypeId(String entryRecordTypeId) { this.entryRecordTypeId = entryRecordTypeId; }
	public void setEntryRecordTypeName(String entryRecordTypeName) { this.entryRecordTypeName = entryRecordTypeName; }	
}
