package Project1;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Main {
	
	public static int searchDepth;
	public static String[][] gameTable = new String[6][7];
	public static String[][] fakeTable = new String[6][7];
	public static int pieceUser = 21;
	public static int pieceAI = 21;
	public static Boolean win = false;
	public static Node root;
	
	public static Boolean checkWin(int turn) {
		String str="";
		if(turn == 1) {
			str = "O";
		}
		else {
			str = "X";
		}
		Boolean winned = false;
		
		//horizontal win
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				if(gameTable[i][j]== str && gameTable[i][j+1] == str && gameTable[i][j+2] == str && gameTable[i][j+3] == str ) {
					winned = true;
				}
			}
		}
		//vertical win
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 7; j++) {
				if(gameTable[i][j]== str && gameTable[i+1][j] == str && gameTable[i+2][j] == str && gameTable[i+3][j] == str ) {
					winned = true;
				}
			}
		}
		//positive diagonal win
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
				if(gameTable[i][j]== str && gameTable[i+1][j+1] == str && gameTable[i+2][j+2] == str && gameTable[i+3][j+3] == str ) {
					winned = true;
				}
			}
		}
		//negative diagonal win
		for(int i = 0; i < 3; i++) {
			for(int j = 3; j < 4; j++) {
				if(gameTable[i][j]== str && gameTable[i+1][j+1] == str && gameTable[i+2][j+2] == str && gameTable[i+3][j+3] == str ) {
					winned = true;
				}
			}
		}
		return winned;
	}
	
	public static Boolean checkFull(int userMove) {
		Boolean full = false;
		if(gameTable[5][userMove] != "#") {
			full = true;
		}
		return full;
	}
	
	public static int firstOpenLoc() {
		for(int i = 0; i < 7; i++) {
			if(checkFull(i) == false) {
				return i;
			}
		}
		return -1;
	}
	
	public static void putPiece(int userMove, int turn, String [][] table) {
		String t = "";
		if(turn == -1) {
			t = "X";
		}
		else {
			t = "O";
		}
		for(int i = 0; i < 6; i++) {
			if(table[i][userMove] == "#") {
				table[i][userMove] = t;
				break;
			}
			
		}
	}
	
	public static void printBoard() {
		
		for(int i = 5; i >= 0; i--) {
			System.out.println();
			for(int j = 0; j < 7; j++) {
				System.out.print(gameTable[i][j] + " ");
			}
		}
	}
	
	public static void nestedLoop(Node node, int turn, int depth) {
		if (depth == 0) {
		}
		else {
			for(int j = 0; j < 7; j++) {
				int score = evaluateScore(j, turn, fakeTable);
				Node child = new Node(score);
				node.addChild(child);
				child.setLevel(node.getLevel()+1);
				nestedLoop(child, -turn, depth-1);
			}
		}
	}
	
	public static void generateTree(int turn) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				fakeTable[i][j] = gameTable[i][j];
			}
		}
		Node node = root;
		int depth = searchDepth;
		nestedLoop(node, turn, depth);
	}
	
	public static int DFS() {
		Stack<Node> stack = new Stack<Node>();
	    Node current = root;
	    stack.push(root);
	    
	    int count = 0;

        int array[] = new int [7];
        
	    while (!stack.isEmpty()) {
	        current = stack.peek();
	        boolean hasChild = (current.isLeaf());
	        
	        if (!hasChild) {
	            current = stack.pop();
	            array[count] = current.getScore();
	            count++;
	            if(count == 6) {
	            	int maxScore = (int)Double.NEGATIVE_INFINITY;
	            	for(int i = 0; i < array.length; i++) {
	            		maxScore = Math.max(maxScore, array[i]);
	            	}
	            current.getParent().setScore(current.getParent().getScore()+maxScore);
	            count = 0;
	            }
	        } else {
	            if (current.getChildren() != null) {
	                stack.push(current.getChildren().get(6));
	                stack.push(current.getChildren().get(5));
	                stack.push(current.getChildren().get(4));
	                stack.push(current.getChildren().get(3));
	                stack.push(current.getChildren().get(2));
	                stack.push(current.getChildren().get(1));
	                stack.push(current.getChildren().get(0));
	            }
	        }
	    } 
	    /*int ar[] = new int[7];
	    int ind[] = new int[7];
	    for(int i = 0; i< 7; i++) {
	    	ar[i] = root.getChildren().get(i).getScore();
	    	ind[i] = i;
	    }
        
        int [] returnAr = insertionSort(ar, ind, 7);*/
	    int maxScore = (int)Double.NEGATIVE_INFINITY;
	    int colNum = 8;
	    for(int i = 0; i < 7; i++) {
	    	if(root.getChildren().get(i).getScore() > maxScore) {
	    		colNum = i;
	    	}
	    	maxScore = Math.max(maxScore, root.getChildren().get(i).getScore());
	    }
	    
	    return colNum;
	    
	}
	
	public static int[] insertionSort(int values[], int indices[], int n) {
		  int i, j;
		  for (i = 1; i < n; ++i) {
		    int tmp = values[i];
		    int index = indices[i];
		    for (j = i; j >= 1 && tmp > values[j - 1]; --j){
		      values[j] = values[j - 1];
		      indices[j] = indices[j-1];
		    }
		    values[j] = tmp;
		    indices[j] = index;
		  }
		  return indices;
		}
	
	public static int evaluateScore(int userMove, int turn, String[][] Table) {
		String[][] copyTable = new String[6][7];
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				copyTable[i][j] = Table[i][j];
			}
		}
		putPiece(userMove, turn, copyTable);
		
			//horizontal score
			int maxHorizontalAI = (int) Double.NEGATIVE_INFINITY;
			int hc1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "O" && 
							(((j != 6) && (copyTable[i][j+1] == "#"))||((j!= 0) && (copyTable[i][j-1]== "#")))) {
						hc1 = 1;
					}
				}
			}
			int hc2 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 6; j++) {
					if(copyTable[i][j] == "O" && copyTable[i][j+1] == "O" && 
							(((j < 5) && (copyTable[i][j+2] == "#"))||((j!= 0) && (copyTable[i][j-1]== "#")))) {
						hc2 = 2;
					}
				}
			}
			int hc3 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 5; j++) {
					if(copyTable[i][j] == "O" && copyTable[i][j+1] == "O" && copyTable[i][j+2] == "O" && 
							(((j < 4) && (copyTable[i][j+3] == "#"))||((j!= 0) && (copyTable[i][j-1]== "#")))) {
						hc3 = 3;
					}
				}
			}
	
			maxHorizontalAI = Math.max(hc1, hc2);
			maxHorizontalAI = Math.max(maxHorizontalAI, hc3);
			
			
			int maxHorizontalUser = (int)Double.POSITIVE_INFINITY;
			int hu1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "X" && (((j != 6) && 
							(copyTable[i][j+1] == "#"))||((j!= 0) && (copyTable[i][j-1]== "#")))) {
						hu1 = 1;
					}
				}
			}
			int hu2 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 6; j++) {
					if(copyTable[i][j] == "X" && copyTable[i][j+1] == "X" && 
							(((j < 5) && (copyTable[i][j+2] == "#"))||((j!= 0) && (copyTable[i][j-1]== "#")))) {
						hu2 = 2;
					}
				}
			}
			int hu3 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 5; j++) {
					if(copyTable[i][j] == "X" && copyTable[i][j+1] == "X" && copyTable[i][j+2] == "X" && 
							(((j < 4) && (copyTable[i][j+3] == "#"))||((j!= 0) && (copyTable[i][j-1]== "#")))) {
						hu3 = 3;
					}
				}
			}
	
			maxHorizontalUser = Math.max(hu1, hu2);
			maxHorizontalUser = Math.max(maxHorizontalUser, hc3);
			maxHorizontalUser = -1 * maxHorizontalUser;
			
			int horizontalScore = maxHorizontalAI + maxHorizontalUser;
			
			//vertical score
			int maxVerticalAI = (int)Double.NEGATIVE_INFINITY;
			int t1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "O" && 
							(((i != 5) && copyTable[i+1][j] == "#") || ((i != 0) && copyTable[i-1][j] == "#"))) {
						t1 = 1;
					}
				}
			}
			int t2 = 0;
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "O" && copyTable[i+1][j] == "O" && 
							(((i < 4) && copyTable[i+2][j] == "#") || ((i != 0) && copyTable[i-1][j] == "#"))) {
						t2 = 2;
					}
				}
			}
			int t3 = 0;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "O" && copyTable[i+1][j] == "O" && copyTable[i+2][j] == "O" &&
							(((i < 3) && copyTable[i+3][j] == "#") || ((i != 0) && copyTable[i-1][j] == "#"))) {
						t3 = 3;
					}
				}
			}
			
			maxVerticalAI = Math.max(t1, t2);
			maxVerticalAI = Math.max(maxVerticalAI, t3);
			
			
			int maxVerticalUser = (int)Double.POSITIVE_INFINITY;
			int tt1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "X" && (((i != 5) && copyTable[i+1][j] == "#") || ((i != 0) && copyTable[i-1][j] == "#"))) {
						tt1 = 1;
					}
				}
			}
			int tt2 = 0;
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "X" && copyTable[i+1][j] == "X" && 
							(((i < 4) && copyTable[i+2][j] == "#") || ((i != 0) && copyTable[i-1][j] == "#"))) {
						tt2 = 2;
					}
				}
			}
			int tt3 = 0;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "X" && copyTable[i+1][j] == "X" && copyTable[i+2][j] == "X" &&
							(((i < 3) && copyTable[i+3][j] == "#") || ((i != 0) && copyTable[i-1][j] == "#"))) {
						tt3 = 3;
					}
				}
			}
			maxVerticalUser = Math.max(tt1, tt2);
			maxVerticalUser = Math.max(maxVerticalUser, tt3);
			maxVerticalUser = -1 * maxVerticalUser;
			
			int verticalScore = maxVerticalAI + maxVerticalUser;
			
			//positive diagonal score
			int maxPosDiagonalScoreP = (int)Double.NEGATIVE_INFINITY;
			int count1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "O" &&
							(((i != 0) && (j != 0) && copyTable[i-1][j-1] == "#") || ((i != 5) && (j != 6) && copyTable[i+1][j+1] == "#"))) {
						count1 = 1;
					}
				}
			}
			int count2 = 0;
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 6; j++) {
					if(copyTable[i][j] == "O" && copyTable[i+1][j+1] == "O" &&
							(((i != 0) && (j != 0) && copyTable[i-1][j-1] == "#") || ((i < 3) && (j < 3) && copyTable[i+2][j+3] == "#"))){
						count2 = 2;
					}
				}
			}
			int count3 = 0;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 5; j++) {
					if(copyTable[i][j] == "O" && copyTable[i+1][j+1] == "O" && copyTable[i+2][j+2] == "O" &&
							(((i != 0) && (j != 0) && copyTable[i-1][j-1] == "#") || ((i < 1) && (j < 2) && copyTable[i+3][j+3] == "#"))){
						count3 = 3;
					}
				}
			}
			
			maxPosDiagonalScoreP = Math.max(count1, count2);
			maxPosDiagonalScoreP = Math.max(maxPosDiagonalScoreP, count3);
			
			
			int maxNegDiagonalScoreP = (int)Double.POSITIVE_INFINITY;
			int c1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "X" &&
							(((i != 0) && (j != 0) && copyTable[i-1][j-1] == "#") || ((i < 5) && (j < 6) && copyTable[i+1][j+1] == "#"))) {
						c1 = 1;
					}
				}
			}
			int c2 = 0;
			for(int i = 0; i < 5; i++) {
				for(int j = 0; j < 6; j++) {
					if(copyTable[i][j] == "X" && copyTable[i+1][j+1] == "X" &&
							(((i != 0) && (j != 0) && copyTable[i-1][j-1] == "#") || ((i < 3) && (j < 3) && copyTable[i+2][j+3] == "#"))){
						c2 = 2;
					}
				}
			}
			int c3 = 0;
			for(int i = 0; i < 4; i++) {
				for(int j = 0; j < 5; j++) {
					if(copyTable[i][j] == "X" && copyTable[i+1][j+1] == "X" && copyTable[i+2][j+2] == "X" &&
							(((i != 0) && (j != 0) && copyTable[i-1][j-1] == "#") || ((i < 1) && (j < 2) && copyTable[i+3][j+3] == "#"))){
						c3 = 3;
					}
				}
			}
			
			maxNegDiagonalScoreP = Math.max(c1, c2);
			maxNegDiagonalScoreP = Math.max(maxNegDiagonalScoreP, c3);
			maxNegDiagonalScoreP = -1 * maxNegDiagonalScoreP;
			
			int diagonalPositive = maxPosDiagonalScoreP + maxNegDiagonalScoreP;
			
			//negative diagonal score
			int maxPosDiagonalScoreN = (int)Double.NEGATIVE_INFINITY;
			int co1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "O" &&
							(((i != 5) && (j != 0) && copyTable[i+1][j-1] == "#") || ((i > 0) && (j < 6) && copyTable[i-1][j+1] == "#"))){
						co1 = 1;
					}
				}
			}
			int co2 = 0;
			for(int i = 1; i < 6; i++) {
				for(int j = 0; j < 6; j++) {
					if(copyTable[i][j] == "O" && copyTable[i-1][j+1] == "O" &&
							(((i != 5) && (j != 0) && copyTable[i+1][j-1] == "#") || ((i > 2) && (j < 4) && copyTable[i-2][j+2] == "#"))){
						co2 = 2;
					}
				}
			}
			int co3 = 0;
			for(int i = 2; i < 6; i++) {
				for(int j = 0; j < 5; j++) {
					if(copyTable[i][j] == "O" && copyTable[i-1][j+1] == "O" && copyTable[i-2][j+2] == "O" &&
							(((i != 5) && (j != 0) && copyTable[i+1][j-1] == "#") || ((i > 4) && (j < 2) && copyTable[i-3][j+3] == "#"))){
						co3 = 3;
					}
				}
			}
			maxPosDiagonalScoreN = Math.max(co1, co2);
			maxPosDiagonalScoreN = Math.max(maxPosDiagonalScoreN, co3);
			
			
			int maxNegDiagonalScoreN = (int)Double.POSITIVE_INFINITY;
			int cn1 = 0;
			for(int i = 0; i < 6; i++) {
				for(int j = 0; j < 7; j++) {
					if(copyTable[i][j] == "X" &&
							(((i != 5) && (j != 0) && copyTable[i+1][j-1] == "#") || ((i > 0) && (j < 6) && copyTable[i-1][j+1] == "#"))){
						cn1 = 1;
					}
				}
			}
			int cn2 = 0;
			for(int i = 1; i < 6; i++) {
				for(int j = 0; j < 6; j++) {
					if(copyTable[i][j] == "X" && copyTable[i-1][j+1] == "X" &&
							(((i != 5) && (j != 0) && copyTable[i+1][j-1] == "#") || ((i > 2) && (j < 4) && copyTable[i-2][j+2] == "#"))){
						cn2 = 2;
					}
				}
			}
			int cn3 = 0;
			for(int i = 2; i < 6; i++) {
				for(int j = 0; j < 5; j++) {
					if(copyTable[i][j] == "X" && copyTable[i-1][j+1] == "X" && copyTable[i-2][j+2] == "X" &&
							(((i != 5) && (j != 0) && copyTable[i+1][j-1] == "#") || ((i > 4) && (j < 2) && copyTable[i-3][j+3] == "#"))){
						cn3 = 3;
					}
				}
			}
			
			maxNegDiagonalScoreN = Math.max(cn1, cn2);
			maxNegDiagonalScoreN = Math.max(maxPosDiagonalScoreN, cn3);
			maxNegDiagonalScoreN = -1 *  maxNegDiagonalScoreN;
			
			
			int diagonalNegative = maxPosDiagonalScoreN + maxNegDiagonalScoreN;
			
			
			int flat = Math.max(horizontalScore, verticalScore);
			int diagonal = Math.max(diagonalPositive, diagonalNegative);
			int totalScore = Math.max(flat, diagonal);
			
			return totalScore;
	}
	
	
	
	public static void GameMode() {
		Scanner sc = new Scanner(System.in);
		Boolean win = false;
		System.out.println("It's your turn, enter a move(0-6): ");
		int userfirstMove = sc.nextInt();
		while(checkFull(userfirstMove) || userfirstMove < 0 || userfirstMove > 6){
			System.out.println("You enter an invalid move, enter another move: ");
			userfirstMove = sc.nextInt();
		}
		putPiece(userfirstMove, -1, gameTable);
		pieceUser --;
		printBoard();
		System.out.println("It's my turn...");
		putPiece(3, 1, gameTable);
		pieceAI --;
		printBoard();
		
		while(pieceUser != 0 || pieceAI != 0 || win == false) {
			System.out.println("It's your turn, enter a move(0-6): ");
			int userMove = sc.nextInt();
			while(checkFull(userMove) || userMove < 0 || userMove > 6){
				System.out.println("You enter an invalid move, enter another move: ");
				userMove = sc.nextInt();
			}
			putPiece(userMove, -1, gameTable);
			int score = evaluateScore(userMove, -1, gameTable);
			System.out.println("score: "+ score);
			root = new Node(score);
			root.setLevel(0);
			pieceUser --;
			printBoard();
			if(checkWin(-1)) {
				System.out.println("Congratulations, you win!");
				win = true;
				break;
			}
			else {
				System.out.println("It's my turn...");
				generateTree(1);
				int aiMove = DFS();
				if(checkFull(aiMove)) {
					aiMove = firstOpenLoc();
					System.out.println("full");
				}
				putPiece(aiMove, 1, gameTable);
				System.out.println(aiMove);
				pieceAI--;
				printBoard();
				if(checkWin(1)) {
					System.out.println("Too bad, you lose.");
					win = true;
					break;
				}
			}
		}
	}
	
	public static void main(String [] args) {
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				gameTable[i][j] = "#";
			}
		}
		for(int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				fakeTable[i][j] = "#";
			}
		}
		System.out.println("Welcome to Connect4 game. Please choose a search depth to start.");
		Scanner sc = new Scanner(System.in);
		searchDepth = sc.nextInt();
		GameMode();
	}

}
