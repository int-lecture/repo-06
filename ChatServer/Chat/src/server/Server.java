package server;


import java.util.HashMap;

import java.util.List;
import java.util.Map;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.sun.grizzly.http.SelectorThread;
import com.sun.jersey.api.container.grizzly.GrizzlyWebContainerFactory;

public class Server {
	// Endlosschleife bei jeder Nachricht
		/**
		 * Datumsformatierung
		 */
		public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

		/**
		 * HashMap zur Speicherung der Usermessages
		 */
		//private static Map<String, UserID> userMessages = new HashMap<>();

		/**
		 * Empfängt Nachrichten
		 *
		 * @param jsonMessage
		 *            Übergebene Nachricht als String
		 * @return Response ob alles oder garnichts geklappt hat
		 * @throws JSONException
		 */

	StorageProviderMongoDB db = new StorageProviderMongoDB();

	public static void main(String[] args) throws IOException {

		final String baseUri = "http://0.0.0.0:5000/";
		final String paket = "server";
		final Map<String, String> initParams = new HashMap<String, String>();
		initParams.put("com.sun.jersey.config.property.packages", paket);
		System.out.println("Starte grizzly...");
		SelectorThread threadSelector = GrizzlyWebContainerFactory.create(
		baseUri, initParams);
		System.out.printf("Grizzly läuft unter %s%n", baseUri);
		System.out.println("[ENTER] drücken, um Grizzly zu beenden");
		System.in.read();
		threadSelector.stopEndpoint();
		System.out.println("Grizzly wurde beendet");
		System.exit(0);
	}

	@Path("/send")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response send(String jsonMessage) throws JSONException {
		try {
			UserID user;
			Message message = Message.transferJSONinMessage(jsonMessage);
			if (message.to != null && message.from != null && message.date != null && message.text != null) {
				user = db.retrieveUser(message.from);
				if(user==null){
					return Response.status(Response.Status.UNAUTHORIZED).build();
				}
				user.setToken(message.token);

				if(!user.auth()){
					return Response.status(Response.Status.UNAUTHORIZED).build();
				}

				message.sequenceNr=db.getUpdatedSequence(message.to);
				db.storeMessages(message);

				try {
					return Response.ok().entity(message.transferInJSONObject().toString()).build();
				} catch (JSONException e) {
					e.printStackTrace();
					return Response.serverError().build();
				}
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return Response.status(Response.Status.BAD_REQUEST).entity("Message was not correctly formatted").build();
	}

	/**
	 * Methode die einem alle Nachrichten ab einer bestimmten Sequenznummer
	 * liefert. Dabei wird geprüft ob der Nutzername bereits in der HashMap
	 * enthalten ist. Ist dies der Fall, dann wird ein JsonArray erstellt, in
	 * dem alle neuen Nachrichten gespeichert werden nachdem sie in ein
	 * JsonObject umgewandelt worden sind.
	 *
	 * @param userID
	 *            Name des Users
	 * @param sequenceNr
	 *            Sequenznummer
	 * @return Response falls alles oder garnichts geklappt hat
	 * @throws ParseException
	 */
	@GET
	@Path("/messages/{user_id}/{sequence_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recieveMessage(@PathParam("user_id") String userID, @PathParam("sequence_number") int sequenceNr,@Context HttpHeaders header) throws ParseException {

		UserID user = db.retrieveUser(userID);

		if (user!=null) {
			JSONArray jsonMessageArray = new JSONArray();

			List<Message> newMessages = db.retrieveMessages(userID,sequenceNr,true);
			MultivaluedMap<String, String> hmap = header.getRequestHeaders();
			String token = hmap.get("Authorization").get(0).substring(6);
			System.out.println(token);
			if (hmap.get("Authorization") == null || hmap.get("Authorization").isEmpty()) {
				return Response.status(Status.UNAUTHORIZED).build();
			}
			if (newMessages.isEmpty()) {
				return Response.noContent().entity("No Messages").build();
			} else {
				for (Message message : newMessages) {
					try {
						jsonMessageArray.put(message.transferInJSONObject());
					} catch (JSONException e) {
						return Response.serverError().build();
					}

				}
				try {
					return Response.ok().entity(jsonMessageArray.toString()).build();
				} catch (Exception e) {
					return Response.serverError().build();
				}
			}

		} else {
			return Response.status(Response.Status.BAD_REQUEST).entity("No User").build();
		}
	}

	/**
	 * Methode die die Funktion <code>recieveMessage(userID, 0)</code> aufruft.
	 * Die <code>0</code> gibt an, das ab der ersten Nachricht mit der
	 * Sequenznummer 0 alle Nachrichten gespeichert werden sollen.
	 *
	 * @param userID
	 *            Übergebener User Name
	 * @return Response wenn bzw. wenn kein Fehler aufgetretten ist.
	 * @throws ParseException
	 */
	@GET
	@Path("/messages/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response revieveAllMessages(@PathParam("user_id") String userID,@Context HttpHeaders header) throws ParseException {
		return recieveMessage(userID, 0,header);
	}

}
