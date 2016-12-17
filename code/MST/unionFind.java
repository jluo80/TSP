package MST;

public class unionFind {
	private int[] parent;
	private int[] rank;
	private int counter;

	public unionFind(int size) {
		parent = new int[size];
		rank = new int[size];
		for (int i = 0; i < size; i++) {
			parent[i] = i;
			rank[i] = 0;
		}
		counter = size;
	}

	public int find(int a) {
		while (a != parent[a]) {
			parent[a] = parent[parent[a]];
			a = parent[a];
		}
		return a;
	}

	public void union(int a, int b) {
		int i = find(a);
		int j = find(b);
		if (i != j) {
			if (rank[i] < rank[j]) {
				parent[i] = j;
			} else if (rank[i] > rank[j]) {
				parent[j] = i;
			} else {
				parent[j] = i;
				rank[i]++;
			}
			counter--;
		}
	}

	public boolean connected(int a, int b) {
		return find(a) == find(b);
	}
}
