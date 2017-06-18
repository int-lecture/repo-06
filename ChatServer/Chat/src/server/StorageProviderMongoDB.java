package server;

import static com.mongodb.client.model.Filters.eq;
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
	private static MongoDatabase database = mongoClient.getDatabase("userbase");

	/**
	 * @see var.chat.server.persistence.StorageProvider#retrieveMessages(java.lang.String,
	 *      long, boolean)
	 */
	public synchronized UserID retrieveUser(String benutzername) {

		MongoCollection<Document> collectionAccount = database.getCollection("account");
		// Information über User
		Document document = collectionAccount.find(eq("user", benutzername)).first();
		// keine Daten
		if (document == null) {
			return null;
		}
		UserID user = new UserID(benutzername);
		return user;
	}

	public synchronized void storeMessages(Message message) {

		MongoCollection<Document> collectionMessages = database.getCollection("messages");

		Document document = new Document("from", message.from).append("to", message.to).append("date", message.date)
				 .append("sequence", message.sequenceNr).append("text", message.text);

		collectionMessages.insertOne(document);
	}


	   /**
     * @see var.chat.server.persistence.StorageProvider#clearForTest()
     */
    public void clearForTest() {
        database.getCollection("messages").drop();
        database.getCollection("sequences").drop();
    }


}