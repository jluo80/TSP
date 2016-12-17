package components;

import java.util.Comparator;
public class Vertex implements Comparable<Vertex> {
	private int weight;
	private int nodeNumber;

	public Vertex(int weight, int nodeNumber){
		this.weight = weight;
		this.nodeNumber = nodeNumber;
	}

	public int getWeight() {
		return weight;
	}

	public int getNodeNumber() {
		return nodeNumber;
	}

	@Override
	public int compareTo(Vertex other) {
		return Integer.compare(weight, other.weight);
	}
}