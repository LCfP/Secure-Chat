package secureChat;

import java.io.Serializable;
import java.util.Scanner;

public class User implements Serializable {

	private static final long serialVersionUID = 498394893388392L;
	private String username;
	private String password;
	private String securityQuestion;
	private String securityAnswer;
	private String screenName;

	public User(){
		// all values to be filled in by the user
		this.username = null;
		this.password = null;
		this.securityQuestion = null;
		this.securityAnswer = null;
		this.screenName = null;

	}
	
	public void setScreenname(String name)
	{
		this.screenName = name;
	}
	
	public void CreateUsername(){
		// TODO must be unique, max length; solve scanner resource leak
		String name = null;
		System.out.println("Enter a username:");
		Scanner scanner = new Scanner(System.in);
		name = scanner.nextLine();
		this.username = name;



	}
	public void CreatePassword(){
		// TODO add password requirements eg length
		Scanner scanner = new Scanner(System.in);
		String pw1 = null;
		String pw2 = null;
		do {
		System.out.println("Enter a password without spaces:");
		pw1 = scanner.next();
		System.out.println("Re-enter your password:");
		pw2 = scanner.next();
		}while (!pw1.equals(pw2));
		this.password = pw1;

	}
	public void CreateSecurityQuestion(){
		// user enters security question
		System.out.println("Enter a security question:");
		Scanner scanner = new Scanner(System.in);
		String question = scanner.nextLine();

		this.securityQuestion = question;

	}
	public void CreateSecurityAnswer(){
		System.out.println("Enter the answer to your security question:");
		Scanner scanner = new Scanner(System.in);
		String answer = scanner.nextLine();
		this.securityAnswer = answer;

	}
	public void CreateScreenName(){
		System.out.println("Enter a screen name:");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.nextLine();
		this.screenName = name;

	}
	public void ChangePassword(){
		// allows user to change their password if security question answered and same pw entered twice
		System.out.println(securityQuestion);
		Scanner scanner = new Scanner(System.in);
		String answer = scanner.next();
		if (answer.equals(securityAnswer)){
			String pw1 = null;
			String pw2 = null;
			do {
			System.out.println("Enter a new password:");
			pw1 = scanner.next();
			System.out.println("Re-enter your new password:");
			pw2 = scanner.next();
			}while (!pw1.equals(pw2));
			this.password = pw1;
		}else{
			System.out.println("Wrong Answer, try again.");
		}
		}


	public void ChangeScreenName(){
		// allows user to change their screen name
		System.out.println("Enter a new screen name:");
		Scanner scanner = new Scanner(System.in);
		String name = scanner.next();
		this.screenName = name;

	}
	public String getUsername(){
		return username;
	}
	public String getPassword(){
		return password;
	}
	public String getSecurityQuestion(){
		return securityQuestion;
	}
	public String getSecurityAnswer(){
		return securityAnswer;
	}
	public String getScreenName(){
		return screenName;
	}

	public String toString(){
		return screenName;
	}
}
