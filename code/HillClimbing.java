import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import components.Node;
import components.Parsers;
import writers.SolutionWriter;
import writers.TraceWriter;

public class HillClimbing {
	public static SolutionWriter sol;
	public static TraceWriter trace;

	public static void run(String instance, Integer cutoff, Integer seed) throws IOException {
		String method = "LS2";

		sol = new SolutionWriter(instance, method, cutoff, seed);
		trace = new TraceWriter(instance, method, cutoff, seed);

		File file = new File("./DATA/" + instance + ".tsp");
		List<Node> tourManager = Parsers.parseInput(file.getAbsolutePath());

		long start = System.nanoTime();
		List<Node> tour = runCode(tourManager, cutoff, seed);
		long end = System.nanoTime();
		int dist = (int) getDistance(tour);

		// // unit of time is millisecond.
		// double time = (end - start) / (double) 1000000000;

		// for (int k = 0; k < tour.size(); k++) {
		// 	if (k == tour.size() - 1) {
		// 		sol.write(tour.get(k).getId(), tour.get(0).getId(), (int) tour.get(k).distanceTo(tour.get(0)));
		// 		break;
		// 	}
		// 	sol.write(tour.get(k).getId(), tour.get(k + 1).getId(), (int) tour.get(k).distanceTo(tour.get(k + 1)));
		// }
		// trace.write(time, dist);

		sol.close();
		trace.close();
	}

	public static List<Node> runCode(List<Node> tourManager, int cutoff, int seed)
			throws FileNotFoundException, UnsupportedEncodingException {
		// 1. Generate initial Solution
		List<Node> tourOpt = randomSolution(tourManager, seed);
		long start = System.nanoTime();
		long interval = (long) Math.pow(10, 9) * cutoff;
		// 2. Hill climbing local search & complete 2-opt find neighbor.
		while (true && System.nanoTime() - start < interval) {
			boolean move = false;
			List<Node> tourTemp = tourOpt;
			// Find the best neighbor among all possible neighbors.
			for (int i = 0; i < tourOpt.size() - 1; i++) {
				for (int j = i + 1; j < tourOpt.size(); j++) {
					List<Node> tourSwap = twoOptExchange(tourOpt, i, j);
					if (getDistance(tourTemp) > getDistance(tourSwap)) {
						tourTemp = tourSwap;
					}
				}
			}
			// Determine if this is a good neighbor to move and record the
			// improved trace.
			if (getDistance(tourOpt) > getDistance(tourTemp)) {
				tourOpt = tourTemp;
				move = true;
				long improved = System.nanoTime();
				trace.write((improved - start) / (double) 1000000000, (int) getDistance(tourOpt));
			}
			// Keep moving until there is no more promising neighbor.
			if (!move)
				break;
		}
		for (int k = 0; k < tourOpt.size(); k++) {
			if (k == tourOpt.size() - 1) {
				sol.write(tourOpt.get(k).getId(), tourOpt.get(0).getId(),
						(int) tourOpt.get(k).distanceTo(tourOpt.get(0)));
				break;
			}
			sol.write(tourOpt.get(k).getId(), tourOpt.get(k + 1).getId(),
					(int) tourOpt.get(k).distanceTo(tourOpt.get(k + 1)));
		}
		// trace.close();
		// sol.close();

		return tourOpt;
	}

	// Randomly initialize a solution in the beginning.
	private static List<Node> randomSolution(List<Node> tourManager, long seed) {
		Collections.shuffle(tourManager, new Random(seed));
		return tourManager;
	}

	// Perform 2-opt switch and only accept solution with lower cost.
	// example route: A ==> B ==> C ==> D ==> E ==> F ==> G ==> H ==> A
	// example i = 4, example k = 7
	// new_route:
	// 1. (A ==> B ==> C)
	// 2. A ==> B ==> C ==> (G ==> F ==> E ==> D)
	// 3. A ==> B ==> C ==> G ==> F ==> E ==> D (==> H ==> A)
	private static List<Node> twoOptExchange(List<Node> tour, int i, int j) {
		List<Node> tourTemp = new ArrayList<Node>();
		// 1. take tour[0] to tour[i-1] and add them in order to tourTemp
		for (int a = 0; a <= i - 1; a++) {
			tourTemp.add(tour.get(a));
		}

		// 2. take tour[i] to tour[j] and add them in reverse order to tourTemp
		for (int b = j; b >= i; b--) {
			tourTemp.add(tour.get(b));
		}

		// 3. take tour[j+1] to end and add them in order to tourTemp
		for (int c = j + 1; c < tour.size(); c++) {
			tourTemp.add(tour.get(c));
		}
		return tourTemp;
	}

	// Calculate total distance for a candidate solution.
	private static double getDistance(List<Node> tour) {
		double dist = 0;
		for (int i = 0; i < tour.size(); i++) {
			Node prev = tour.get(i);
			Node next;
			if (i == tour.size() - 1) {
				next = tour.get(0);
			} else {
				next = tour.get(i + 1);
			}
			dist = dist + prev.distanceTo(next);
		}
		return Math.round(dist);
	}
}
