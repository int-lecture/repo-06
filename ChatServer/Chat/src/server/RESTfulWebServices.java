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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

@Path("/")
public class RESTfulWebServices {

	// Endlosschleife bei jeder Nachricht
	/**
	 * Datumsformatierung
	 */
	public static final String ISO8601 = "yyyy-MM-dd'T'HH:mm:ssZ";

	/**
	 * HashMap zur Speicherung der Usermessages
	 */
	private static Map<String, UserID> userMessages = new HashMap<>();

	/**
	 * Empfängt Nachrichten
	 * 
	 * @param jsonMessage
	 *            Übergebene Nachricht als String
	 * @return Response ob alles oder garnichts geklappt hat
	 * @throws JSONException
	 */
	@Path("/send")
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response send(String jsonMessage) throws JSONException {
		try {
			UserID user;
			Message message = Message.transferJSONinMessage(jsonMessage);
			if (message.to != null && message.from != null && message.date != null && message.text != null) {
				if (!userMessages.containsKey(message.to)) {
					user = new UserID(message.to);
					userMessages.put(message.to, user);
					user.sendMessage(message);
				} else {
					user = userMessages.get(message.to);
					user.sendMessage(message);
				}

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
	 */
	@GET
	@Path("/messages/{user_id}/{sequence_number}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response recieveMessage(@PathParam("user_id") String userID, @PathParam("sequence_number") int sequenceNr) {
		if (userMessages.containsKey(userID)) {
			JSONArray jsonMessageArray = new JSONArray();
			UserID user = userMessages.get(userID);
			List<Message> newMessages = user.revieveMessage(sequenceNr);
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
	 */
	@GET
	@Path("/messages/{user_id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response revieveAllMessages(@PathParam("user_id") String userID) {
		return recieveMessage(userID, 0);
	}

}
