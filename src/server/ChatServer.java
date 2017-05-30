package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.Set;

import secureChat.User;

public class ChatServer {
	private static Set<ChatServerThread> openConnections = new HashSet<ChatServerThread>();

	public static void main(String[] args) throws IOException {

		if (args.length != 1) {
			System.err.println("Usage: java ChatServer <port number>");
			System.exit(1);
		}

		int portNumber = Integer.parseInt(args[0]);
		boolean listening = true;
		
		try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
			while (listening) {
				ChatServerThread thread = new ChatServerThread(serverSocket.accept());
				thread.start();
				openConnections.add(thread);
			}
		} catch (IOException e) {
			System.err.println("Could not listen on port " + portNumber);
			System.exit(-1);
		}
	}

	public static ChatServerThread getConnectionForUser(String screenname){
		for ( ChatServerThread thread : openConnections ) {
			if ( thread.threadForUsername().equals(screenname) ) {
				return thread;
			}
		}
		return null;
	}

	public static void closeThread(ChatServerThread thread){
		openConnections.remove(thread);
	}

	public static User[] getUsers() {
		User[] users = new User[openConnections.size()];
		int userNo = 0;
		for ( ChatServerThread thread : openConnections ){
			users[userNo++] = thread.threadForUser();
		}
		return users;
	}

}
