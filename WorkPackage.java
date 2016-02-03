public class WorkPackage {
	
	private String workPackageId;
	private String workPackageName;
	private String workPackageStatus;
	private String workPackageProject;
	
	public WorkPackage() {}

	// Getter
	public String getWorkPackageId() { return workPackageId; }
	public String getWorkPackageName() { return workPackageName; }
	public String getWorkPackageStatus() { return workPackageStatus; }
	public String getWorkPackageProject() { return workPackageProject; }
	
	// Setter
	public void setWorkPackageId(String wId) { this.workPackageId = wId; }
	public void setWorkPackageName(String wName) { this.workPackageName = wName; }
	public void setWorkPackageStatus(String wStatus) { this.workPackageStatus = wStatus; }
	public void setWorkPackageProject(String wProject) { this.workPackageProject = wProject; }
}
