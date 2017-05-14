package loginServer;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import org.apache.commons.codec.binary.Base64;

@Path("/")
public class Login {

	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

	private static Map<String, String> passwordList = new HashMap<>();
	private static ArrayList<String> tokenList = new ArrayList();

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(String jsonString) throws JSONException, ParseException {

		JSONObject jsonObject = new JSONObject(jsonString);
		// Wenn die JsonDatei falsch formatiert ist, dann Sende 400/Bad-Request.
		if (!jsonObject.optString("name").isEmpty() || !jsonObject.optString("password").isEmpty()
				|| !jsonObject.optString("pseudonym").isEmpty()) {
			// Wenn das Passwort falsch ist dann sende Unautorisiert ohne
			// Message.
			String user = jsonObject.optString("name");
			String password = jsonObject.optString("password");
			if (checkPassword(user, password)) {
				JSONObject tokenObject = new JSONObject();
				tokenObject.put("token", createToken("testToken")).put("expire-date", new Date());
				return Response.ok().entity(tokenObject.toString()).build();

			} else {
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}

	@POST
	@Path("/auth")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response auth(String jsonString) throws JSONException {
		JSONObject jsonObject = new JSONObject(jsonString);
		if (!jsonObject.optString("token").isEmpty() || !jsonObject.optString("expire-date").isEmpty()) {	
			if(tokenList.contains(jsonObject.optString("token"))){
				JSONObject successJsonObject = new JSONObject();
				successJsonObject.put("success", "true").put("expire-Date", new Date());
				return Response.ok().entity(successJsonObject.toString()).build();
			}else{
				return Response.status(Response.Status.UNAUTHORIZED).build();
			}
			
		} else {
			return Response.status(Response.Status.BAD_REQUEST).build();
		}
	}


	private String createToken(String stringToEncode) {
		byte[] encoded = Base64.encodeBase64(stringToEncode.getBytes());
		String token = new String(encoded);
		tokenList.add(token);
		return token;
	}

	private boolean checkPassword(String user, String password) {
		String check = passwordList.get(user);
		byte[] decoded = Base64.decodeBase64(check);
		String compare = new String(decoded).toString();
		if (password.equals(compare)) {
			return true;
		} else {
			return false;
		}
	}

	@PUT
	@Path("/setuppassword")
	public Response setupPassword() {
		passwordList.put("dennis", createToken("test"));
		return Response.ok().build();

	}


}
