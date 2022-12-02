package Project2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LCS {
	
	static int diagonal = 5;
	static int up = 10;
	static int left = 15;
	
	static List<String> turned_on = new ArrayList<String>();
	
	
	//recursive approach for tracking the comeFrom array and constructing the lcs path for finding the lcs variables, 
	//write them to the turned_on array.
	public static void FindPath(int element, int m, int n, String [] outlets, int[][] comeFrom) {
		if(element == -1) {
			
		}
		else {
			if(element == diagonal) {
				turned_on.add(outlets[m-1]);
				element = comeFrom[m-1][n-1];
				FindPath(element, m-1, n-1, outlets, comeFrom);
			}
			else if(element == up) {
				element = comeFrom[m-1][n];
				FindPath(element, m-1, n, outlets, comeFrom);
			}
			else {
				element = comeFrom[m][n-1];
				FindPath(element, m, n-1, outlets, comeFrom);
			}
		}
	}
	
	
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of outlets: (max 50)");
		int outlets_length = sc.nextInt();
		while(outlets_length > 50 || outlets_length < 1) {
			System.out.println("You enter an invalid number. Please try again.");
			outlets_length = sc.nextInt();
		}
		int lamps_length = outlets_length;
		System.out.println("Please enter the 2-digit hexadecimal codes of outlets with one space in between: ");
		sc.nextLine();
		String str_of_outlets = sc.nextLine();
		System.out.println("Please enter the 2-digit hexadecimal codes of lamps with one space in between: ");
		String str_of_lamps = sc.nextLine();
		
		//initialize the outlet and lamp arrays
		String outlets [] = new String [outlets_length];
		String lamps [] = new String [lamps_length];
		int j = 0;
		for(int i = 0; i < outlets_length; i++) {
			outlets[i] =  str_of_outlets.substring(j, j+2);
			lamps[i] = str_of_lamps.substring(j, j+2);
			j= j+3;
		}
		
		//for finding the path, the previous match
		int comeFrom [][] = new int[outlets_length + 1][lamps_length + 1];
		
		//the lcs matrix that has the lcs of every subsequence
		int lengths [][] = new int [outlets_length + 1][lamps_length + 1];
		
		//first row and column initializations
		for(int i = 0; i < outlets_length + 1; i++) {
			lengths[0][i] = 0;
			lengths[i][0] = 0;
			comeFrom[0][i] = -1;
			comeFrom[i][0] = -1;
		}
		
		//fill up both matrices at the same time, a comefrom element is set for every corresponding lcs element.
		for(int i = 1; i < outlets_length + 1; i++) {
			for(int x = 1; x < lamps_length + 1; x++) {
				if(outlets[i - 1].equals(lamps[x - 1])) {
					lengths[i][x] = 1 + lengths[i - 1][x - 1];
					comeFrom[i][x] = diagonal;
				}
				else {
					lengths[i][x] = Math.max(lengths[i-1][x], lengths[i][x-1]);
					if(lengths[i][x] == lengths[i-1][x]) {
						comeFrom[i][x] = up;
					}
					else if(lengths[i][x] == lengths[i][x-1]) {
						comeFrom[i][x] = left;
					}
				}
			}
		}
        
		//function that tracks the comeFrom array and find a path
		int element = comeFrom[outlets_length][lamps_length];
		FindPath(element, outlets_length, lamps_length, outlets, comeFrom);
		
		System.out.println("The maximum number of lamps that can be turned on: ");
		System.out.println(lengths[outlets_length][lamps_length]);
		System.out.println("Turned on lamps are: ");
		for(int i = turned_on.size() - 1 ; i >= 0 ; i--) {
			System.out.print(turned_on.get(i) + " ");
		}
	}

}
