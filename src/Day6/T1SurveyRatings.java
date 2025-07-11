package Day6;

import java.util.Scanner;

public class T1SurveyRatings {

	public static void main(String[] args) {
		int[] a = new int[5];
		Scanner sc = new Scanner(System.in); 

		System.out.println("Enter your Ratings: ");
		for (int i = 0; i < a.length; i++) {
			a[i] = sc.nextInt(); 
		}

		int sum = 0;
		for (int j = 0; j < a.length; j++) {
			sum += a[j];
		}

		float avg = (float) sum / a.length; 
		System.out.println("Average rating: " + avg);

		sc.close(); 
	}
}
