package MST;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class MST_writer {
	private String BASE_PATH = null;

	public MST_writer() {
		BASE_PATH = "./output/";
	}

	public void WriteOutPut(TSP tsp, Graph G, Integer cutoff) {
		BufferedWriter writer = null;

		String fileName = String.format(BASE_PATH + G.cityName + "_" + "MSTApprox_" + cutoff + ".sol");

		try {
			File outPutFile = new File(fileName);

			writer = new BufferedWriter(new FileWriter(outPutFile));
			writer.write(String.format("%d", (int) Math.round(tsp.total_cost)));
			writer.newLine();
			for (Edge e : tsp.list) {
				writer.write(String.format("%d %d %d", e.start, e.end, (int) Math.round(e.weight)));
				writer.newLine();

			}
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void writeTrace(TSP tsp, Graph G, Integer cutoff, Double time) {
		BufferedWriter writer = null;

		String fileName = String.format(BASE_PATH + G.cityName + "_" + "MSTApprox_" + cutoff  + ".trace");

		try {
			File outPutFile = new File(fileName);

			writer = new BufferedWriter(new FileWriter(outPutFile));
			writer.write(String.format(time + " " + Math.round(tsp.total_cost)));
			writer.newLine();
			writer.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
