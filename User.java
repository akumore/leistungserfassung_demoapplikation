public class User {

	private String userId;
	private String accountId;
	private String internalContactId;
	private String username;
    private String password;
        
    public User() {}
   
    // Getter
    public String getUserId() { return userId; }
    public String getAccountId() { return accountId; }
    public String getInternalContactId() { return internalContactId; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }

    // Setter
    public void setUserId(String uId) { this.userId = uId; }
    public void setAccountId(String id) { this.accountId = id; }
    public void setInternalContactId(String id) { this.internalContactId = id; }
    public void setUsername(String uName) { this.username = uName; }
    public void setPassword(String pass) { this.password = pass; }
}

