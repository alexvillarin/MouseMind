package main;

public class Main {

	// Class used to test results
	public static void main(String[] args) {
		System.out.println("Cheese\t\tWalk\t\tRest\t\tNotBreak\tResult");
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				for (int k = 0; k < 4; k++)
					for (int l = 0; l < 4; l++)
						System.out.println(i + "\t\t" + j + "\t\t" + k + "\t\t" + l + "\t\t");
	}

}
