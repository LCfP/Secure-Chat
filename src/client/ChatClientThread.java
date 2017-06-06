package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import secureChat.SecureChat;
import secureChat.User;

public class ChatClientThread extends Thread {

	private Socket socket = null;
	//private secureChat.User user = null;

	private ObjectOutputStream out = null;
	private ObjectInputStream in = null;

	public ChatClientThread(Socket socket){
		super("ChatClientThread");
		this.socket = socket;
	}

	public void run() {

        try {
            out = new ObjectOutputStream(socket.getOutputStream());
        	in = new ObjectInputStream(socket.getInputStream());

            boolean keepGoing = true;
            while (keepGoing) {
            	Object object = in.readObject();
            	if ( object instanceof secureChat.Message ) {
            		System.out.println("Received Message:");
            		SecureChat.printMessage((secureChat.Message)object);
            	} else if ( object instanceof User[] ) {
            		System.out.println("Received Userlist");
            		SecureChat.loginOtherUsers((User[]) object);
            	} else if ( object instanceof Integer ){
            		if( (int)object == 1 )
            		{
            			System.out.println("Screenname already taken");
                		SecureChat.screennameTaken();
            		}
            	} else {
            		System.out.println( "from server: " + object);
            	}
            }
            SecureChat.closeThread(this);
            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            SecureChat.closeThread(this);
        }
    }

	public void sendMessage(secureChat.Message message) {
		try {
			out.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void sendUser(User user) {
		//this.user = user;
		try {
			out.writeObject(user);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void requestUsers(){
		try{
			out.writeObject(null);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void logout(){
		try{
			out.writeObject(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
