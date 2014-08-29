package core;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import core.utilities.InOut;
import core.utilities.Timer;
import core.utilities.Utilities;
import de.adrianwilke.acotspjava.Ants;
import de.adrianwilke.acotspjava.Tsp;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class is used to control the main procedures of this algorithm
 *
 */
public class AlgControl {

	/**
	 * initilialize variables appropriately when starting a trial
	 * 
	 * @param ntry
	 */
	public static void init_try(int ntry) {
		Timer.start_timers();
		InOut.time_used = Timer.elapsed_time();
		InOut.time_passed = InOut.time_used;

		if (InOut.comp_report != null) {
			InOut.printToFile(InOut.comp_report, "Utilities.seed " + Utilities.seed);
		}
		/* Initialize variables concerning statistics etc. */

		InOut.n_solutions = 1;
		InOut.iteration = 1;
		InOut.restart_iteration = 1;
		Ants.best_so_far_ant.path_length = Double.MAX_VALUE;

		// Initialize the Pheromone trails, only if ACS is used, Ants.pheromones have to be initialized differently
		if (!(Ants.acs_flag || Ants.bwas_flag)) {
			Ants.trail_0 = 1. / ((Ants.rho) * Ants.nn_tour());
			/*
			 * in the original papers on Ant System, Elitist Ant System, and Rank-based Ant System it is not exactly
			 * defined what the initial value of the Ants.pheromones is. Here we set it to some small constant,
			 * analogously as done in MAX-MIN Ant System.
			 */
			Ants.init_pheromone_trails(Ants.trail_0);
		}
		if (Ants.bwas_flag) {
			Ants.trail_0 = 1. / ((double) Tsp.n * (double) Ants.nn_tour());
			Ants.init_pheromone_trails(Ants.trail_0);
		}
		if (Ants.mmas_flag) {
			Ants.trail_max = 1. / ((Ants.rho) * Ants.nn_tour());
			Ants.trail_min = Ants.trail_max / (2. * Tsp.n);
			Ants.init_pheromone_trails(Ants.trail_max);
		}
		if (Ants.acs_flag) {
			Ants.trail_0 = 1. / ((double) Tsp.n * (double) Ants.nn_tour());
			Ants.init_pheromone_trails(Ants.trail_0);
		}

	}

	/**
	 * main control for running the ACO algorithms and dijkstra algorithm
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (String argument : args) {
			System.out.println(argument);
		}
		Timer.start_timers();

		InOut.init_program(args);

		if (InOut.dijkstra_flag) {
			System.out.println("using dijkstra algorithm");
			System.out.println("Problem Name: " + InOut.problem.name);
			DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<Integer, DefaultWeightedEdge>(
					InOut.problem.graph, InOut.problem.start_vertex, InOut.problem.end_vertex);
			InOut.problem.shortest_path_edge_list = dijkstraShortestPath.getPathEdgeList();
			InOut.problem.shortest_path_length = dijkstraShortestPath.getPathLength();
		} else {
			System.out.println("using ACO algorithm");
			Ants.pheromone = Utilities.generate_double_matrix(InOut.problem.graph.vertexSet().size(),
					InOut.problem.graph.vertexSet().size());
			Ants.total = Utilities.generate_double_matrix(InOut.problem.graph.vertexSet().size(), InOut.problem.graph
					.vertexSet().size());

			InOut.time_used = Timer.elapsed_time();
			System.out.println("Initialization took " + InOut.time_used + " seconds\n");

			for (InOut.n_try = 0; InOut.n_try < InOut.max_tries; InOut.n_try++) {

			}
		}

		for (DefaultWeightedEdge edge : InOut.problem.graph.edgeSet()) {
			System.out.println(edge + "--" + InOut.problem.graph.getEdgeWeight(edge));
		}
		System.out.println("shortest_path_length: " + InOut.problem.shortest_path_length);
		System.out.println("shortest_path_edge_list: " + InOut.problem.shortest_path_edge_list);

		System.out.println(Timer.elapsed_time());

	}

}
