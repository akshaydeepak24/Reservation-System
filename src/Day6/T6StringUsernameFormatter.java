package Day6;

import java.util.Scanner;

public class T6StringUsernameFormatter {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the Username: ");
		String user = sc.next();
		String formatted = formatUsername(user);
		System.out.println("Formatted Username: "+ formatted);
		sc.close();
	}
	public static String formatUsername(String username) { 
        return username.trim().toLowerCase().replaceAll("\\s+", "_");
	}
}