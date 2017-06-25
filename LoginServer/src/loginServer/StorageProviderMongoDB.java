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

	private static final String MONGO_URL = "mongodb://141.19.142.60:27017";
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
	public synchronized User retrieveUser(String username) {

		MongoCollection<Document> collectionAccount = database.getCollection("profiles");
		// Information über User
		Document document = collectionAccount.find(eq("user", username)).first();
		// keine Daten
		if (document == null) {
			return null;
		}
		User user = new User(username, document.getString("storedPassword"), document.getString("pseudonym"), true);
		return user;
	}

	/**
	 *
	 */
	public synchronized void saveToken(String token, String ablauf, String pseudonym) {

		MongoCollection<Document> collectionToken = database.getCollection("token");

		// user zur database hinzufügen
		Document document = new Document("token", "" + token + "").append("ablauf", ablauf)
				.append("pseudonym", pseudonym);

		if (collectionToken.find(eq("pseudonym", pseudonym)).first() != null) {
			collectionToken.updateOne(eq("pseudonym", pseudonym), new Document("$set", document));
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
		return document.getString("ablauf");
	}

	public synchronized void deleteToken(String token) {
		MongoCollection<Document> collection = database.getCollection("token");
		collection.deleteOne(eq("token", token)) ;
	}

	/**
     * @see var.chat.server.persistence.StorageProvider#clearForTest()
     */
	public void clearForTest() {

	   }

}