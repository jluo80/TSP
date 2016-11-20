import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class RunTest {
    public static void main(String[] args) {
        File data = new File("./src/data");
        File[] list = data.listFiles();
        String[] fileName = new String[list.length];

    	if (list.length <= 0) {
			System.err.println("Unexpected number of files");
			System.exit(1);
    	}

//    	String outputFile = "./src/output/output_" + "boston" + ".txt";
//    	String outputFile = "./src/output/output_" + "cincinnati" + ".txt";
//    	String outputFile = "./src/output/output_" + "roanoke" + ".txt";
    	String outputFile = "./src/output/output_" + "toronto" + ".txt";


    	PrintWriter output;
		try {
			output = new PrintWriter(outputFile, "UTF-8");
	    	double totalTime = 0;
	    	int totalDist = 0;
			DecimalFormat df = new DecimalFormat("0.00");
	        for (int j = 0; j < 10; j++) {
//	        	int i = 1;
//	        	int i = 3;
//	        	int i = 7;
	        	int i = 9;
	            if (list[i].isFile()) {
	            	fileName[i] = list[i].getName();
//	                System.out.println(list[i].getName());
	                List<Node> tourManager = new ArrayList<Node>();
	                tourManager = parseFile(fileName[i]);

	                long start = System.nanoTime();
	                int dist = RunIteratedLocalSearch.main(tourManager);
	                long end = System.nanoTime();
	                totalDist = totalDist + dist;
	                
	                double time = (end - start) / (double) 1000000;
	                totalTime = totalTime + time;
	              	
//	                System.out.println(j + " Total time: " + time);
//	              	System.out.println(j + " Total time: " + dist);
	        		output.println(df.format(time) + "," + dist);
	            }
	        }
//	        System.out.println("Average distance: " + totalDist / 10.0);
//	      	System.out.println("Average time(milisecond): " + totalTime / 10.0 / 1000000);
//	      	System.out.println("Average time(second): " + totalTime / 10.0 / 1000000000);
			output.println("Average(milisecond): " + df.format(totalTime / 10.0 / 1000000) + "," + totalDist / 10);
			System.out.println("Completed");
			output.close();
		} catch (FileNotFoundException | UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

	private static List<Node> parseFile(String fileName) {
    	String inputFile = "./src/data/" + fileName;
//		System.out.println(inputFile);
		List<Node> tourManager = new ArrayList<Node>();

    	try {
			BufferedReader br = new BufferedReader(new FileReader(inputFile));
			
			// Read the number of points
			int n = 3;
			while (n-- > 1) {
				br.readLine();
			}
			String line = br.readLine();
			StringTokenizer dimensionTok = new StringTokenizer(line, "DIMENSION: ");
			int dimension = Integer.parseInt(dimensionTok.nextToken());
//			System.out.println(dimension);
			// Read the coordinates information
			n = 3;
			while (n-- > 1) {
				br.readLine();
			}
			while ((line = br.readLine()) != null) {
				if (!line.equals("EOF")) {
					StringTokenizer coordTok = new StringTokenizer(line, " ");
					int nodeID = Integer.parseInt(coordTok.nextToken());
					int x = (int) Double.valueOf(coordTok.nextToken()).doubleValue();
					int y = (int) Double.valueOf(coordTok.nextToken()).doubleValue();
//					System.out.println(x + " " + y);
					Node node = new Node(x,y);
					tourManager.add(node);
				} 
			}
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
            System.out.println("Unable to open file '" + inputFile + "'");
		} catch (IOException ex) {
            System.out.println("Error reading file '" + inputFile + "'");
        }
		return tourManager;
	}
}














