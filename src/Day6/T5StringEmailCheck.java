package Day6;

import java.util.Scanner;

public class T5StringEmailCheck {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter your email: ");
		String email = sc.next();
		if(isValidEmail(email)) {
			System.out.println("Valid Mail...");
		}
		else{
			System.out.println("Inavalid.");
		}
		sc.close();
	}
	public static boolean isValidEmail(String email) {
		int at = email.indexOf('@');
		int dot = email.lastIndexOf('.');
		return at > 0 && dot > at && dot < email.length() - 1 && email.indexOf('@', at + 1) == -1; 
	}

}
