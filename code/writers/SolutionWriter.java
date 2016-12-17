package writers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class SolutionWriter {
	private PrintWriter writer;

	public SolutionWriter(String instance, String method, Integer cutoff, Integer randSeed)
			throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File("./output/");
		writer = new PrintWriter(
				file.getAbsoluteFile() + "/" + instance + "_" + method + "_" + cutoff + "_" + randSeed + ".sol",
				"UTF-8");
	}

	public SolutionWriter(String instance, String method, Integer cutoff)
			throws FileNotFoundException, UnsupportedEncodingException {
		File file = new File("./output/");
		writer = new PrintWriter(file.getAbsoluteFile() + "/" + instance + "_" + method + "_" + cutoff + ".sol",
				"UTF-8");
	}

	public void write(Integer bestSol) {
		writer.println(bestSol);
	}

	public void write(Integer u, Integer v, Integer cost) {
		writer.println(u + " " + v + " " + cost);
	}

	public void close() {
		writer.close();
	}
}
