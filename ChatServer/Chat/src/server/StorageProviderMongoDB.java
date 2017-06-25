package server;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.gte;
import static com.mongodb.client.model.Filters.lte;
import static com.mongodb.client.model.Filters.and;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

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
	public synchronized UserID retrieveUser(String benutzername) {

		MongoCollection<Document> collectionAccount = database.getCollection("profiles");
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

	public synchronized List<Message> retrieveMessages(String userID, int sequence, boolean delete)
			throws ParseException {
		MongoCollection<Document> collectionMessages = database.getCollection("messages");

		List<Document> list = (List<Document>) collectionMessages.find(and(eq("to", userID), gte("sequence", sequence)))
				.into(new ArrayList<Document>());

		if (list == null) {
			return null;
		}
		if (delete == true) {
			collectionMessages.deleteMany((and(eq("to", userID))));
		}

		List<Message> messageList = new ArrayList<Message>();

		for (Document d : list) {
			messageList.add(new Message(d.getString("token"), d.getString("from"), d.getString("to"),
					d.getDate("date"), d.getString("text"),
					d.getInteger("sequenceNr")));
		}
		Collections.sort(messageList, new Comparator<Message>() {
			@Override
			public int compare(Message o1, Message o2) {
				return o1.sequenceNr - o2.sequenceNr;
			}
		});
		return messageList;
	}

	public synchronized int getUpdatedSequence(String userId){
		MongoCollection<Document> collectionSequence = database.getCollection("sequence");
		Document document=collectionSequence.find(eq("pseudonym",userId)).first();
		if(document!=null){
			document.replace("sequence", document.getInteger("sequence")+1);
			collectionSequence.deleteOne(eq("pseudonym",userId));
			collectionSequence.insertOne(document);
			return document.getInteger("sequence");
		}else{
			Document documentNew=new Document("sequence",0).append("pseudonym", userId);

			collectionSequence.insertOne(documentNew);
			return 0;
		}
	}


	   /**
     * @see var.chat.server.persistence.StorageProvider#clearForTest()
     */
    public void clearForTest() {
        database.getCollection("messages").drop();
        database.getCollection("sequences").drop();
    }


}