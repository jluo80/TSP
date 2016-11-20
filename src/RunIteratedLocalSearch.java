import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class RunIteratedLocalSearch  {
    public static int main(List<Node> tourManager) {
        List<Integer> nodeList = new ArrayList<Integer>();
        for (int i = 0; i < tourManager.size(); i++) {
            nodeList.add(i);
        }

        // 1. Generate initial Solution
        List<Node> tourInitialOld = randomSolution(tourManager);
        List<Node> tourLsOld = localSearch(tourInitialOld);
        int it = 0;
        while (it <= 50000) {
            // 2. Perturbation
            List<Node> tourInitialNew = perturbation(tourLsOld, nodeList);
            // 3. Local Search Component
//            long start = System.nanoTime();
            List<Node> tourLsNew = localSearch(tourInitialNew);
            int distNew = getDistance(tourLsNew);
            // 4. Acceptance criterion
//          tourLsOld = acceptanceCriterion(tourLsOld, tourLsNew);
            int distOld = getDistance(tourLsOld);
            if (distOld > distNew) {
                tourLsOld = tourLsNew;
//                long end = System.nanoTime();
//                double totalTime = (end - start) / (double) 1000000;
//                System.out.println("Total distance: " + getDistance(tourLsOld));
//                System.out.println("Total time: " + totalTime);
            }
            it = it + 1;
        }
        int outputDistance = getDistance(tourLsOld);
//        System.out.println("Total distance: " + getDistance(tourLsOld));
//        System.out.println("Optimal tour: " + tourLsOld);
//        System.out.println("Size:"+ tourLsOld.size());
        return outputDistance;
    }

    // Calculate total distance for a candidate solution.
    private static int getDistance(List<Node> tour) {
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
        return (int) dist;
    }

    // Randomly initialize a solution in the beginning.
    private static List<Node> randomSolution(List<Node> tourManager) {
        long seed = System.nanoTime();
        Collections.shuffle(tourManager, new Random(seed));
        return tourManager;
    }

    // Perform 2-opt switch and only accept solution with lower cost.
    private static List<Node> localSearch(List<Node> tourNew) {
        List<Node> tourOpt = tourNew;
        for (int i = 0; i < tourNew.size() - 1; i++) {
            for (int j = i + 1; j < tourNew.size(); j++) {
                while (i < j) {
                    Collections.swap(tourNew, i, j);
                    i++;
                    j--;
                }

                if (getDistance(tourOpt) > getDistance(tourNew)){
                    tourOpt = tourNew;
                } else {
                    tourNew = tourOpt;
                }
            }
        }
        return tourOpt;
    }

    // Perform double-bridge move and try to escape from local optimal.
    private static List<Node> perturbation(List<Node> tourLsOld, List<Integer> nodeList) {
        long seed = System.nanoTime();
        Collections.shuffle(nodeList, new Random(seed));

        // Randomly pick 4 nodes to implement double-bridge move
        int[] arr = new int[4];
        for (int j = 0; j < 4; j++) {
            arr[j] = nodeList.get(j);
        }
        Arrays.sort(arr);

        // ArrayList a, b, c and d denote four sets of edges that need to be perturbed.
        // For example:
        // 3 10 15 19 => 3 19 15 10
        // (3 4 5 6 7 8 9) (10 11 12 13 14) (15 16 17 18) (19 20 1 2)
        // (3 4 5 6 7 8 9) (19 20 1 2) (15 16 17 18) (10 11 12 13 14)

        List<Integer> a = new ArrayList<Integer>();
        for (int i = arr[0]; i < arr[1]; i++) {
            a.add(i);
        }

        List<Integer> b = new ArrayList<Integer>();
        for (int i = arr[1]; i < arr[2]; i++) {
            b.add(i);
        }

        List<Integer> c = new ArrayList<Integer>();
        for (int i = arr[2]; i < arr[3]; i++) {
            c.add(i);
        }

        List<Integer> d = new ArrayList<Integer>();
        for (int i = arr[3]; i < nodeList.size(); i++) {
            d.add(i);
        }

        int m = 0;
        while (m < arr[0]) {
            d.add(m);
            m++;
        }

        // New tour seqence after perturbation.
        List<Integer> temp = new ArrayList<Integer>();
        temp.addAll(a);
        temp.addAll(d);
        temp.addAll(c);
        temp.addAll(b);
        
        List<Node> tourInitialNew = new ArrayList<Node>();
        for (int i = 0; i < temp.size(); i++) {
            int index = temp.get(i);
            tourInitialNew.add(tourLsOld.get(index));
        }
        return tourInitialNew;
    }

    // Accepting only improving solutions with lower cost.
    private static List<Node> acceptanceCriterion(List<Node> tourLsOld, List<Node> tourLsNew) {
        if (getDistance(tourLsOld) > getDistance(tourLsNew)){
            tourLsOld = tourLsNew;
        }
        return tourLsOld;
    }
}





















