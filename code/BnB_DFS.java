
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import writers.SolutionWriter;
import writers.TraceWriter;
import components.Node;
import components.Vertex;
import components.Edge;

public class BnB_DFS {
	
	public static int total_nodes,lowerBound,upperBound;
	public static boolean[] Visit;
	public static int index[];
	public static int index_record[];
	public static int[][] distanceMatrix;
	public static long startTime;
	public static SolutionWriter sol;
	public static TraceWriter trace;
	public static ArrayList<Integer> candidates;
	

	public static void run(String instance, Integer cutoff, Integer seed) throws Exception {
		String method = "BnB";

		sol = new SolutionWriter(instance, method, cutoff);
		trace = new TraceWriter(instance, method, cutoff);
		
		//Read data
		List<Node> TourManager = new ArrayList<Node>();
		String fileName = "./DATA/" + instance + ".tsp";
		TourManager = ParseData(fileName);
		int num_node = TourManager.size();

        //get distance matrix
		distanceMatrix = getDistanceMatrix(TourManager);
		// System.out.println(String.valueOf(TourManager));

        //initial parameters
		total_nodes = num_node;
		lowerBound = Integer.MIN_VALUE;
		upperBound = Integer.MAX_VALUE;
		Visit = new boolean[total_nodes];
		// System.out.println("initialUpperBound: "+upperBound);
		index= new int[num_node];
		index_record= new int[num_node];

		index[0] = 1; 
		Visit[0] = true;
		candidates = new ArrayList<Integer>();
		for(int i = 1; i<total_nodes;i++) {
			if(!Visit[i]) {
				candidates.add(i);
			}
		}

        //Run the algorithm
		long cutoff_real = (long) (Math.pow(10, 9)*cutoff);
		// System.out.println(cutoff_real);
		startTime = System.nanoTime();
		int result = dfsSearch(TourManager,cutoff_real);
		long end = System.nanoTime();
		double time = (end-startTime )/1000000000.;

		//   Get output
		sol.write(result);
		// System.out.println("result:" + result);
		// System.out.println("time:" + time);
		for(int i = 0; i<total_nodes-1;i++) {
			// System.out.println(index_record[i]-1);
			int cost = (int) TourManager.get(index_record[i]-1).distanceTo(TourManager.get(index_record[i+1]-1));
			sol.write(index_record[i]-1, index_record[i+1]-1, cost);
			// System.out.println(String.valueOf(index_record[i]-1));
		}
		sol.write(index_record[total_nodes-1]-1, index_record[0]-1, (int) TourManager.get(index_record[total_nodes-1]-1).distanceTo(TourManager.get(index_record[0]-1)));
		
		sol.close();
		trace.close();
	}
	

		//**Read data**//
	public static List<Node> ParseData(String fileName) throws IOException {
		List<Node> TourManager = new ArrayList<Node>();
			//read data
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		String[] split = line.split(" ");
		int xx,yy;
			//count for the line
		int c = 0;
			//number of nodes
		int num_nodes = 0;
		int id = 0;
			//read data line by line		
		while ((line = br.readLine()) != null) {
			if (c==1) {
				split = line.split(" ");
				num_nodes = Integer.parseInt(split[1].trim());
			}
			if (c>3&&c<(4+num_nodes)) {
				split = line.split(" ");
				String x_string = split[1];
				String y_string = split[2];
				xx = (int)Double.parseDouble(x_string);
				yy = (int)Double.parseDouble(y_string);
				Node node = new Node(id,xx,yy);
				TourManager.add(node);
				id++;

			}
			c++;
		}
		br.close();

		return TourManager;
	}





		//compute MST
	public static int computeMST(int[][] distanceMatrx, ArrayList<Integer> candidates) {
		int n = candidates.size();
		HashSet<Integer> explored = new HashSet<Integer>();
		PriorityQueue<Edge> MinQ = new PriorityQueue<Edge>();
		int MSTcost = 0;
		MinQ.add(new Edge(0, 0));
		while(!MinQ.isEmpty()) {
			Edge aNode = MinQ.poll();
			int nodeNumber = aNode.getNodeNumber();
			if(!explored.contains(nodeNumber)) {
				explored.add(nodeNumber);
				MSTcost+=aNode.getWeight();
				for(int j = 0; j<n; j++) {
					if(!explored.contains(j)) {
						Edge toAdd = new Edge(distanceMatrx[candidates.get(nodeNumber)][candidates.get(j)], j);
						MinQ.add(toAdd);
					}
				}
			}			
		}		
		return MSTcost;

	}


		//**get the total distance of one tour without considering the connection between first and last node**//
	public static int getDistance(int[] index) {
		List<Integer> tour = new ArrayList<Integer>();
		int initial_distance = 0;
		for(int i = 0;i<total_nodes;i++) {
			if(index[i]!=0) {
				tour.add(index[i]-1);
			}
		}
		for(int j = 0;j < tour.size()-1;j++) {
			initial_distance = initial_distance + distanceMatrix[tour.get(j)][tour.get(j+1)];
		}

		return initial_distance;

	}

