package components;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Parsers {
	public static List<Node> parseInput(String fileName) throws IOException {
		List<Node> TourManager = new ArrayList<Node>();

		// read data
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		String line = br.readLine();
		String[] split = line.split(" ");

		// count for the line
		int c = 0;
		// number of nodes
		int num_nodes = 0;

		// read data line by line
		while ((line = br.readLine()) != null) {
			if (c == 1) {
				split = line.split(" ");
				num_nodes = Integer.parseInt(split[1].trim());
			}

			if (c > 3 && c < (4 + num_nodes)) {
				split = line.split(" ");
				int label = (int) Double.parseDouble(split[0]);
				int x = (int) Double.parseDouble(split[1]);
				int y = (int) Double.parseDouble(split[2]);
				Node node = new Node(label, x, y);
				TourManager.add(node);
			}
			c++;
		}
		br.close();

		return TourManager;
	}
}
