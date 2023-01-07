package Project4;

import java.util.Scanner;

public class NPComplete {
	
	private static int items;
	private static int [] weigths;
	private static int [] profits;
	private static int maxWeigth;
	private static int minProfit;
	private static Node root;
	private static boolean output = false;
	
	private static void DFS(Node node, int sumWeigth, int sumProfit) {
		if(node == null) {
			return;
		}
		if(node.getValue() == 1) {
			sumWeigth += weigths[node.getIndex()];
			sumProfit += profits[node.getIndex()];
		}
		if(node.isLeaf()) {
			if(sumWeigth <= maxWeigth && sumProfit >= minProfit) {
				output = true;
				return;
			}
			sumWeigth = 0;
			sumProfit = 0;
		}
		else {
			if(sumWeigth > maxWeigth) {
				node.removeChildren();
			}
			if(node.getChildren().get(0) != null)
				DFS(node.getChildren().get(0), sumWeigth, sumProfit);
			if(node.getChildren().get(1) != null)
				DFS(node.getChildren().get(1), sumWeigth, sumProfit);
		}
	}
	
	private static void ConstructTree(Node node, int index) {
		if(index == items) {
			
		}
		else {
			Node zero = new Node(0, node, index);
			Node one = new Node(1, node, index);
			node.addChild(zero);
			node.addChild(one);
			ConstructTree(zero, index + 1);
			ConstructTree(one, index + 1);
		}
	}
	
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter the number of items:");
		items = sc.nextInt();
		while(items > 25) {
			System.out.println("Please enter a number smaller than 25:");
			items = sc.nextInt();
		}
		sc.nextLine();
		System.out.println("Please enter the weigths of the items:");
		String w [] = sc.nextLine().split(" ");
		weigths = new int[items];
		for(int i = 0; i < items; i++) {
			weigths[i] = Integer.parseInt(w[i]);
		}
		System.out.println("Please enter the profits of the items");
		String p [] = sc.nextLine().split(" ");
		profits = new int[items];
		for(int i = 0; i < items; i++) {
			profits[i] = Integer.parseInt(p[i]);
		}
		System.out.println("Please enter the max weight that can be carried:");
		maxWeigth =sc.nextInt();
		System.out.println("Please enter the minimum profit required:");
		minProfit = sc.nextInt();
		
		root = new Node();
		
		ConstructTree(root, 0);
		DFS(root, 0, 0);
		
		if(output == true) {
			System.out.println("YES");
		}
		else {
			System.out.println("NO");
		}
	}

}
