package client;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import secureChat.Message;
import secureChat.User;

public class ChatClient {
	
	private static ChatClientThread clientThread = null;
	
public static void main(String[] args) throws IOException {
        
        if (args.length != 3) {
            System.err.println(
                "Usage: java EchoClient <host name> <port number> <screen name>");
            System.exit(1);
        }

        String hostName = args[0];
        int portNumber = Integer.parseInt(args[1]);
        String screenName = args[2];

        try {
        	Socket socket = new Socket(hostName, portNumber);
        	clientThread = new ChatClientThread(socket);
        	clientThread.start();
        	
        	User user = new User ();
        	user.setScreenname(screenName);
        	clientThread.sendUser(user);
            
        } catch (UnknownHostException e) {
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        }
    }

public static void closeThread(ChatClientThread chatClientThread) {
	if (clientThread == chatClientThread) {
		clientThread = null;
	}
	
}

}
