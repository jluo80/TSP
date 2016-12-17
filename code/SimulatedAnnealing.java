import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import components.Node;
import components.Parsers;
import writers.SolutionWriter;
import writers.TraceWriter;

public class SimulatedAnnealing {
	public static SolutionWriter sol;
	public static TraceWriter trace;

	public static void run(String instance, Integer cutoff, Integer seed) throws IOException {
		String method = "LS1";

		sol = new SolutionWriter(instance, method, cutoff, seed);
		trace = new TraceWriter(instance, method, cutoff, seed);

		Random generator = new Random(seed);

		// Read data
		File file = new File("./DATA/" + instance + ".tsp");
		List<Node> tourManager = Parsers.parseInput(file.getAbsolutePath());
		int num_node = tourManager.size();

		// get a random initial solution
		long start = System.nanoTime();
		int[] index = new int[num_node];
		index = GetRandomSequence(num_node, generator);
		List<Node> tour = new ArrayList<Node>();
		tour = getTour(index, tourManager);
		int initial_distance = getDistance(tour);

		// Set initial temperature & Cooling rate
		double temperature = 10000000; //1000000;
		double coolingRate = 0.00003; //0.00001;
		int best_distance = initial_distance;
		double last_time = -1;

		// Cooling loop
		while (temperature > 1) {
			// get a new tour and its total distance
			int[] index_temp = new int[num_node];
			index_temp = getNeighbor(index, generator);
			List<Node> newtour = new ArrayList<Node>();
			newtour = getTour(index_temp, tourManager);
			int new_distance = getDistance(newtour);

			// Decide if we should accept the neighbor
			if (acceptanceProbability(initial_distance, new_distance, temperature) > generator.nextDouble()) {
				initial_distance = new_distance;
				index = index_temp;
			}

			// Keep track of the best solution found
			if (best_distance > new_distance) {
				best_distance = new_distance;
				long end = System.nanoTime();
				double time = (end - start) / 1000000000.;
				last_time = time;
				trace.write(time, best_distance);
			}

			// Cool system
			temperature *= 1 - coolingRate;
		}

		// Get output
		sol.write(best_distance);
		for (int i = 0; i < num_node - 1; i++) {
			int cost = (int) tourManager.get(index[i]).distanceTo(tourManager.get(index[i + 1]));
			sol.write(index[i], index[i + 1], cost);
		}
		sol.write(index[num_node - 1], index[0],
				(int) tourManager.get(index[num_node - 1]).distanceTo(tourManager.get(index[0])));

		sol.close();
		trace.close();

//		System.out.println(last_time + "," + best_distance);
	}

	// **get the random node sequence**//
	public static int[] GetRandomSequence(int total, Random generator) {
		int[] sequence = new int[total];
		int[] output = new int[total];

		for (int i = 0; i < total; i++) {
			sequence[i] = i;
		}

		int end = total - 1;

		for (int i = 0; i < total; i++) {
			int num = generator.nextInt(end + 1);
			output[i] = sequence[num];
			sequence[num] = sequence[end];

			end--;
		}

		return output;
	}

	// **get the Tour based on the index**//
	public static List<Node> getTour(int[] index, List<Node> TourManager) {
		int num_node = index.length;
		List<Node> tour = new ArrayList<Node>();
		for (int i = 0; i < num_node; i++) {
			int j = index[i];
			Node cur = TourManager.get(j);
			tour.add(cur);
		}

		return tour;
	}

	// **Randomly get a neighbor**//
	public static int[] getNeighbor(int[] index, Random generator) {
		int num_node = index.length;
		// get random two indexes
		int nodeindex1 = (int) (num_node * generator.nextDouble());
		int nodeindex2 = (int) (num_node * generator.nextDouble());

		// find smaller and larger index
		if (nodeindex1 > nodeindex2) {
			int temp = nodeindex1;
			nodeindex1 = nodeindex2;
			nodeindex2 = temp;
		}

		// get new tour and new distance
		int[] index_temp = new int[num_node];
		for (int i = 0; i < num_node; i++) {
			index_temp[i] = index[i];
		}

		for (int i = nodeindex1, j = nodeindex2; i < j; i++, j--) {
			int temp = index_temp[i];
			index_temp[i] = index_temp[j];
			index_temp[j] = temp;
		}
		return index_temp;
	}

	// ** Calculate the acceptance probability**//
	public static double acceptanceProbability(int initial_distance, int new_distance, double temperature) {
		// If the new solution is better, accept it
		if (new_distance < initial_distance) {
			return 1.0;
		}
		double probability = Math.exp((initial_distance - new_distance) / temperature);
		// If the new solution is worse, calculate an acceptance probability
		// System.out.println("exp: "+ probability+" "+initial_distance+"
		// "+new_distance);
		return probability;
	}

	// **get the total distance of one tour**//
	public static int getDistance(List<Node> tour) {
		double initial_distance = 0;

		for (int i = 0; i < tour.size(); i++) {
			Node cur = tour.get(i);
			Node nex;

			// get initial distance
			if (i == tour.size() - 1) {
				nex = tour.get(0);
			} else {
				nex = tour.get(i + 1);
			}

			initial_distance = initial_distance + cur.distanceTo(nex);
		}

		return (int) initial_distance;
	}
}
