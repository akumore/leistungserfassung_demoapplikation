import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.http.Header;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Rest {

	
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
    

    private List<Entry> entriesToday;
    private List<Entry> entriesWeek;
    private List<Project> projects;
    private List<WorkPackage> packages;
    
    private boolean authSuccessful = false;
    
    private boolean projectChosen;
    private boolean workpackageChosen;
    
    private boolean createdEntry;
    private boolean updated;
    private boolean insertError;
    

	public Rest() {
		benutzer = new User();
		
		projectChosen = false;
		workpackageChosen = false;
	}
	
	
	// Getters
	public User getUser() { return benutzer; }
	
	public List<Entry> getEntryDayList() { return entriesToday; }
	
	public List<Entry> getEntryWeekList() { return entriesWeek; }
	
    public List<Project> getProjectList() { return projects; }
    
    public Project getChosenProject() { return chosenProject; }
    
    public WorkPackage getChosenWorkPackage() { return chosenWorkPackage; }
    
    public boolean isAuthSuccess() { return authSuccessful; }
    
    public boolean isProjectChosen() { return projectChosen; }
    
    public boolean isWorkpackageChosen() { return workpackageChosen; }
    
    public boolean hasCreatedEntry() { return createdEntry; }
    
    public boolean isUpdated() { return updated; }
    
    public boolean getInsertError() { return insertError; }
    
    // Setters
    public void setUser(User benutzer) { this.benutzer = benutzer; }
    
    public void setChosenProject(Project cProject) { this.chosenProject = cProject; }
    
    public void setChosenWorkPackage(WorkPackage cWorkPackage) { this.chosenWorkPackage = cWorkPackage; }
    
    public void setAuthSuccess(boolean b) { this.authSuccessful = b; }
    
    public void setProjectChosen(boolean b) { this.projectChosen = b; }
    
    public void setWorkpackageChosen(boolean b) { this.workpackageChosen = b; }
    
    public void setCreatedEntry(boolean b) { this.createdEntry = b; }
    
    public void setUpdated(boolean b) { this.updated = b; }
    
    public void setInsertError(boolean b) { this.insertError = b; }
    
    ///////////////////////////

    public void setupRest() {
    	
    	// Login-URL zusammensetzen
        HttpClient httpclient = HttpClientBuilder.create().build();
        String loginURL = LOGINURL +
                          GRANTSERVICE +
                          "&client_id=" + CLIENTID +
                          "&client_secret=" + CLIENTSECRET +
                          "&username=" + benutzer.getUsername() +
                          "&password=" + benutzer.getPassword();

        // Login requests müssen POST-Methoden sein
        HttpPost httpPost = new HttpPost(loginURL);
        HttpResponse response = null;

        // Login POST-Request ausführen
        try {
            response = httpclient.execute(httpPost);
        } catch (ClientProtocolException cpException) {
            System.out.println(cpException.getMessage());
        } catch (IOException ioException) {
            System.out.println(ioException.getMessage());
        }
        
        // Überprüfen ob das Request erfolgreich war
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
        oauthHeader = new BasicHeader("Authorization", "OAuth " + loginAccessToken);
        System.out.println("oauthHeader1: " + oauthHeader);
        System.out.println("\n" + response.getStatusLine());
        System.out.println("Successful login");
        System.out.println("instance URL: "+loginInstanceUrl);
        System.out.println("access token/session ID: "+loginAccessToken);
        System.out.println("baseUri: "+ baseUri);        
        
        queryUsername();
        queryAccount();
        queryContact();
        
        //release connection
        httpPost.releaseConnection();
        setAuthSuccess(true);
    }

    public void queryUsername() {
        System.out.println("\n_______________ UserName QUERY _______________");
        try {
          HttpClient httpClient = HttpClientBuilder.create().build();
          
          String username = "'" + benutzer.getUsername() + "'";
          
              String uri = baseUri + "/query?q=SELECT+Id,+Name,+Username+FROM+User+WHERE+Username=" + username;

          System.out.println("Query URL: " + uri);
              HttpGet httpGet = new HttpGet(uri);
              System.out.println("oauthHeader2: " + oauthHeader);
              httpGet.addHeader(oauthHeader);
              httpGet.addHeader(prettyPrintHeader);
              
              HttpResponse response = httpClient.execute(httpGet);
              int statusCode = response.getStatusLine().getStatusCode();
              if (statusCode == 200) {
                  String response_string = EntityUtils.toString(response.getEntity());
                  try {
                      JSONObject json = new JSONObject(response_string);
                      System.out.println("JSON result of Query:\n" + json.toString(1));
                      JSONArray j = json.getJSONArray("records");
                      for (int i = 0; i < j.length(); i++) {
                        benutzer.setUserId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                      }
                  } catch (JSONException je) {
                      System.out.println(je.getMessage());
                  }
              } else {
                  System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                  System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                  System.out.println(getBody(response.getEntity().getContent()));
                  System.exit(-1);
              }
          } catch (IOException | NullPointerException ioe) {
              System.out.println(ioe.getMessage());
          }
      }

    public void queryAccount() {
        System.out.println("\n_______________ Account QUERY _______________");
        try {
          HttpClient httpClient = HttpClientBuilder.create().build();
          
          String formatedUserId = "'" + benutzer.getUserId() + "'";
          System.out.println(formatedUserId);
              String uri = baseUri + "/query?q=SELECT+Id,+Name+,+User__c+FROM+Account+WHERE+User__c=" + formatedUserId;
          
          System.out.println("Query URL: " + uri);
              HttpGet httpGet = new HttpGet(uri);
              System.out.println("oauthHeader2: " + oauthHeader);
              httpGet.addHeader(oauthHeader);
              httpGet.addHeader(prettyPrintHeader);
              
              HttpResponse response = httpClient.execute(httpGet);
              int statusCode = response.getStatusLine().getStatusCode();
              if (statusCode == 200) {
                  String response_string = EntityUtils.toString(response.getEntity());
                  try {
                      JSONObject json = new JSONObject(response_string);
                      System.out.println("JSON result of Query:\n" + json.toString(1));
                      JSONArray j = json.getJSONArray("records");
                      for (int i = 0; i < j.length(); i++) {  
                        benutzer.setAccountId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                      }
                  } catch (JSONException je) {
                      System.out.println(je.getMessage());
                  }
              } else {
                  System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                  System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                  System.out.println(getBody(response.getEntity().getContent()));
                  System.exit(-1);
              }
          } catch (IOException | NullPointerException ioe) {
              System.out.println(ioe.getMessage());
          }
      }

    public void queryContact() {
        System.out.println("\n_______________ Contact QUERY _______________");
        try {
          HttpClient httpClient = HttpClientBuilder.create().build();
          
          String formatedAccountId = "'" + benutzer.getAccountId() + "'";
          String uri = baseUri + "/query?q=SELECT+Id,+Name,+Account.Name+FROM+Contact+WHERE+Account.Id=" + formatedAccountId;
          
          System.out.println("Query URL: " + uri);
              HttpGet httpGet = new HttpGet(uri);
              System.out.println("oauthHeader2: " + oauthHeader);
              httpGet.addHeader(oauthHeader);
              httpGet.addHeader(prettyPrintHeader);
              
              HttpResponse response = httpClient.execute(httpGet);
              int statusCode = response.getStatusLine().getStatusCode();
              if (statusCode == 200) {
                  String response_string = EntityUtils.toString(response.getEntity());
                  try {
                      JSONObject json = new JSONObject(response_string);
                      System.out.println("JSON result of Query:\n" + json.toString(1));
                      JSONArray j = json.getJSONArray("records");
                      for (int i = 0; i < j.length(); i++) {  
                        benutzer.setInternalContactId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                      }
                  } catch (JSONException je) {
                      System.out.println(je.getMessage());
                  }
              } else {
                  System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                  System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                  System.out.println(getBody(response.getEntity().getContent()));
                  System.exit(-1);
              }
          } catch (IOException | NullPointerException ioe) {
              System.out.println(ioe.getMessage());
          }
      }

    public void queryDayTracks() throws ParseException {
        System.out.println("\n_______________ Tracks Day QUERY _______________");
        try {

        	SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        	Date now = new Date();
            String strDate = sdfDate.format(now);
        	
            //Set up the HTTP objects needed to make the request.
            HttpClient httpClient = HttpClientBuilder.create().build();

            String uri = baseUri + "/query?q=SELECT"
            		+ "+Id,"
            		+ "+Subject__c,"
            		+ "+Project__c,"
            		+ "+Project__r.Project_Number__c,"
            		+ "+Project__r.Name,"
            		+ "+Work_Package__r.Name,"
            		+ "+Work_Package__c,"
            		+ "+Date__c,"
            		+ "+End_Time_h__c,"
            		+ "+Start_Time_h__c,"
            		+ "+Hours_calc__c"
            		+ "+FROM"
            		+ "+Time_Tracking__c"
            		+ "+WHERE"
            		+ "+Date__c=" + strDate;
            System.out.println("Query URL: " + uri);
            HttpGet httpGet = new HttpGet(uri);
            System.out.println("oauthHeader2: " + oauthHeader);
            httpGet.addHeader(oauthHeader);
            httpGet.addHeader(prettyPrintHeader);

            // Make the request.
            HttpResponse response = httpClient.execute(httpGet);

            // Process the result
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                String response_string = EntityUtils.toString(response.getEntity());
                try {
                  this.entriesToday = new ArrayList<>();
                    JSONObject json = new JSONObject(response_string);
                    System.out.println("JSON result of Query:\n" + json.toString(1));
                    JSONArray j = json.getJSONArray("records");
                    for (int i = 0; i < j.length(); i++){
                      Entry ey = new Entry();
                      ey.setEntryId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                      ey.setEntrySubject(json.getJSONArray("records").getJSONObject(i).getString("Subject__c"));
                      ey.setEntryProjectId(json.getJSONArray("records").getJSONObject(i).getString("Project__c"));
                      ey.setEntryProjectName(json.getJSONArray("records").getJSONObject(i).getJSONObject("Project__r").getString("Name"));
                      ey.setEntryProjectNr(json.getJSONArray("records").getJSONObject(i).getJSONObject("Project__r").getString("Project_Number__c"));
                      ey.setEntryWorkPackageName(json.getJSONArray("records").getJSONObject(i).getJSONObject("Work_Package__r").getString("Name"));
                      ey.setEntryWorkPackageId(json.getJSONArray("records").getJSONObject(i).getString("Work_Package__c"));
                      ey.setEntryDate(json.getJSONArray("records").getJSONObject(i).getString("Date__c"));
                      ey.setEntryStartTime(json.getJSONArray("records").getJSONObject(i).getString("Start_Time_h__c"));
                      ey.setEntryEndTime(json.getJSONArray("records").getJSONObject(i).getString("End_Time_h__c"));
                      ey.setEntryHours(json.getJSONArray("records").getJSONObject(i).getDouble("Hours_calc__c"));
                      entriesToday.add(ey);
                    }
                } catch (JSONException je) {
                    System.out.println(je.getMessage());
                }
            } else {
                System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                System.out.println(getBody(response.getEntity().getContent()));
                System.exit(-1);
            }
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    public void queryWeekTracks() throws ParseException {
        System.out.println("\n_______________ Tracks Week QUERY _______________");
        try {
            String[] strDate = getCurrentWeekDates();
            
            this.entriesWeek = new ArrayList<>();
        	
            for(String s: strDate){
                HttpClient httpClient = HttpClientBuilder.create().build();

                String uri = baseUri + "/query?q=SELECT"
                		+ "+Id,"
                		+ "+Subject__c,"
                		+ "+Project__c,"
                		+ "+Project__r.Project_Number__c,"
                		+ "+Project__r.Name,"
                		+ "+Work_Package__r.Name,"
                		+ "+Work_Package__c,"
                		+ "+Date__c,"
                		+ "+End_Time_h__c,"
                		+ "+Start_Time_h__c,"
                		+ "+Hours_calc__c"
                		+ "+FROM"
                		+ "+Time_Tracking__c"
                		+ "+WHERE"
                		+ "+Date__c=" + s;
                System.out.println("Query URL: " + uri);
                HttpGet httpGet = new HttpGet(uri);
                System.out.println("oauthHeader2: " + oauthHeader);
                httpGet.addHeader(oauthHeader);
                httpGet.addHeader(prettyPrintHeader);
                
             // Make the request.
                HttpResponse response = httpClient.execute(httpGet);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == 200) {
                    String response_string = EntityUtils.toString(response.getEntity());
                    try {
                        JSONObject json = new JSONObject(response_string);
                        System.out.println("JSON result of Query:\n" + json.toString(1));
                        JSONArray j = json.getJSONArray("records");
                        for (int i = 0; i < j.length(); i++){
                          Entry ey = new Entry();
                          ey.setEntryId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                          ey.setEntrySubject(json.getJSONArray("records").getJSONObject(i).getString("Subject__c"));
                          ey.setEntryProjectId(json.getJSONArray("records").getJSONObject(i).getString("Project__c"));
                          ey.setEntryProjectName(json.getJSONArray("records").getJSONObject(i).getJSONObject("Project__r").getString("Name"));
                          ey.setEntryProjectNr(json.getJSONArray("records").getJSONObject(i).getJSONObject("Project__r").getString("Project_Number__c"));
                          ey.setEntryWorkPackageName(json.getJSONArray("records").getJSONObject(i).getJSONObject("Work_Package__r").getString("Name"));
                          ey.setEntryWorkPackageId(json.getJSONArray("records").getJSONObject(i).getString("Work_Package__c"));
                          ey.setEntryDate(json.getJSONArray("records").getJSONObject(i).getString("Date__c"));
                          ey.setEntryStartTime(json.getJSONArray("records").getJSONObject(i).getString("Start_Time_h__c"));
                          ey.setEntryEndTime(json.getJSONArray("records").getJSONObject(i).getString("End_Time_h__c"));
                          ey.setEntryHours(json.getJSONArray("records").getJSONObject(i).getDouble("Hours_calc__c"));
                          entriesWeek.add(ey);
                        }
                    } catch (JSONException je) {
                        System.out.println(je.getMessage());
                    }	
                } else {
                    System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                    System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                    System.out.println(getBody(response.getEntity().getContent()));
                    System.exit(-1);
                }
              }
            } catch (IOException | NullPointerException ioe) {
                    System.out.println(ioe.getMessage());
         }        
    }
        

 
    
    public void queryProjects() {
        System.out.println("\n_______________ Project QUERY _______________");
        try {
          HttpClient httpClient = HttpClientBuilder.create().build();
          
              String uri = baseUri + "/query?q="
              						+ "SELECT"
              						+ "+Id,"
              						+ "+Name,"
              						+ "+Project_Number__c"
              						+ "+FROM"
              						+ "+Project__c";
              
              System.out.println("Query URL: " + uri);
              HttpGet httpGet = new HttpGet(uri);
              System.out.println("oauthHeader2: " + oauthHeader);
              httpGet.addHeader(oauthHeader);
              httpGet.addHeader(prettyPrintHeader);
              
              HttpResponse response = httpClient.execute(httpGet);
              int statusCode = response.getStatusLine().getStatusCode();
              if (statusCode == 200) {
                  String response_string = EntityUtils.toString(response.getEntity());
                  try {
                    projects = new ArrayList<Project>();
                    
                      JSONObject json = new JSONObject(response_string);
                      System.out.println("JSON result of Query:\n" + json.toString(1));
                      JSONArray j = json.getJSONArray("records");
                      for (int i = 0; i < j.length(); i++) {
                        
                        Project pr = new Project();
                        pr.setProjectId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                        pr.setProjectName(json.getJSONArray("records").getJSONObject(i).getString("Name"));
                        pr.setProjectNr(json.getJSONArray("records").getJSONObject(i).getString("Project_Number__c"));
                        projects.add(pr);
                      }
                  } catch (JSONException je) {
                      System.out.println(je.getMessage());
                  }
              } else {
                  System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                  System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                  System.out.println(getBody(response.getEntity().getContent()));
                  System.exit(-1);
              }
          } catch (IOException | NullPointerException ioe) {
              System.out.println(ioe.getMessage());
          }
      }
    
    public void queryWorkPackages() {
        System.out.println("\n_______________ Workpackage QUERY _______________");
        try {
          HttpClient httpClient = HttpClientBuilder.create().build();
          String projectID = "'" + chosenProject.getProjectId() + "'";
          
          String uri = baseUri + "/query?q="
          						+ "SELECT"
          						+ "+Id,"
          						+ "+Name,"
          						+ "+Status__c"
          						+ "+FROM"
          						+ "+Work_Package__c"
          						+ "+WHERE"
          						+ "+Project__r.Id=" + projectID;
          
              System.out.println("Query URL: " + uri);
              HttpGet httpGet = new HttpGet(uri);
              System.out.println("oauthHeader2: " + oauthHeader);
              httpGet.addHeader(oauthHeader);
              httpGet.addHeader(prettyPrintHeader);
              
              HttpResponse response = httpClient.execute(httpGet);
              int statusCode = response.getStatusLine().getStatusCode();
              if (statusCode == 200) {
                  String response_string = EntityUtils.toString(response.getEntity());
                  try {
                    packages = new ArrayList<WorkPackage>();
                      JSONObject json = new JSONObject(response_string);
                      System.out.println("JSON result of Query:\n" + json.toString(1));
                      JSONArray j = json.getJSONArray("records");
                      for (int i = 0; i < j.length(); i++) {
                        
                        WorkPackage wp = new WorkPackage();
                        wp.setWorkPackageId(json.getJSONArray("records").getJSONObject(i).getString("Id"));
                        wp.setWorkPackageName(json.getJSONArray("records").getJSONObject(i).getString("Name"));
                        wp.setWorkPackageStatus(json.getJSONArray("records").getJSONObject(i).getString("Status__c"));
                        System.out.println("Workpackage record is: " + i + ". " + wp.getWorkPackageId() + " " + wp.getWorkPackageName() + " " + wp.getWorkPackageStatus());
                        packages.add(wp);
                        chosenProject.setWorkPackageList(packages);
                      }
                  } catch (JSONException je) {
                      System.out.println(je.getMessage());
                  }
              } else {
                  System.out.println("Query was unsuccessful. Status code returned is " + statusCode);
                  System.out.println("An error has occured. Http status: " + response.getStatusLine().getStatusCode());
                  System.out.println(getBody(response.getEntity().getContent()));
                  System.exit(-1);
              }
          } catch (IOException | NullPointerException ioe) {
              System.out.println(ioe.getMessage());
          }
      }
    

    public String[] getCurrentWeekDates() {
    	Calendar now = Calendar.getInstance();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        String[] days = new String[7];
        int delta = -now.get(GregorianCalendar.DAY_OF_WEEK) + 2; //add 2 if your week start on monday
        now.add(Calendar.DAY_OF_MONTH, delta );
        for (int i = 0; i < 7; i++)
        {
            days[i] = format.format(now.getTime());
            now.add(Calendar.DAY_OF_MONTH, 1);
        }
        System.out.println(days[0]);
        System.out.println(Arrays.toString(days));
        
        return days;
    }
    
    
    public void createEntry(String subject, String date, String startTime, String endTime) {
        System.out.println("\n_______________ Time-Entry-Card INSERT _______________");
        
        String uri = baseUri + "/sobjects/Time_Tracking__c/";
        try {
            JSONObject tt = new JSONObject();
       
            tt.put("RecordTypeId", chosenProject.getProjectRecordTypeId());
            tt.put("Subject__c", subject);
            tt.put("Project__c", getChosenProject().getProjectId());
            tt.put("Work_Package__c", getChosenWorkPackage().getWorkPackageId());
            tt.put("Date__c", date);
            tt.put("Start_Time_h__c", startTime);
            tt.put("End_Time_h__c", endTime);
            tt.put("Internal_Contact__c", benutzer.getInternalContactId());
            tt.put("Status__c", "Submitted by Employee");
            
            System.out.println("JSON for car record to be inserted:\n" + tt.toString(1));

            //Construct the objects needed for the request
            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPost httpPost = new HttpPost(uri);
            httpPost.addHeader(oauthHeader);
            httpPost.addHeader(prettyPrintHeader);
            // The message we are going to post
            StringEntity body = new StringEntity(tt.toString(1));
            body.setContentType("application/json");
            httpPost.setEntity(body);
              
            //Make the request
            HttpResponse response = httpClient.execute(httpPost);

            //Process the results
            int statusCode = response.getStatusLine().getStatusCode();
            
            String response_string = EntityUtils.toString(response.getEntity());
            JSONObject json = new JSONObject(response_string);
            
            if (statusCode == 201) {
                // Store the retrieved lead id to use when we update the lead.
                String ttId = json.getString("id");
                System.out.println("New TT id from response: " + ttId);
            } else {
            	setInsertError(true);
                System.out.println("Insertion unsuccessful. Status code returned is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
            System.out.println(e.toString());
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    
    public void updateEntry(String eId, String subject, String date, String startTime, String endTime) {
        System.out.println("\n_______________ Entry UPDATE _______________");

        String uri = baseUri + "/sobjects/Time_Tracking__c/" + eId;
        try {
            JSONObject entry = new JSONObject();
            entry.put("Subject__c", subject);
            entry.put("Date__c", date);
            entry.put("Start_Time_h__c", startTime);
            entry.put("End_Time_h__c", endTime);
            
            System.out.println("JSON for update of entry record:\n" + entry.toString(1));

            HttpClient httpClient = HttpClientBuilder.create().build();

            HttpPatch httpPatch = new HttpPatch(uri);
            httpPatch.addHeader(oauthHeader);
            httpPatch.addHeader(prettyPrintHeader);
            StringEntity body = new StringEntity(entry.toString(1));
            body.setContentType("application/json");
            httpPatch.setEntity(body);

            //Make the request
            HttpResponse response = httpClient.execute(httpPatch);

            //Process the response
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 204) {
            	setUpdated(true);
                System.out.println("Updated the entry successfully.");
            } else {
            	setUpdated(false);
                System.out.println("Entry update NOT successfully. Status code is " + statusCode);
            }
        } catch (JSONException e) {
            System.out.println("Issue creating JSON or processing results");
        } catch (IOException | NullPointerException ioe) {
            System.out.println(ioe.getMessage());
        }
    }
    
    
    private static String getBody(InputStream inputStream) {
        String result = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(inputStream))) {
            String inputLine;
            while ( (inputLine = in.readLine() ) != null ) {
                result += inputLine;
                result += "\n";
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        return result;
    }
    
}
    
 