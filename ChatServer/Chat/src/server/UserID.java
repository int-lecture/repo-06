package server;

import java.util.ArrayList;
import java.util.List;


public class UserID {
	/**
	 * Name des Users
	 */
	String name;
	/**
	 * Sequenznummer
	 */
	int sequenceNr = 0;

	/**
	 * <code>List</code> zum Speichern von Nachrichten
	 */
	public List<Message> messageList = new ArrayList<Message>();

	/**
	 * Konstruktor der UserID
	 * @param name Übergebener Name als String
	 */
	public UserID(String name){
		this.name = name;
	}

	/**
	 * Erhöht die Sequenznummer einer Nachricht und fügt diese einer <code>messageList</code> hinzu.
	 * @param message Übergebene Nachricht
	 * @return	Nachricht
	 */
	public Message sendMessage(Message message){
		message.sequenceNr = sequenceNr++;
		messageList.add(message);
		return message;
	}

	/**
	 * Fügt einer ArrayList recievableMessages alle Messages hinzu, bei dennen die Sequenznummer entweder gleich 0 oder
	 * grösser ist als die Sequenznummer in den Messages die in <code>messageList</code> enthalten sind.
	 *
	 * Sind alle Nachrichten in der <code>recievableMessages</code> enthalten, wird jede Nachricht aus der
	 * <code>messageList</code> entfernt solange die <code>messageList</code> nicht leer ist oder die Sequenznummer
	 * der Nachricht kleiner oder gleich der Übergeben Sequenznummer ist.
	 * @param sequenceNr	Übergebene Sequenznummer
	 * @return <code>List</code> mit Nachrichten.
	 */
	public List<Message> revieveMessage(int sequenceNr){
		List<Message> recievableMessages = new ArrayList<>();
		for(Message message: messageList){
			if(sequenceNr == 0 || message.sequenceNr >= sequenceNr){
				recievableMessages.add(message);

			}
		}
		while(!messageList.isEmpty()){
			int i=1;
			Message message = this.messageList.get(messageList.size()-i);
			if(message.sequenceNr < sequenceNr){
				messageList.remove(messageList.size()-i);
			}else{
				i++;
			}
		}
		return recievableMessages;
	}

}
