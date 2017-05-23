package secureChat;

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

public class SecureChat extends Application {

	public static BorderPane root;
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
	private static User loggedInUser;
	public static GridPane conversationPane;
	public static Label senderInput;
	public static Label messageInput;
	public static Label timeInput;

	public static void main(String[] args) {
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
		Scene loginScene = new Scene(loginPane,400,150);
		
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
					
					chatbox = new ChatBox();
					chatbox.login(thisUser);
					
					userPane = new StackPane();
					userLabels = new ArrayList<Label>(0);
					
					for(User idx:chatbox.getUsers() )
					{
						userLabels.add(new Label(idx.getScreenName()));
					}
					
					userPane.getChildren().addAll(userLabels);
					users.setContent(userPane);
					loggedInUser = thisUser;
					
					loginStage.close();
					
					primaryStage.show();
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
				Message thisMessage = new Message(loggedInUser,loggedInUser,messageField.getText());
			}
		});
	
				
		
		GridPane.setConstraints(loginField, 1, 0);
		GridPane.setConstraints(nicknameLabel, 0, 0);
		GridPane.setConstraints(errorLabel, 1, 1);
		GridPane.setConstraints(loginButton, 1, 2);
		
		loginPane.getRowConstraints().add(new RowConstraints(50));
		loginPane.getRowConstraints().add(new RowConstraints(25));
		loginPane.getRowConstraints().add(new RowConstraints(50));
		loginPane.getColumnConstraints().add(new ColumnConstraints(75));
		loginPane.setAlignment(Pos.CENTER);
		
		loginPane.getChildren().addAll(loginField,nicknameLabel,errorLabel,loginButton);
		loginStage.setScene(loginScene);
		loginStage.show();		
	}
}
