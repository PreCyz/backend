package backend.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonRootName;

import backend.jpa.entities.BoxMessage;

@JsonRootName(value="messages")
public class BoxMessages implements Serializable {

	private static final long serialVersionUID = 5721506925848133689L;
	
	private List<BoxMessage> messages;
	private List<BoxMessage> groupedMessages;
	private int totalUnreadCount;
	
	public BoxMessages() {}
	
	public BoxMessages(List<BoxMessage> messages, List<BoxMessage> groupedMessages) {
		super();
		this.messages = messages;
		this.groupedMessages = groupedMessages;
	}
	
	public List<BoxMessage> getMessages() {
		return messages;
	}
	public List<BoxMessage> getGroupedMessages() {
		return groupedMessages;
	}
	public int getTotalUnreadCount() {
		return totalUnreadCount;
	}
	
	public void refreshTotalCounts() {
		
		int count = 0;
		if (groupedMessages != null) {
			for(BoxMessage message : groupedMessages) {
				count += message.getGroupUnreadCount();
			}
		}
		totalUnreadCount = count;
	}
	
}
