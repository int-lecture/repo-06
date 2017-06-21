package loginServer;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.and;
import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Storage provider for a MongoDB.
 */
class StorageProviderMongoDB {

	private static final String MONGO_URL = "mongodb://127.0.0.1:27017";
	/** URI to the MongoDB instance. */
	private static MongoClientURI connectionString = new MongoClientURI(MONGO_URL);

	/** Client to be used. */
	private static MongoClient mongoClient = new MongoClient(connectionString);

	/** Mongo database. */
	private static MongoDatabase database = mongoClient.getDatabase("chat");

	/**
	 * @see var.chat.server.persistence.StorageProvider#retrieveMessages(java.lang.String,
	 *      long, boolean)
	 */
	public synchronized User retrieveUser(String benutzername) {

		MongoCollection<Document> collectionAccount = database.getCollection("profiles");
		// Information über User
		Document document = collectionAccount.find(eq("user", benutzername)).first();
		// keine Daten
		if (document == null) {
			return null;
		}
		User user = new User(benutzername, document.getString("gespeichertesPassword"), document.getString("pseudonym"), true);
		return user;
	}

	/**
	 *
	 */
	public synchronized void saveToken(String token, String expirationDate, String pseudonym) {

		MongoCollection<Document> collectionToken = database.getCollection("token");

		// user zur database hinzufügen
		Document document = new Document("token", "" + token + "").append("expire-date", expirationDate)
				.append("pseudonym", pseudonym);

		if (collectionToken.find(eq("pseudonym", pseudonym)).first() != null) {
			collectionToken.updateOne(eq("pseudonym", pseudonym), new Document("set", document));
		} else {
			collectionToken.insertOne(document);
		}
	}

	public synchronized String retrieveToken(String pseudonym, String token) {
		MongoCollection<Document> collection = database.getCollection("token");

		// token zurückgeben
		Document document = collection.find(and(eq("pseudonym", pseudonym), eq("token", token))).first();
		if (document == null) {
			return null;
		}
		return document.getString("expire-date");
	}

	public synchronized void deleteToken(String token) {
		MongoCollection<Document> collection = database.getCollection("token");
		collection.deleteOne(eq("token", token)) ;
	}

}