import java.util.ArrayList;
import java.util.List;

public class Project {

	private String projectId;
	private String projectName;
	private String projectNr;
	private String projectRecordTypeId;
	private String projectRecordTypeName;
	private List<WorkPackage> workpackageList;
	
	public Project() {							
		workpackageList = new ArrayList<WorkPackage>();
		this.projectRecordTypeId = "01211000000DCEBAA4";
		this.projectRecordTypeName = "Project Time Tracking";
	}
	
	// Getter
	public String getProjectId() { return projectId; }
	public String getProjectName() { return projectName; }
	public String getProjectNr() { return projectNr; }
	public String getProjectRecordTypeId() { return projectRecordTypeId; }
	public String getProjectRecordTypeName() { return projectRecordTypeName; }
	public List<WorkPackage> getWorkPackageList() { return workpackageList; }
	
	// Setter
	public void setProjectId(String pId) { this.projectId = pId; }
	public void setProjectName(String pName) { this.projectName = pName; }
	public void setProjectNr(String number) { this.projectNr = number; }
	public void setProjectRecordTypeId(String pRecordType) { this.projectRecordTypeId = pRecordType; }
	public void setProjectRecordTypeName(String pRecordType) { this.projectRecordTypeName = pRecordType; }
	public void setWorkPackageList(List<WorkPackage> wp) { this.workpackageList = wp;}
	
	public void addWorkPackages(WorkPackage wp) {
		workpackageList.add(wp);
	}
}
