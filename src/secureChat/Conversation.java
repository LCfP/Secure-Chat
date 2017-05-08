package secureChat;

import java.util.*;

public class Conversation
{
	private ArrayList<User> Userlist ; //a conversation should contains a list of (two) users
	private ArrayList<Message> conversation; //a list of all messages in this conversation

	public Conversation()
	{
		Userlist = new ArrayList<User>(0);
		conversation = new ArrayList<Message> (0);
	}

	public void addUser(User user)
	{
		Userlist.add(user);
	}

	public ArrayList<User> getUsers()
	{
		return Userlist;
	}

	public ArrayList<Message> getMessages()
	{
		return conversation;
	}

	public void addMessage(Message message)
	{
		if (Userlist.contains(message.getRecipient() )  && Userlist.contains(message.getSender() ) )
		{ // if the sender and the receivers together are the same as the userlist, the message can be added to the conversation
			conversation.add(message);
		}
	}
}