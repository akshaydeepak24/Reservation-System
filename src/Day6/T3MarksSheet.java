package Day6;
import java.util.Scanner;
public class T3MarksSheet {

	public static void main(String[] args) {
		int[][] m = new int[4][5];
		Scanner sc = new Scanner(System.in);
		for (int i = 0; i<m.length; i++) {
			System.out.println("Enter Stdnt marks: "+ (i+1) + ": ");
			for(int k = 0; k< m[i].length; k++) {
				System.out.println("Enter Sub marks " + (k+1) + ": ");
				m[i][k] = sc.nextInt();
			}
			System.out.println();
		}
		for(int i = 0; i<m.length; i++) {
			System.out.println("Student " + (i+1) + ": ");
			for(int k = 0; k<m[i].length; k++) {
				System.out.print(m[i][k] + " ");
			}
			System.out.println();
		}
		sc.close();
		}

}
