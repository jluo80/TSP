package components;
import java.util.Comparator;
public class Edge implements Comparable<Edge>
{
	private int weight;
	private int nodeNumber;
	public Edge(int weight, int nodeNumber){
		this.weight = weight;
		this.nodeNumber = nodeNumber;

	}
	public int getWeight(){
		return weight;
	}
	public int getNodeNumber(){
		return nodeNumber;
	}

	@Override
	public int compareTo(Edge other) {
		return Integer.compare(weight, other.weight);
	}
}