import MST.Graph;
import MST.MST;
import MST.MST_writer;
import MST.Reader;
import MST.TSP;

public class MST_tsp {

	public static void run(String city, Integer cutoff, Integer seed) {
		Reader reader = new Reader();

		Graph myG = reader.readGraph(city + ".tsp");

		// A Random Vertex to Start the TSP
		int min = 1;
		int max = myG.size;
		int startVertex;
		startVertex = (int) (Math.random() * max + min);
		long startTime = System.nanoTime();
		MST T = new MST(myG);
		TSP myTSP = new TSP(T, startVertex);
		myTSP.tspFinder(startVertex);
		myTSP.finalTSP(myG);
		long endTime = System.nanoTime();
		double time = (endTime - startTime) / 1000000000.0;

		MST_writer writer = new MST_writer();
		writer.WriteOutPut(myTSP, myG, cutoff);
		writer.writeTrace(myTSP, myG, cutoff, time);
	}
}