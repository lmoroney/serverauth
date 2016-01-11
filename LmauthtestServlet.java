/*
 * Servlet for App Engine to test Auth with the canonical samples from google-samples
 */
package com.lmoroney;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;


@SuppressWarnings("serial")
public class LmauthtestServlet extends HttpServlet {
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseVal = processToken(request);
		response.getWriter().write(responseVal);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String responseVal = processToken(request);
		response.getWriter().write(responseVal);

	}
	
	private String processToken(HttpServletRequest request){
		String returnVal="";
		String idTokenString = request.getParameter("id_token");
		NetHttpTransport transport = new NetHttpTransport();
		GsonFactory jsonFactory = new GsonFactory();
		
		if(idTokenString != null && !idTokenString.equals("")){
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
				.setAudience(Arrays.asList(ENTER_YOUR_SERVER_CLIENT_ID_HERE))
				// To learn about getting a Server Client ID, see this link
				// https://developers.google.com/identity/sign-in/android/start
				// And follow step 4
				.setIssuer("https://accounts.google.com").build();
		
			try{
				GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {
					Payload payload = idToken.getPayload();
					returnVal = "User ID: " + payload.getSubject();
					// You can also access the following properties of the payload in order
					// for other attributes of the user. 
					// String email = payload.getEmail();
					// boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
					// String name = (String) payload.get("name");
					// String pictureUrl = (String) payload.get("picture");
					// String locale = (String) payload.get("locale");
					// String familyName = (String) payload.get("family_name");
					// String givenName = (String) payload.get("given_name");
				} else {
					returnVal = "Invalid ID token.";
				}
			} catch (Exception ex){
				returnVal = ex.getMessage();
			}
		}
		else{
			returnVal = "Bad Token Passed In";
		}
		
		return returnVal;
	}
	
}
