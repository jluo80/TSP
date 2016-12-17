package MST;

import java.util.PriorityQueue;

public class Edges {
	// public TreeMap<String, Edge> edges = new TreeMap();
	public int numberOfNodes = -1;
	public PriorityQueue<Edge> queueOfEdges = new PriorityQueue<Edge>();

	public Edges() {

	}

	public Edges(Graph G) {
		numberOfNodes = G.size;
		for (int i = 1; i <= G.size; i++) {
			for (int j = 1; j <= G.size; j++) {
				Edge e = new Edge();
				e.start = i;
				e.end = j;
				e.weight = G.matrixGraph[i][j];
				queueOfEdges.add(e);
			}
		}
	}
}
