package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import secureChat.User;


public class ChatServerThread extends Thread {
	
	private Socket socket = null;
	private secureChat.User user = null;
	
	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;
	
	public ChatServerThread(Socket socket){
		super("ChatServerThread");
		this.socket = socket;
	}
	
	public User threadForUser(){
		return user;
	}
	
	public String threadForUsername(){
		if ( user != null ) {
			return user.getScreenName();
		}
		return "";
	}
	
	public void run() {

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        	in = new ObjectInputStream(socket.getInputStream());
        		
            do {
            	out.writeObject(new String("Please send a User object"));
            	Object object = in.readObject();
            	if ( object instanceof secureChat.User && ChatServer.getConnectionForUser(((secureChat.User)object).getScreenName()) == null ) {
            		System.out.println("Found user object for which no connection exists");
            		this.user = (secureChat.User)object;
            	}
            } while ( user == null );
            
            out.writeObject(new String("Logged in, please send a Message object or request logged in Users"));
            boolean keepGoing = true;
            while (keepGoing) {
            	
            	Object object = in.readObject();
            	if ( object instanceof secureChat.Message ) {
            		System.out.println("Received Message object attempting to forward.");
            		secureChat.Message message = (secureChat.Message)object;
            		
            		ChatServerThread connection = ChatServer.getConnectionForUser(message.getRecipient().getScreenName());
            		
            		if ( connection != null ){
            			connection.forwardMessage(message);
            		}
            	} else {
            		User[] users = ChatServer.getUsers();
            		out.writeObject(users);
            	}
            }
            ChatServer.closeThread(this);
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
        	e.printStackTrace();
            System.out.println("Closing thread for user " + user.getScreenName());
        	
            ChatServer.closeThread(this);
        }
    }
	
	public void forwardMessage(secureChat.Message message) throws IOException{
		out.writeObject(message);
	}

}
