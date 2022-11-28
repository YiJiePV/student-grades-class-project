/*Karena Qian 
 *CS 210 
 *5/30/2020 
 *This program takes in test grades of individual students from a text file,
 *computes their final grade,
 *and prints it out in a separate text file. */
//for Scanner, FileNotFoundException, and PrintStream
import java.util.*;
import java.io.*;
public class StudentsGrades {

	public static void main(String args[])throws FileNotFoundException{
		//setting up variables
		int id;
		double grade = 0;
		Scanner input = new Scanner(new File("students_test.txt"));
		PrintStream output = new PrintStream(new File("grades.txt"));
		//analyzing each line...
		while(input.hasNextLine()) {
			
		/* Alternate solution (figured this out with my computer scientist dad)
		 * replaces every ',' with a space in line
			String studentData = input.nextLine();
			Scanner text = new Scanner(studentData.replace(',', ' '));
		 *  then find each student's id and calculates final grade		
			id = text.nextInt();
			grade = 0;
			grade += text.nextDouble() * 0.1;
			grade += text.nextDouble() * 0.1;
			grade += text.nextDouble() * 0.1;
			grade += text.nextDouble() * 0.2;
			grade += text.nextDouble() * 0.5;
			*/
			
			//finds each student's id and calculates final grade	  
			Scanner text = new Scanner(input.nextLine());
			id = getId(text);
			grade = finalGrades(text);
			//files the information in .txt file
			fileGrades(id, grade, output);
		}
		
	}
	//student id
	public static int getId(Scanner input) {
		//reads and returns student id (int) without ','
		String idString = input.next();
		int id = 0;
		for (int i = 0; i < idString.length() - 1; i++) {
			id = id * 10 + Character.getNumericValue(idString.charAt(i));
		}
		return id;
	}
	//final grade
	public static double finalGrades(Scanner input) {
		//reads and stores grades (double) for each test without ','
		double[] grades = new double[5];
		//manually analyzes each token...
		while(input.hasNext()) {
			for(int i = 0; i < grades.length; i++) {
				String scores = input.next();
				int factor = 10;
				int decimalPoint = 0;
				for (int j = 0; j < scores.length(); j++) {
					//keep grade's decimal pos. the same (88.5 still is 88.5 not 8850.0)
					if(scores.charAt(j) == '.') {
						factor = 1;
						decimalPoint = j;
					} 
					else if (scores.charAt(j) != ',') {
						double numValue = 0;
						if (decimalPoint != 0) {
							numValue = Character.getNumericValue(scores.charAt(j))/Math.pow(10, j - decimalPoint);
						} else {
							numValue = Character.getNumericValue(scores.charAt(j)); 
						}
						grades[i] =  grades[i] * factor + numValue;
					}
				}
			}
		}
		//calculates final grade
		double quizGrade = (grades[0]+grades[1]+grades[2])*0.1;
		double finalScore = quizGrade + (grades[3]*0.2) + (grades[4]*0.5);
		return finalScore;
	}
	//files ids and grades w/ proper format
	public static void fileGrades(int id, double finalGrade, PrintStream output) {
		output.printf("%d, %.1f \n", id, finalGrade);
	}

}
