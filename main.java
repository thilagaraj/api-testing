import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.RestAssured;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Main {

	public static void main(String[] args) {		
		RestAssured.baseURI = "https://checkinapp.info/webapi";	
		RequestSpecification httpRequest = RestAssured.given();	
		JSONObject requestParams = new JSONObject();
		requestParams.put("client_id", "7007"); 
		requestParams.put("client_secret", "meODJymLFKzsAQIAsMdN");		  
		requestParams.put("grant_type", "password");
		requestParams.put("username", "tk.thilagaraj@gmail.com");
		requestParams.put("password",  "kMb#6reJ");
		requestParams.put("companycode",  "CKN1538118865");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.body(requestParams.toJSONString());
		Response response = httpRequest.post("/oauth/token");
		//String responseBody = response.getBody();
		JsonPath jsonPathEvaluator = response.jsonPath();		 
		String accessToken = jsonPathEvaluator.get("access_token");


		httpRequest.body("{\r\n" + 
				"	\"payload\":{\r\n" + 
				"		\"filter\":{\r\n" + 
				"			\"startRecord\":0,\r\n" + 
				"			\"numberOfRecords\":10,\r\n" + 
				"			\"isAll\":false,\r\n" + 
				"			\"sortColumn\":\"\",\r\n" + 
				"			\"sortOrder\":\"\"\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"}");
		httpRequest.header("Content-Type", "application/json");
		httpRequest.header("Authorization", "Bearer " + accessToken);
		Response response1 = httpRequest.post("/attendees/getAll");
		JsonPath jsonPathEvaluator1 = response1.jsonPath();	
		
		System.out.println("Response Body is =>  " + accessToken);
		System.out.println("Response Body is =>  " + jsonPathEvaluator1.get("data.totalRecords"));
	}

}
