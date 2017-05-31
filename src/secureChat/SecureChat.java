package secureChat;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import client.ChatClientThread;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class SecureChat extends Application{

	private static final SimpleDateFormat sdf = new SimpleDateFormat("HH.mm");

	public static BorderPane root;

	public static TextField hostField;
	public static Label hostLabel;
	public static TextField portField;
	public static Label portLabel;
	public static TextField loginField;
	public static Label errorLabel;
	public static Label nicknameLabel;
	public static Button loginButton;
	public static Button continueButton;
	public static Button logoutButton;

	public static ToggleGroup userGroup = new ToggleGroup();

	public static ArrayList<ToggleButton> userButtons;
	public static GridPane userPane;

	public static GridPane messagePane;
	public static TextField messageField;
	public static Button messageButton;
	public static ScrollPane messageHistory;
	public static ScrollPane users;

	private static ArrayList<User> loggedInUsers;

	public static GridPane conversationPane;
	public static Label senderInput;
	public static Label messageInput;
	public static Label timeInput;

	private static ChatClientThread clientThread = null;
	private static User user;

	public static void main(String[] args)
	{

		launch(args);
		Date date = new Date();

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

		logoutButton = new Button("Logout");
		logoutButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0)
			{
				clientThread = null;

				primaryStage.close();
			}
		});


		GridPane.setConstraints(messageField, 0, 1);
		GridPane.setConstraints(messageButton, 1, 1);
		GridPane.setConstraints(logoutButton, 1, 0);

		messagePane.setAlignment(Pos.CENTER);
		messagePane.getChildren().addAll(messageField,messageButton,logoutButton);
		root.setBottom(messagePane);

		conversationPane = new GridPane();
		conversationPane.setPrefWidth(600);
		conversationPane.setPrefHeight(450);
		messageHistory.setContent(conversationPane);

		// TODO replace placeholders with actual sender, message, and time
		senderInput = new Label("Peterkfdjsa;lkdfjsa;kdfjsa;kdfjsa;kdfjs;kjdfs");
		messageInput = new Label("Hi");
		timeInput = new Label(sdf.format(new Timestamp(System.currentTimeMillis())));

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
		loginButton.setDefaultButton(true);

		Stage tempStage = new Stage();
		StackPane tempPane = new StackPane();
		Scene tempScene = new Scene(tempPane,200,100);
		tempStage.setScene(tempScene);

		continueButton = new Button("Continue");
		tempPane.getChildren().add(continueButton);

		continueButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0)
			{
				clientThread.sendUser(user);

				tempStage.close();

				primaryStage.show();
				messageButton.setDefaultButton(true);
			}
		});

		loginButton.setOnAction(new EventHandler<ActionEvent>()
		{
			public void handle(ActionEvent arg0)
			{
				if(loginField.getText().length() >= 3)
				{
					loggedInUsers = new ArrayList<User>(0);
					loggedInUsers.add( new User() );
					loggedInUsers.get(0).setScreenname(loginField.getText());

					userPane = new GridPane();
					userButtons = new ArrayList<ToggleButton>(0);

					userButtons.add(new ToggleButton(loggedInUsers.get(0).getScreenName()));
					userButtons.get(0).setSelected(true);
					userButtons.get(0).setToggleGroup(userGroup);

					userPane.getChildren().add(userButtons.get(0) );
					users.setContent(userPane);


					if(!hostField.getText().equals("") || !portField.getText().equals(""))
					{

						try {
				        	Socket socket = new Socket(hostField.getText(),Integer.parseInt(portField.getText() ) );
				        	clientThread = new ChatClientThread(socket);
				        	clientThread.start();

				        	user = new User ();
				        	user.setScreenname(loginField.getText());

				        } catch (UnknownHostException e) {
				            System.err.println("Don't know about host " + hostField.getText() );
				            System.exit(1);
				        } catch (IOException e) {
				            System.err.println("Couldn't get I/O for the connection to " +
				            		hostField.getText() );
				            System.exit(1);
				        } catch (NumberFormatException e){
				        	System.err.println("Port number should be an integer");
				        	System.exit(1);
				        }
					}

					loginStage.close();

					tempStage.show();
					continueButton.setDefaultButton(true);
				}
				else
				{
					errorLabel.setText("Nickname should contain at least 3 characters");
				}
			}
		} );


		messageButton.setOnAction((event)-> //
		{
			if(messageField.getText().length() >= 1)
			{
				User recipient = null;

				for(int idx=0;idx<userButtons.size();++idx)
				{
					if(userButtons.get(idx).isSelected() )
					{
						recipient = loggedInUsers.get(idx);
						break;
					}
				}

				Message thisMessage = new Message(loggedInUsers.get(0),recipient,messageField.getText());
				messageField.setText("");

				clientThread.sendMessage(thisMessage);

			}
		});

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

	public static void loginOtherUsers(User[] object)
	{
		userPane.getChildren().clear();
		for(User idx:object)
		{
			if(!loggedInUsers.contains(idx))
			{
				loggedInUsers.add(idx);
				ToggleButton button = new ToggleButton(idx.getScreenName());
				button.setToggleGroup(userGroup);
				userButtons.add(button);
			}
		}

		for(User idx1:loggedInUsers)
		{
			boolean present = false;

			for(User idx2:object)
			{
				if(idx2.equals(idx1))
				{
					present = true;
					break;
				}
			}

			if(!present)
			{
				userButtons.remove(loggedInUsers.indexOf(idx1));
				loggedInUsers.remove(idx1);
			}
		}

		for(int idx=0;idx<userButtons.size();idx++)
			GridPane.setConstraints(userButtons.get(idx), 0, idx);

		userPane.getChildren().addAll(userButtons);
	}
}
