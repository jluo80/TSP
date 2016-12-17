
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.Node;
import components.Parsers;
import writers.SolutionWriter;
import writers.TraceWriter;

/*
 * Algorithm: nearest neighbor
 * Steps (wikipedia):
 * 1. start on an arbitrary vertex as current vertex.
 * 2. find out the shortest edge connecting current vertex and an unvisited vertex V.
 * 3. set current vertex to V.
 * 4. mark V as visited.
 * 5. if all the vertices in domain are visited, then terminate.
 * 6. Go to step 2.
 */
public class NearestNeighbor {
	static int overallBestSol;

	public static void run(String instance, Integer cutoff, Integer seed) throws Exception {
		String method = "Heur";

		File file = new File("./DATA/" + instance + ".tsp");
		List<Node> tourManager = Parsers.parseInput(file.getAbsolutePath());
		int total = tourManager.size();

		SolutionWriter sol = new SolutionWriter(instance, method, cutoff);
		TraceWriter trace = new TraceWriter(instance, method, cutoff);

		List<String> best_results = null;
		int best_Sol = Integer.MAX_VALUE;
		double best_time = 0;

		// System.out.println(tourManager.size());
		for(int i = 0; i < total; i++) {
			tourManager = Parsers.parseInput(file.getAbsolutePath());
			overallBestSol = 0;
			long start = System.nanoTime();
			List<String> results = runAlgorithm(i, tourManager);
			long end = System.nanoTime();
			double time = (end-start)/1000000000.;
			// System.out.println(i + " " + overallBestSol);

			if (overallBestSol < best_Sol) {
				best_results = results;
				best_Sol = overallBestSol;
				best_time = time;
			}
		}

		sol.write(best_Sol);
		for(String result : best_results) {
			Integer first_node = Integer.parseInt(result.split(" ")[0]);
			Integer second_node = Integer.parseInt(result.split(" ")[1]);
			Integer cost = Integer.parseInt(result.split(" ")[2]);
			sol.write(first_node, second_node, cost);
		}
		trace.write(best_time, best_Sol);
		// System.out.println(best_time + ", " + best_Sol);

		sol.close();
		trace.close();
	}

	private static List<String> runAlgorithm(Integer seed, List<Node> tourManager) {
		List<String> results = new ArrayList<String>();
		// Random ran = new Random(seed);

		int node_index = seed; //ran.nextInt(tourManager.size());
		Node current_node = tourManager.get(node_index);
		Node first_node = tourManager.get(node_index);
		tourManager.remove(current_node);

		while (!tourManager.isEmpty()) {
			Node nearest = findNearestNode(current_node, tourManager);
			Integer cost = (int) current_node.distanceTo(nearest);
			results.add(current_node.getId() + " " + nearest.getId() + " " + cost);
			overallBestSol += cost;
			current_node = nearest;
			tourManager.remove(current_node);
		}

		Integer cost = (int) current_node.distanceTo(first_node);
		results.add(current_node.getId() + " " + first_node.getId() + " " + cost);
		overallBestSol += cost;

		return results;
	}

	private static Node findNearestNode(Node n, List<Node> tourManager) {
		Node nearest_node = null;
		double min_distance = Double.MAX_VALUE;

		for (Node current : tourManager) {
			double new_distance = n.distanceTo(current);
			if (new_distance < min_distance) {
				min_distance = new_distance;
				nearest_node = current;
			}
		}

		return nearest_node;
	}
}
