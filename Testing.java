public class Testing {

	public Testing() {
		System.out.println("-- Testing Unit initialisiert");
	}
	
	public void getUserAttribtues(User user){
		
		System.out.println("-- Getting User attributes --");
        System.out.println("Benutzer: " + 
				user.getUserId() + 
				" " + 
				user.getUsername() +
				" " +
				user.getAccountId() +
				" " +
				user.getInternalContactId());
	}
	
	
}
