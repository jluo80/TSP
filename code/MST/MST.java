package MST;

public class MST {
	public int nodeNumber;
	public double[][] finalMST;

	public MST(Graph G) {
		nodeNumber = G.size;
		finalMST = new double[nodeNumber + 1][nodeNumber + 1];
		for (int i = 0; i < nodeNumber + 1; i++) {
			for (int j = 0; j < nodeNumber + 1; j++) {
				finalMST[i][j] = 0.00;
			}
		}
		Edges ed = new Edges(G);
		Kruskal krus = new Kruskal(ed);
		for (Edge myEdge : krus.listOfEdgesForMSP) {
			finalMST[myEdge.start][myEdge.end] = myEdge.weight;
		}
	}
}