package secureChat;

import java.util.ArrayList;

public class ChatBox
{
	private ArrayList<User> listOfUsers;

	public ChatBox()
	{
		listOfUsers = new ArrayList<User>(0);
	}


	public void login(User user)
	{
		listOfUsers.add(user);
	}

	public void logout(User user)
	{
		listOfUsers.remove(user);
	}

	public ArrayList<User> getUsers()
	{
		return listOfUsers;
	}

		/*public ListOfUsers(){

		}

		private WritingBox(){

		}

		private ConversationBox(){

		}*/
}
