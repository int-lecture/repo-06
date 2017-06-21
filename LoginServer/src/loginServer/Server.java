package loginServer;

import java.io.IOException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

@Path("")
public class Server {

	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	private StorageProviderMongoDB strPrMDB = new StorageProviderMongoDB();

	public static void main(String[] args) throws IOException {

		final String baseUri = "http://0.0.0.0:5001/";
		final String paket = "loginServer";
		final Map<String, String> initParams = new HashMap<String, String>();
		initParams.put("com.sun.jersey.config.property.packages", paket);
		System.out.println("Starte grizzly...");
		SelectorThread threadSelector = GrizzlyWebContainerFactory.create(baseUri, initParams);
		System.out.printf("Grizzly lÃ¤uft unter %s%n", baseUri);
		System.out.println("[ENTER] drÃ¼cken, um Grizzly zu beenden");
		System.in.read();
		threadSelector.stopEndpoint();
		System.out.println("Grizzly wurde beendet");
		System.exit(0);
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(String jsonString) throws JSONException, ParseException {

		String benutzerName;
		String passwort;
		// geprüft ob jsonString das richtige Format hat
		// wenn es falsch formatiert ist, dann wird Bad-Requuest gesendet
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			benutzerName = jsonObject.getString("benutzer");
			passwort = jsonObject.getString("passwort");

		} catch (JSONException e) {
			return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").build();
		}

		// User wird von datenbank geholt
		User user = strPrMDB.retrieveUser(benutzerName);
		// wenn es den User gibt und sein Pass mit dem erzeugtem Pass
		// ubereinstimmt, dann wird den Token für den User erzeugt
		if (user != null && user.VerifyPassword(passwort)) {
			JSONObject jsonObject = new JSONObject();
			user.GenerateToken();
			// zu den Token wird ein ablaufdatum bestimmt
			try {
				// SimpleDateFormat formatierung statt Date
				SimpleDateFormat sdf = new SimpleDateFormat(Server.ISO8601);
				Calendar expireDate = user.GetTokenExpireDate();

				// übergeben an json
				jsonObject.put("expire-date", sdf.format(expireDate.getTime()));
				jsonObject.put("token", user.GetToken().toString());
				jsonObject.put("pseudonym", user.pseudonym);
			} catch (JSONException e) {
				// fehler von server
				return Response.status(Response.Status.INTERNAL_SERVER_ERROR).header("Access-Control-Allow-Origin", "*")
						.build();

			}

			SimpleDateFormat sdf = new SimpleDateFormat(Server.ISO8601);
			Calendar expireDate = user.GetTokenExpireDate();

			strPrMDB.saveToken(user.GetToken(), sdf.format(expireDate.getTime()), user.pseudonym);

			return Response.status(Response.Status.OK).entity(jsonObject.toString())
					.header("Access-Control-Allow-Origin", "*").build();
		} else {
			// nicht authorisiert
			return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build();
		}
	}

	@OPTIONS
	@Path("/login")
	public Response optionsReg() {
		return Response.ok("").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}

	@OPTIONS
	@Path("/auth")
	public Response optionsProfile() {
		return Response.ok("").header("Access-Control-Allow-Origin", "*")
				.header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
				.header("Access-Control-Allow-Credentials", "true")
				.header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
				.header("Access-Control-Max-Age", "1209600").build();
	}

	@POST
	@Path("/auth")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response auth(String jsonString) throws JSONException {
		String token;
		String pseudonym;
		// es wird geprüft ob jsonString richtige format hat
		try {
			JSONObject jsonObject = new JSONObject(jsonString);
			token = jsonObject.getString("token");
			pseudonym = jsonObject.getString("pseudonym");

		} catch (JSONException e) {
			// wenn Format nicht passt, wird Fehler ausgegeben
			return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").build();

		}
		// expire date wird aus datenbank geholt
		String expireDate = strPrMDB.retrieveToken(pseudonym, token);

		if (expireDate != null) {
			SimpleDateFormat sdf = new SimpleDateFormat(Server.ISO8601);
			Date date;
			try {
				date = sdf.parse(expireDate);
			} catch (ParseException ex1) {
				System.out.println("Falsche Datum");
				return Response.status(Response.Status.BAD_REQUEST).header("Access-Control-Allow-Origin", "*").build();
			}
			Calendar calendar = Calendar.getInstance();
			// geprüfft ob Token abgelaufen ist
			if (calendar.getTime().before(date)) {
				JSONObject jsonObject = new JSONObject();
				try {
					sdf = new SimpleDateFormat(Server.ISO8601);
					jsonObject.put("success", "true");
					jsonObject.put("expire-date", expireDate);
					return Response.status(Response.Status.OK).entity(jsonObject.toString())
							.header("Access-Control-Allow-Origin", "*").build();

				} catch (JSONException e) {
					// nicht autorisiert
					return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*")
							.build();
				}
			} else {
				// token wird gelöscht
				strPrMDB.deleteToken(token);
			}

		}

		return Response.status(Response.Status.UNAUTHORIZED).header("Access-Control-Allow-Origin", "*").build();

	}

}