package secureChat;

import java.io.Serializable;

public class Message implements Serializable{

	private static final long serialVersionUID = -3348546542463134980L;
	private User sender;
	private User recipient;
	private String message;

	public Message(User Sender,User Recipient, String text)
	{
		sender = Sender;
		recipient = Recipient;
		message = text;
	}

	public User getSender()
	{
		return sender;
	}

	public User getRecipient()
	{
		return recipient;
	}

	public String getMessage()
	{
		return message;
	}
}