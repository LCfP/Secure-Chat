package secureChat;

import java.util.*;

public class Conversation
{
	private ArrayList<String> Userlist ; //a conversation should contains a list of (two) users
	private ArrayList<String> Conversation; //a list of all messages in this conversation

	public ArrayList<String> getUsers()
	{
		return Userlist;
	}

	public ArrayList<String> getMessages()
	{
		return Conversation;
	}

	public void addMessage(Message message)
	{
		if (receivers + sender == getUsers)
		{ // if the sender and the receivers together are the same as the userlist, the message can be added to the conversation
			Conversation.add(text);
		}
	}
}