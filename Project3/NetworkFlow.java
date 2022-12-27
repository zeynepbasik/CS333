package Project3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Scanner;

public class NetworkFlow {
	
	private static Map<Character, Integer> projects = new HashMap<Character, Integer>(); 
	private static String project_names [];
	private static int projectNums [];
	private static String [] prerequisities;
	private static int [][] graph;
	
	private static void PrintResult(List<String> results) {
		System.out.print("Venture projects: ");
		int sum = 0;
		for(int i = 0; i < results.size(); i++) {
			System.out.print(results.get(i) + " ");
			sum += projects.get(results.get(i).charAt(0));
		}
		System.out.println();
		System.out.print("Maximum profit: " + sum);
	}
	
	private static List<String> computeProjects(List<int[]> edges, char [] orderProjects, List<Character> pos) {
		List<String> results = new ArrayList<String>();
		List<Character> nos = new ArrayList<Character>();
		for(int i = 0; i < edges.size(); i++) {
			if(edges.get(i)[0] == 0) {
				for(int j = 0; j < orderProjects.length; j++) {
					if(edges.get(i)[1] == j) {
						nos.add(orderProjects[j]);
					}
				}
			}
			else {
				for(int j = 0; j < orderProjects.length; j++) {
					if(edges.get(i)[0] == j) {
						results.add("" + orderProjects[j]);
					}
				}
			}
		}
		
		for(int j = 0; j < pos.size(); j++) {
			if(!nos.contains(pos.get(j))) {
				results.add("" + pos.get(j));
			}
		}
		
			
		return results;
	}
	
	private static boolean bfs(int[][] rGraph, int s, int t, int[] parent) { 
		boolean[] visited = new boolean[rGraph.length]; 
  
		Queue<Integer> q = new LinkedList<Integer>(); 
		q.add(s); 
		visited[s] = true; 
		parent[s] = -1; 
  
		while (!q.isEmpty()) { 
			int v = q.poll(); 
			for (int i = 0; i < rGraph.length; i++) { 
				if (rGraph[v][i] > 0 && !visited[i]) { 
					q.offer(i); 
					visited[i] = true; 
					parent[i] = v; 
				} 
			} 
		} 
		boolean fin = false;
		if(visited[t] == true) {
			fin = true;
		}
   
		return fin; 
	} 
	
	private static void dfs(int[][] rGraph, int s, boolean[] visited) { 
		visited[s] = true; 
		for (int i = 0; i < rGraph.length; i++) { 
			if (rGraph[s][i] > 0 && !visited[i]) { 
				dfs(rGraph, i, visited); 
			} 
		} 
	} 
	
	private static List<int []> minCut(int s, int t) { 
		List<int[]> edges = new ArrayList<int[]>();
        int u,v; 
          
        int[][] rGraph = new int[graph.length][graph.length];  
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                rGraph[i][j] = graph[i][j]; 
            } 
        } 
  
        int[] parent = new int[graph.length];  
          
        
        while (bfs(rGraph, s, t, parent)) { 
              
            int pathFlow = Integer.MAX_VALUE;          
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                pathFlow = Math.min(pathFlow, rGraph[u][v]); 
            } 
              
            for (v = t; v != s; v = parent[v]) { 
                u = parent[v]; 
                rGraph[u][v] = rGraph[u][v] - pathFlow; 
                rGraph[v][u] = rGraph[v][u] + pathFlow; 
            } 
        } 
             
        boolean[] isVisited = new boolean[graph.length];      
        dfs(rGraph, s, isVisited); 
        
        
        for (int i = 0; i < graph.length; i++) { 
            for (int j = 0; j < graph.length; j++) { 
                if (graph[i][j] > 0 && isVisited[i] && !isVisited[j]) { 
                	int [] e = new int[3];
                	e[0] = i;
                	e[1] = j;
                	e[2] = graph[i][j];
                	edges.add(e);
                } 
            } 
        } 
        return edges;
    } 
	
	private static void MakeGraph() {
		graph = new int[projects.size()+2][projects.size()+2];
		List<Integer> positiveProjects = new ArrayList<Integer>();
		List<Integer> negativeProjects = new ArrayList<Integer>();
		char orderProjects [] = new char [projects.size()+2];
		List<Character> pos = new ArrayList<Character>();
		List<Character> neg = new ArrayList<Character>();
		orderProjects[0] = 's';
		for(Map.Entry<Character, Integer> entry : projects.entrySet()) {
			if(entry.getValue() >= 0) {
				positiveProjects.add(entry.getValue());
				pos.add(entry.getKey());
			}
			else {
				negativeProjects.add(-entry.getValue());
				neg.add(entry.getKey());
			}
		}
		int y = 0;
		for(; y < pos.size(); y++) {
			orderProjects[y+1] = pos.get(y);
		}
		int x = 0;
		for(int z = pos.size()+1; z < orderProjects.length-1; z++) {
			orderProjects[z] = neg.get(x);
			x++;
		}
		orderProjects[projects.size()+1] = 't';
		
		
		for(int i = 1; i < positiveProjects.size()+1; i++) {
			graph[0][i] = positiveProjects.get(i-1);
		}
		for(int i = 0; i < prerequisities.length; i++) {
			char pi = prerequisities[i].charAt(1);
			char pj = prerequisities[i].charAt(3);
			int numpi = 0;
			int numpj = 0;
			for(int j = 0; j < orderProjects.length; j++) {
				if(pi == orderProjects[j]) {
					numpi = j;
				}
				if(pj == orderProjects[j]) {
					numpj = j;
				}
			}
			graph[numpi][numpj] = Integer.MAX_VALUE;
		}
		int j = 0;
		for(int i = positiveProjects.size()+1; i < projects.size()+1; i++) {
			graph[i][projects.size()+1] = negativeProjects.get(j);
			j++;
		}
		
		List<int[]> edges = minCut(0, graph.length-1);
		List<String> results = computeProjects(edges, orderProjects, pos);
		PrintResult(results);
	}
	
	private static void printGraph() {
		for(int i = 0; i < graph.length; i++) {
			System.out.println();
			for(int j = 0; j < graph.length; j++) {
				System.out.print(graph[i][j] + " "); 
			}
		}
	}
	
	public static void main(String [] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the number of venture projects");
		int projects_size = sc.nextInt();
		sc.nextLine();
		System.out.println("Enter the names of the venture projects");
		String toConvertNames = "";
		if(sc.hasNextLine()) {
			toConvertNames = sc.nextLine();
		}
		project_names = toConvertNames.split(" "); 
		projectNums = new int [projects_size];
		for(int i = 0; i < project_names.length; i++) {
			projectNums[i] = i;
		}
		System.out.println("Enter the outcomes of the venture projects");
		String toConvertOutcomes = sc.nextLine();
		String projects_outcomes [] = toConvertOutcomes.split(" ");
		
		
		for(int i = 0; i < projects_size; i++) {
			projects.put(project_names[i].charAt(0), Integer.parseInt(projects_outcomes[i]));
		}
		System.out.println(projects);
		
		System.out.println("Enter the prerequisities");
		String pres = sc.nextLine();
		prerequisities = pres.split(" ");
		
		String decide = sc.nextLine();
		
		if(decide.equals("Decide")) {
			MakeGraph();
		}
		
	}

}
