package MST;

import java.util.LinkedList;

public class TSP {
	public double total_cost;
	public int startVertex;
	public int size;
	public int[][] adjacencyMatrix;
	LinkedList<Integer> eulerCycle = new LinkedList<Integer>();
	LinkedList<Integer> finalTSP = new LinkedList<Integer>();
	public Edge[] list;

	public TSP(MST mst, int s) {
		startVertex = s;
		size = mst.nodeNumber;
		adjacencyMatrix = new int[size + 1][size + 1];

		for (int i = 0; i <= size; i++) {
			for (int j = 0; j <= size; j++) {
				adjacencyMatrix[i][j] = 0;
			}
		}

		for (int i = 1; i <= size; i++) {
			for (int j = 1; j <= size; j++) {
				if (mst.finalMST[i][j] > 0.00) {
					adjacencyMatrix[i][j] = 1;
					adjacencyMatrix[j][i] = 1;
				}
			}
		}
	}

	public void tspFinder(int startVertex) {
		eulerCycle.addLast(startVertex);

		for (int nextVertex = 1; nextVertex <= size; nextVertex++) {
			if (adjacencyMatrix[startVertex][nextVertex] == 1) {
				eulerCycle.addLast(nextVertex);
				removeEdge(startVertex, nextVertex);
				tspFinder(nextVertex);
			}
		}
	}

	public void finalTSP(Graph G) {
		for (int i : eulerCycle) {
			if (finalTSP.contains(i) == false) {
				finalTSP.addLast(i);
			}
		}
		finalTSP.addLast(startVertex);

		list = new Edge[finalTSP.size() - 1];
		int i = 0;
		int j = i + 1;
		total_cost = 0;

		while (j < finalTSP.size()) {
			Edge e = new Edge();
			e.start = finalTSP.get(i);
			e.end = finalTSP.get(j);
			e.weight = G.matrixGraph[finalTSP.get(i)][finalTSP.get(j)];
			list[i] = e;
			total_cost = total_cost + list[i].weight;
			i++;
			j++;
		}
	}

	public void removeEdge(int start, int end) {
		adjacencyMatrix[start][end] = 0;
	}

	public void addEdge(int start, int end) {
		adjacencyMatrix[start][end] = 1;
		adjacencyMatrix[end][start] = 1;
	}
}
