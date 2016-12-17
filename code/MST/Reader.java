package MST;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Mohammad
 */
public class Reader {

	private String BASE_PATH = null;

	public Reader() {
		BASE_PATH ="./DATA/";
	}

	public Graph readGraph(String fileName) {
		String file = BASE_PATH + fileName;
		String line = null;

		Graph G = null;
		Nodes cityNodes = null;
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader buffer = new BufferedReader(fileReader);
			String s1 = buffer.readLine();
			String[] split1 = s1.split(" ");
			String s2 = buffer.readLine();
			String s3 = buffer.readLine();
			String[] split3 = s3.split(" ");
			String s4 = buffer.readLine();
			String s5 = buffer.readLine();
			G = new Graph(Integer.parseInt(split3[1]));
			G.cityName = split1[1];

			cityNodes = new Nodes(Integer.parseInt(split3[1]));

			while (!(s1 = buffer.readLine()).equalsIgnoreCase("EOF")) {
				String[] split = s1.split(" ");
				int v = Integer.parseInt(split[0]);
				double vX = Double.parseDouble(split[1]);
				double vY = Double.parseDouble(split[2]);

				Node n = new Node(v, vX, vY);
				cityNodes.addNode(n);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		G.fillGraph(cityNodes.allNodes);
		return G;
	}
}
