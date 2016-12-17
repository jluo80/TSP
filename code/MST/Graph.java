package MST;

public class Graph {
	public String cityName;
	public int size;
	public double[][] matrixGraph;

	public Graph() {
	}

	public Graph(int size) {
		this.size = size;
		matrixGraph = new double[size + 1][size + 1];
	}

	public double distance(Node n, Node m) {
		double xDist = Math.abs(n.x - m.x);
		double yDist = Math.abs(n.y - m.y);
		double distance = Math.sqrt((xDist * xDist) + (yDist * yDist));
		return distance;
	}

	public void fillGraph(Node[] allNodes) {
		for (int i = 1; i < (allNodes.length); i++) {
			for (int j = 1; j < (allNodes.length); j++) {
				matrixGraph[i][j] = distance(allNodes[i], allNodes[j]);
			}
		}
	}
}