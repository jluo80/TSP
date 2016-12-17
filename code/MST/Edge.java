package MST;

public class Edge implements Comparable<Edge> {
	public int start = -1;
	public int end = -1;
	public double weight = -1;

	@Override
	public int compareTo(Edge E) {
		if (this.weight > E.weight) {
			return 1;
		} else if (this.weight < E.weight) {
			return -1;
		}
		return 0;
	}

	public double weight() {
		return weight;
	}

	public int either() {
		return start;
	}

	public int other(int node) {
		if (node == start)
			return end;
		else if (node == end)
			return start;
		else
			throw new IllegalArgumentException("Illegal endpoint");
	}
}
