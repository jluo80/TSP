package MST;

public class Nodes {
	public int dim;
	public Node[] allNodes;

	public Nodes(int d) {
		dim = d;
		allNodes = new Node[dim + 1];
	}

	public void addNode(Node myNode) {
		allNodes[myNode.name] = myNode;
	}
}