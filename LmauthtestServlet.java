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
				.setAudience(Arrays.asList("48100979992-12u9s24hq2um6mo0en6la0vdmgilqp3i.apps.googleusercontent.com"))
				.setIssuer("https://accounts.google.com").build();
		
			try{
				GoogleIdToken idToken = verifier.verify(idTokenString);
				if (idToken != null) {
					Payload payload = idToken.getPayload();
					returnVal = "User ID: " + payload.getSubject();
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
