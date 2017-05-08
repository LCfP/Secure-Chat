package secureChat;

public class Message {
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