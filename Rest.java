import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Rest {

	// REST API Salesforce variable decleration.
    private final String LOGINURL     = "https://test.salesforce.com";
    private final String GRANTSERVICE = "/services/oauth2/token?grant_type=password";
    private final String CLIENTID     = "3MVG98RqVesxRgQ6Z7Y7R1sNd0r3MCkY7GvFo9whQ8C_M2cpVtMBUCGP8VPo7qffBeOyUxUOrAkvD4ZvNkBbk";
    private final String CLIENTSECRET = "599090607021828048";
    private final String REST_ENDPOINT = "/services/data" ;
    private final String API_VERSION = "/v34.0" ;
    private String baseUri;
    private Header oauthHeader;
    private final Header prettyPrintHeader = new BasicHeader("X-PrettyPrint", "1");
    
    private User benutzer;
    private Project chosenProject;
    private WorkPackage chosenWorkPackage;
    
    // Declaration of Auto collection list.
    private List<Entry> entries;
    private List<Project> projects;
    private List<WorkPackage> packages;
    


	// Default Constructor
	public Rest() {
		benutzer = new User();
	}
	
	// Getters
	public User getUser() { return benutzer; }
	
	public List<Entry> getEntryList() { return entries; }
	
    public List<Project> getProjectList() { return projects; }
    
    public Project getChosenProject() { return chosenProject; }
    
    public WorkPackage getChosenWorkPackage() { return chosenWorkPackage; }
    
    
    // Setters
    public void setUser(User benutzer) { this.benutzer = benutzer; }
    
    public void setChosenProject(Project cProject) { this.chosenProject = cProject; }
    
    public void setChosenWorkPackage(WorkPackage cWorkPackage) { this.chosenWorkPackage = cWorkPackage; }
    
    
    public void setupRest() {
        /*
    	 *	Establishing the REST functions, assembling the URL for requests,
    	 *	Checking if the login credentials allow for user authentication.
    	 */

        HttpClient httpclient = HttpClientBuilder.create().build();

        // Assemble the login request URL
        String loginURL = LOGINURL +
                          GRANTSERVICE +
                          "&client_id=" + CLIENTID +
                          "&client_secret=" + CLIENTSECRET +
                          "&username=" + benutzer.getUsername() +
                          "&password=" + benutzer.getPassword();

        // Login requests must be POSTs
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;

        // Execute the login POST request
        try {
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            System.out.println(cpException.getMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        // verify response is HTTP OK
        final int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode != HttpStatus.SC_OK) {
            System.out.println("Error authenticating to Force.com: "+statusCode);
            return;
        }

        String getResult = null;
        
        try {
            getResult = EntityUtils.toString(response.getEntity());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }

        JSONObject jsonObject = null;
        String loginAccessToken = null;
        String loginInstanceUrl = null;

        try {
            jsonObject = (JSONObject) new JSONTokener(getResult).nextValue();
            loginAccessToken = jsonObject.getString("access_token");
            loginInstanceUrl = jsonObject.getString("instance_url");
        } catch (JSONException jsonException) {
            System.out.println(jsonException.getMessage());
        }

        baseUri = loginInstanceUrl + REST_ENDPOINT + API_VERSION ;
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken) ;
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        System.out.println("baseUri: "+ baseUri);        
        
        //release connection
        httpPost.releaseConnection();
    }
}
    
 