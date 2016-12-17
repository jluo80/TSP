package MST;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 *
 * @author Mohammad
 */
public class Kruskal {
	private Queue<Edge> mst;
	public ArrayList<Edge> listOfEdgesForMSP = new ArrayList();
	public double totalCost = 0.0;
	public int size = 0;

	public Kruskal(Edges myEdges) {
		mst = new LinkedList<Edge>();

		unionFind loopSet = new unionFind(myEdges.queueOfEdges.size());

		while (!myEdges.queueOfEdges.isEmpty() && mst.size() < myEdges.numberOfNodes) {
			Edge myEdge = myEdges.queueOfEdges.poll();
			int start = myEdge.either();
			int end = myEdge.other(start);

			if (!loopSet.connected(start, end)) {
				loopSet.union(start, end);
				mst.add(myEdge);
				size++;
			}
		}
		listOfEdgesForMSP.addAll(mst);
		this.totalCost();
	}

	public void totalCost() {

		for (Edge myEdge : mst) {
			totalCost += myEdge.weight;
		}
	}
}
