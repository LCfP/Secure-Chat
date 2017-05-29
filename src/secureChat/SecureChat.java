package secureChat;
import client.ChatClient;
import client.ChatClientThread;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SecureChat extends Application{
	
	public static BorderPane root;
	
	public static TextField hostField;
	public static Label hostLabel;
	public static TextField portField;
	public static Label portLabel;
	public static TextField loginField;
	public static Label errorLabel;
	public static Label nicknameLabel;
	public static Button loginButton;
	
	public static ChatBox chatbox;
	public static ArrayList<Label> userLabels;
	public static StackPane userPane;
	
	public static GridPane messagePane;
	public static TextField messageField;
	public static Button messageButton;
	public static ScrollPane messageHistory;
	public static ScrollPane users;
	public static GridPane conversationPane;
	public static Label senderInput;
	public static Label messageInput;
	public static Label timeInput;
		
	private static ChatClientThread clientThread = null;

	public static void main(String[] args)
	{
		chatbox = new ChatBox();
		
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception 
	{
		root = new BorderPane();
		Scene primaryScene = new Scene(root,800,600);
		primaryStage.setScene(primaryScene);
		
		users = new ScrollPane();
		users.setPrefWidth(150);
		users.setHmax(150);
		root.setRight(users);
		
		messageHistory = new ScrollPane();
		messageHistory.setPrefWidth(650);
		messageHistory.setHmax(650);
		root.setCenter(messageHistory);
		
		messagePane = new GridPane();
		messageField = new TextField();
		messageField.setPrefWidth(600);
		messageField.setPrefHeight(100);
		messageButton = new Button("Send");
		
		GridPane.setConstraints(messageField, 0, 0);
		GridPane.setConstraints(messageButton, 1, 0);
		
		messagePane.setAlignment(Pos.CENTER);
		messagePane.getChildren().addAll(messageField,messageButton);
		root.setBottom(messagePane);
		
		conversationPane = new GridPane();
		conversationPane.setPrefWidth(600);
		conversationPane.setPrefHeight(450);
		messageHistory.setContent(conversationPane);
		
		// TODO replace placeholders with actual sender, message, and time
		senderInput = new Label("Peterkfdjsa;lkdfjsa;kdfjsa;kdfjsa;kdfjs;kjdfs");
		messageInput = new Label("Hi");
		timeInput = new Label("20.15");
		
		GridPane.setConstraints(senderInput, 0, 0);
		GridPane.setConstraints(messageInput, 1, 0);
		GridPane.setConstraints(timeInput, 2, 0);
		
		conversationPane.getColumnConstraints().add(new ColumnConstraints(100));
		conversationPane.getColumnConstraints().add(new ColumnConstraints(450));
		conversationPane.getColumnConstraints().add(new ColumnConstraints(50));
		
		conversationPane.getChildren().addAll(senderInput, messageInput, timeInput);
		
		Stage loginStage = new Stage();
		GridPane loginPane = new GridPane();
		Scene loginScene = new Scene(loginPane,400,300);
		
		hostField = new TextField();
		hostLabel = new Label("Host name: ");
		portField = new TextField();
		portLabel = new Label("Port number: ");
		loginField = new TextField();
		nicknameLabel = new Label("Nickname: ");
		errorLabel = new Label("");
		errorLabel.setTextFill(Color.RED);
		loginButton = new Button("Login");
		
		loginButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0) 
			{
				if(loginField.getText().length() >= 3)
				{
					User thisUser = new User();
					thisUser.setScreenname(loginField.getText());
					
					chatbox.login(thisUser);
					
					userPane = new StackPane();
					userLabels = new ArrayList<Label>(0);
					
					for(User idx:chatbox.getUsers() )
					{
						userLabels.add(new Label(idx.getScreenName()));
					}
					
					userPane.getChildren().addAll(userLabels);
					users.setContent(userPane);
					
					if(!hostField.getText().equals("") || !portField.getText().equals(""))
					{
						String[] arguments = new String[3];
						
						arguments[0] = hostField.getText();
						arguments[1] = portField.getText();
						arguments[2] = loginField.getText();
						
						try {
				        	Socket socket = new Socket(hostField.getText(),portField.getText());
				        	clientThread = new ChatClientThread(socket);
				        	clientThread.start();
				        	
				        	User user = new User ();
				        	user.setScreenname(loginField.getText());
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
					
					loginStage.close();
					
					primaryStage.show();
				}
				else
				{
					errorLabel.setText("Nickname should contain at least 3 characters");
				}
			}
		} );
		
		GridPane.setConstraints(hostField, 1, 0);
		GridPane.setConstraints(hostLabel, 0, 0);
		GridPane.setConstraints(portField, 1, 1);
		GridPane.setConstraints(portLabel, 0, 1);
		GridPane.setConstraints(loginField, 1, 2);
		GridPane.setConstraints(nicknameLabel, 0, 2);
		GridPane.setConstraints(errorLabel, 1, 3);
		GridPane.setConstraints(loginButton, 1, 4);
		
		loginPane.getRowConstraints().add(new RowConstraints(50));
		loginPane.getRowConstraints().add(new RowConstraints(50));
		loginPane.getRowConstraints().add(new RowConstraints(50));
		loginPane.getRowConstraints().add(new RowConstraints(25));
		loginPane.getRowConstraints().add(new RowConstraints(50));
		loginPane.getColumnConstraints().add(new ColumnConstraints(75));
		loginPane.setAlignment(Pos.CENTER);
		
		loginPane.getChildren().addAll(hostField,hostLabel,portField,portLabel,loginField,nicknameLabel,errorLabel,loginButton);
		loginStage.setScene(loginScene);
		loginStage.show();		
	}
}
