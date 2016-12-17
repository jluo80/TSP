package writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class TraceWriter {
	private PrintWriter writer;

	public TraceWriter(String instance, String method, Integer cutoff, Integer randSeed)
			throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File("./output/");
		writer = new PrintWriter(
				file.getAbsoluteFile() + "/" + instance + "_" + method + "_" + cutoff + "_" + randSeed + ".trace",
				"UTF-8");
	}

	public TraceWriter(String instance, String method, Integer cutoff)
			throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File("./output/");
		writer = new PrintWriter(file.getAbsoluteFile() + "/" + instance + "_" + method + "_" + cutoff + ".trace",
				"UTF-8");
	}

	public void write(Double timestamp, Integer bestSol) {
		double roundOff = (double) Math.round(timestamp * 100) / 100;
		writer.println(roundOff + ", " + bestSol);
	}

	public void close() {
		writer.close();
	}
}
