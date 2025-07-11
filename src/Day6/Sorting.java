package Day6;
import java.util.*;
public class Sorting {

	public static void main(String[] args) {
		int[] b = {10,24,28,19,27,16,02,03};
		Arrays.sort(b);
		for(int i =0; i<b.length; i++) {
			System.out.println(b[i]);

		}
		
		int index = Arrays.binarySearch(b, 3);
		System.out.println(index);
	}

}
