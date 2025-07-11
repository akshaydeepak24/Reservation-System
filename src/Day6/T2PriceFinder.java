package Day6;

import java.util.Scanner;

public class T2PriceFinder {

	public static void main(String[] args) {
		double[] p = {189.45,200.50,150.99,165.23,752.63};
		System.out.println("Enter the index you want to find: ");
		Scanner sc = new Scanner(System.in);
		int index = sc.nextInt();
		if(index >= 0 && index<p.length) {
			System.out.println(p[index]);
		}
		else {
			System.out.println("Hey!.. Idiot Check it");
			System.out.println("It's Out Of Bounds");
		}
		sc.close();
	}

}