		//**get the total distance of one tour**//
	public static int getTourDistance(int[] index) {
		List<Integer> tour = new ArrayList<Integer>();
		int initial_distance = 0;
		for(int i = 0;i<total_nodes;i++) {
			if(index[i]!=0) {
				tour.add(index[i]-1);
			}
		}
		for(int j = 0;j < tour.size()-1;j++) {
			initial_distance = initial_distance + distanceMatrix[tour.get(j)][tour.get(j+1)];
		}
		initial_distance = initial_distance + distanceMatrix[index[index.length-1]-1][index[0]-1];

		return (int)initial_distance;

	}

	public static int getLowerBound(List<Node> TourManager, int[] index) {
		List<Node> partialSolution = new ArrayList<Node>();
		List<Node> restSolution = new ArrayList<Node>();

		for(Node node : TourManager) {
			restSolution.add(node);
		}

		for(int i = 0;i<total_nodes;i++) {
			if(index[i]!=0) {
				partialSolution.add(TourManager.get(index[i]-1));
			}
		}

		restSolution.removeAll(partialSolution);
		int num_node = partialSolution.size();
		Node firstNode = partialSolution.get(0);
		Node lastNode = partialSolution.get(num_node-1);
		int partialDistance = getDistance(index);
		if(restSolution.size( ) == 0 || restSolution == null) {
			return partialDistance;
		}

		ArrayList<Integer> candiates = new ArrayList<Integer>();
		for(int i = 0; i<restSolution.size(); i++) {
			candiates.add(restSolution.get(i).getId());
		}

		
		int MST = computeMST(distanceMatrix,candiates);
		//connection length between partialSolution and MST;
		int connection_head = findPathToMST(candiates,index[0]-1);
		int connection_tail = findPathToMST(candiates,index[partialSolution.size()-1]-1);

		return connection_head+connection_tail+partialDistance+MST;	
	}

		//**Perform DFS search algorithm**// 
	public static int dfsSearch(List<Node> TourManager,long cutoff) {
//			for(int k = 0; k < total_nodes; k++) {
//        		System.out.print(index[k]);
//        	}
//        	System.out.println(" ");

		if(System.nanoTime()-startTime > cutoff) {
			return upperBound;
		}

		//find out the depth of the tree so far;
		int d = 0;
		for(int i = 0; i<total_nodes; i++) {
			if(Visit[i]) {
				d=d+1;
			}
		}

		//find the bottom of the tree and output the result for one recursion
		if(d==total_nodes) {
			int upperBound_temp = getTourDistance(index);
			if(upperBound_temp<upperBound) {
				upperBound = upperBound_temp;
				for(int i=0; i<index.length; i++) {
					index_record[i] = index[i];
				}
				long end = System.nanoTime();
				double time = (end-startTime )/1000000000.;

				trace.write(time, upperBound);
//        			for(int k =0; k<total_nodes; k++) {
//        				System.out.print(index[k]);
//        			}
//        			System.out.println(" ");
//        			System.out.println("upperbound: "+upperBound);
			}
		}

		PriorityQueue<Vertex> bestMSTQueue = new PriorityQueue<Vertex>();
		for(int i = 0; i<candidates.size();i++) {
			index[d] = candidates.get(i)+1;
			int weight = getLowerBound(TourManager,index);
//	        	for(int k =0; k<total_nodes; k++) {
//    				System.out.print("index_temp: "+index[k]);
//    			}
//    			System.out.println(" ");
			bestMSTQueue.add(new Vertex(weight,candidates.get(i)));
//	        	System.out.println(weight+" "+candidates.get(i));
			index[d] = 0;
		}
//	        System.out.println("next loop");

	    //Recursion for DFS
		while(!bestMSTQueue.isEmpty()) {
			Vertex vertex = bestMSTQueue.poll();
			int i = vertex.getNodeNumber();
//	        	System.out.println("check:" + i);
			index[d] = i+1;
			Visit[i] = true;
			candidates.remove((Integer) vertex.getNodeNumber());
			lowerBound = getLowerBound(TourManager,index);
			if(lowerBound < upperBound) {
				dfsSearch(TourManager,cutoff);

			}
			candidates.add(vertex.getNodeNumber());
			index[d] = 0;
			Visit[i] = false;
		}

		return upperBound;
	}

	//store all the distance for between two nodes into a matrix
	public static int[][] getDistanceMatrix(List<Node> TourManager) {
		int num_node = TourManager.size();
		int[][] distanceMatrix = new int[num_node][num_node];
		for(int i = 0; i < num_node; i++) {
			for(int j = 0; j < num_node; j++) {

				distanceMatrix[i][j] = (int) (TourManager.get(i).distanceTo(TourManager.get(j)));
			}
		}
		return distanceMatrix;
	}

	public static int findPathToMST(ArrayList<Integer> candidates, int vertex) {
		int min = Integer.MAX_VALUE;
		for(int i: candidates) {
			min = Math.min(distanceMatrix[i][vertex], min);
		}
		return min;
	}

}
