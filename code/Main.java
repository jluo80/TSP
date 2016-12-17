public class Main {
	public static void main(String[] args) throws Exception {
		// String value = "Atlanta";
		// exec −inst < filename > −alg [BnB|MSTApprox|Heur|LS1|LS2] −time <
		// cutoff in secods > −seed < random seed >
		 // String[] instances = {"Atlanta", "Boston", "Champaign", "Cincinnati", "Denver",
				 // "NYC", "Philadelphia", "Roanoke", "SanFrancisco", "Toronto", "UKansasState", "UMissouri"};

 		// for(String value : instances) {
// 		for(int i = 1; i < 11; i++) {
			// args = new String[]{"-inst", value, "-alg", "Heur", "-time", "600", "-seed", "0"};

			checkArguments(args);
			String instance = args[1];
			String alg = args[3];
			Integer time = Integer.parseInt(args[5]);
			Integer seed = -1;
			if (args.length == 8) {
				seed = Integer.parseInt(args[7]);
			}

			if (alg.equals("BnB")) {
				BnB_DFS.run(instance, time, seed);
			} else if (alg.equals("MSTApprox")) {
				MST_tsp.run(instance,  time, seed);
			}  else if (alg.equals("Heur")) {
				NearestNeighbor.run(instance, time, seed);
			} else if (alg.equals("LS1")) {
				SimulatedAnnealing.run(instance, time, seed);
			}  else if (alg.equals("LS2")) {
				HillClimbing.run(instance, time, seed);
			}

//			System.out.println("Done with " + value + " on " + alg);
		// }
		// }
		System.out.println("Done!");
	}

	public static void checkArguments(String[] args) {
		if (args.length < 6 || args.length > 8) {
			System.out.println("Incorrect format, use: −inst <filename> −alg [BnB|MSTApprox|Heur|LS1|LS2] −time <cutoff in seconds> −seed <random seed>");
			System.exit(0);
		}

		if (!args[0].equals("-inst") || !args[2].equals("-alg") || !args[4].equals("-time")) {
			System.out.println("Incorrect format, use: −inst <filename> −alg [BnB|MSTApprox|Heur|LS1|LS2] −time <cutoff in seconds> −seed <random seed>");
			System.exit(0);
		}

		if (args.length == 8 && !args[6].equals("-seed")) {
			System.out.println("Incorrect format, use: −inst <filename> −alg [BnB|MSTApprox|Heur|LS1|LS2] −time <cutoff in seconds> −seed <random seed>");
			System.exit(0);
		}

		if (!args[1].equals("Atlanta") && !args[1].equals("Boston") && !args[1].equals("Champaign")
				&& !args[1].equals("Cincinnati") && !args[1].equals("Denver") && !args[1].equals("NYC")
				&& !args[1].equals("Philadelphia") && !args[1].equals("Roanoke") && !args[1].equals("SanFrancisco")
				&& !args[1].equals("Toronto") && !args[1].equals("UKansasState") && !args[1].equals("UMissouri")) {
			System.out.println("Incorrect instance name");
			System.exit(0);
		}

		if (!args[3].equals("BnB") && !args[3].equals("MSTApprox") && !args[3].equals("Heur")
				&& !args[3].equals("LS1") && !args[3].equals("LS2")) {
			System.out.println("Incorrect algorithm name");
			System.exit(0);
		}
	}
}
