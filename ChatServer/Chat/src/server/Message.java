package server;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.*;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

public class Message {
	String from;
	String to;
	Date date;
	String text;
	int sequenceNr;
	String token;

	public Message(String token, String from, String to, Date date, String text, int sequenceNr) {
		this.from = from;
		this.to = to;
		this.date = date;
		this.text = text;
		this.sequenceNr = sequenceNr;
		this.token = token;
	}

	public Message(String from, String to, Date date, String text) {
		this("", from, to, date, text, 0);
	}

	public static Message transferJSONinMessage(String jsonObject) throws ParseException, JSONException {
		Date date = null;
		try {
			JSONObject obj = new JSONObject(jsonObject);
			if (obj.has("date")) {
				String datum = obj.getString("date");
				SimpleDateFormat sdf = new SimpleDateFormat(Server.ISO8601);
				date = sdf.parse(datum);
			}

			return new Message(obj.optString("from"), obj.optString("to"), jsonObject, date, obj.optString("text"),
					obj.optInt("sequence"));
		} catch (JSONException ex) {
			throw new ParseException("String was not a valid JSON Message object.", -1);
		}

	}

	public JSONObject transferInJSONObject() throws JSONException {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("from", this.from).put("to", this.to)
				.put("date", new SimpleDateFormat(Server.ISO8601).format(date)).put("text", this.text)
				.put("sequence", sequenceNr).put("token", this.token);

		return jsonObject;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		SimpleDateFormat sdf = new SimpleDateFormat(Server.ISO8601);

		return String.format("{ 'from': '%s', 'to': '%s', 'date': '%s', 'text': '%s'}".replace('\'', '"'), from, to,
				sdf.format(new Date()), text);
	}

}
