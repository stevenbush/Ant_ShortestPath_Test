package core;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import core.utilities.Graph_Utilities;
import core.utilities.InOut;
import core.utilities.Timer;
import core.utilities.Utilities;

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
	 * occasionally compute some statistics and check whether algorithm is converged
	 */
	public static void search_control_and_statistics() {
		if ((InOut.iteration % 100) == 0) {
			InOut.population_statistics();
			System.out.println("best so far " + Ants.best_so_far_ant.path_length + ", iteration: " + InOut.iteration
					+ ", time " + Timer.elapsed_time());

			System.out.println("try " + InOut.n_try + " iteration " + InOut.iteration);
		}
	}

	/**
	 * manage global Ants.pheromone deposit for Ant System
	 */
	public static void as_update() {
		for (int i = 0; i < Ants.n_ants; i++) {
			Ants.global_update_pheromone(Ants.ants[i]);
		}
	}

	/**
	 * manage global Ants.pheromone deposit for Elitist Ant System
	 */
	public static void eas_update() {
		for (int i = 0; i < Ants.n_ants; i++) {
			Ants.global_update_pheromone(Ants.ants[i]);
		}
		Ants.global_update_pheromone_weighted(Ants.best_so_far_ant, Ants.elitist_ants);
	}

	/**
	 * manage global Ants.pheromone deposit for Rank-based Ant
	 */
	public static void ras_update() {
		double k, b;
		int target;
		double[] help_b;
		help_b = new double[Ants.n_ants];
		for (int i = 0; i < Ants.n_ants; i++) {
			help_b[i] = Ants.ants[i].path_length;
		}

		for (int i = 0; i < Ants.ras_ranks - 1; i++) {
			b = help_b[0];
			target = 0;
			for (int j = 0; j < Ants.n_ants; j++) {
				if (help_b[j] < b) {
					b = help_b[j];
					target = j;
				}
			}
			help_b[target] = Double.MAX_VALUE;
			Ants.global_update_pheromone_weighted(Ants.ants[target], Ants.ras_ranks - i - 1);
		}
		Ants.global_update_pheromone_weighted(Ants.best_so_far_ant, Ants.ras_ranks);
	}

	/**
	 * manage global Ants.pheromone deposit for Ant Colony System. COMMENTS: global Ants.pheromone deposit in ACS is
	 * done per default using the best-so-far ant; Gambardella & Dorigo examined also iteration-best update (see their
	 * IEEE Trans. on Evolutionary Computation article), but did not use it for the published computational results.
	 */
	public static void acs_global_update() {

		Ants.global_acs_pheromone_update(Ants.best_so_far_ant);
	}

	/**
	 * manage global Ants.pheromone trail update for the ACO algorithms.
	 */
	public static void pheromone_trail_update() {

		/*
		 * Simulate the Ants.pheromone evaporation of all Ants.pheromones; this is not necessary for ACS (see also ACO
		 * Book)
		 */
		if (Ants.as_flag || Ants.eas_flag || Ants.ras_flag || Ants.bwas_flag) {
			Ants.evaporation();
		}

		/* Next, apply the Ants.pheromone deposit for the various ACO algorithms */
		if (Ants.acs_flag) {
			acs_global_update();
		}

		Ants.compute_total_information();

	}

	/**
	 * manage some statistical information about the trial, especially if a new best solution (best-so-far or
	 * restart-best) is found and adjust some parameters if a new best solution is found
	 */
	public static void update_statistics() {
		int iteration_best_ant;

		iteration_best_ant = Ants.find_best();

		if (Ants.ants[iteration_best_ant].path_length < Ants.best_so_far_ant.path_length) {

			InOut.time_used = Timer.elapsed_time(); // best solituon found after time_used
			Ants.copy_from_to(Ants.ants[iteration_best_ant], Ants.best_so_far_ant);
			Ants.copy_from_to(Ants.ants[iteration_best_ant], Ants.restart_best_ant);

			InOut.found_best = InOut.iteration;
			InOut.write_report();
		}

	}

	/**
	 * manage the solution construction phase
	 */
	public static void construct_solutions() {

		/* Mark all nodes as unvisited */
		for (int k = 0; k < Ants.n_ants; k++) {
			Ants.ant_empty_memory(Ants.ants[k]);
		}

		/* place the ants on the start node */
		for (int k = 0; k < Ants.n_ants; k++) {
			Ants.place_ant(Ants.ants[k], InOut.problem.start_vertex);
		}

		for (int k = 0; k < Ants.n_ants; k++) {
			while (Ants.ants[k].visited[InOut.problem.end_vertex] != true) {
				Ants.neighbour_choose_and_move_to_next(Ants.ants[k]);
				if (Ants.acs_flag) {
					Ants.local_acs_pheromone_update(Ants.ants[k]);
				}

			}
		}

		for (int i = 0; i < Ants.n_ants; i++) {
			Ants.ants[i].path_length = Graph_Utilities.compute_path_length(InOut.problem.graph, Ants.ants[i].path);
		}

		InOut.n_solutions = InOut.n_solutions + Ants.n_ants;

	}

	public static boolean termination_condition()
	/*
	 * FUNCTION: checks whether termination condition is met INPUT: none OUTPUT: 0 if condition is not met, number neq 0
	 * otherwise (SIDE)EFFECTS: none
	 */
	{
		return (((InOut.n_solutions >= InOut.max_solutions) && (Timer.elapsed_time() >= InOut.max_time)) || (Ants.best_so_far_ant.path_length <= InOut.optimal));
	}

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
			Ants.trail_0 = 1. / ((Ants.rho) * Graph_Utilities.average_path_length(InOut.problem));
			/*
			 * in the original papers on Ant System, Elitist Ant System, and Rank-based Ant System it is not exactly
			 * defined what the initial value of the Ants.pheromones is. Here we set it to some small constant,
			 * analogously as done in MAX-MIN Ant System.
			 */
			Ants.init_pheromone_trails(Ants.trail_0);
		}
		if (Ants.bwas_flag) {
			Ants.trail_0 = 1. / ((double) InOut.problem.graph.vertexSet().size() * (double) Graph_Utilities
					.average_path_length(InOut.problem));
			Ants.init_pheromone_trails(Ants.trail_0);
		}
		if (Ants.acs_flag) {
			Ants.trail_0 = 1. / Graph_Utilities.average_path_length(InOut.problem);
			Ants.init_pheromone_trails(Ants.trail_0);
		}

		/* Calculate combined information Ants.pheromone times heuristic information */
		Ants.compute_total_information();

		if (InOut.comp_report != null)
			InOut.printToFile(InOut.comp_report, "begin try " + ntry);
		if (InOut.stat_report != null)
			InOut.printToFile(InOut.stat_report, "begin try " + ntry);
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

			// the initialize operation
			Ants.pheromone = Utilities.generate_double_matrix(InOut.problem.graph.vertexSet().size(),
					InOut.problem.graph.vertexSet().size());
			Ants.total = Utilities.generate_double_matrix(InOut.problem.graph.vertexSet().size(), InOut.problem.graph
					.vertexSet().size());

			InOut.time_used = Timer.elapsed_time();
			System.out.println("Initialization took " + InOut.time_used + " seconds\n");

			// performing the ant operation loop
			for (InOut.n_try = 0; InOut.n_try < InOut.max_tries; InOut.n_try++) {

				init_try(InOut.n_try);

				while (!termination_condition()) {
					construct_solutions();

					update_statistics();

					pheromone_trail_update();

					search_control_and_statistics();

					InOut.iteration++;
				}
				InOut.exit_try(InOut.n_try);
			}
			InOut.exit_program();

			InOut.problem.shortest_path_length = Utilities.best_of_vector(InOut.best_in_try, InOut.max_tries);
			String aw_best_path = InOut.aw_best_path_in_try[Utilities.aw_best_tour_index()];
			for (int i = 0; i < aw_best_path.length() - 2; i++) {
				InOut.problem.shortest_path_edge_list.add(InOut.problem.graph.getEdge(
						Integer.valueOf(aw_best_path.charAt(i)), Integer.valueOf(aw_best_path.charAt(i + 1))));
			}

			try {
				Writer w = new OutputStreamWriter(new FileOutputStream("path." + InOut.problem.name), "UTF8");
				BufferedWriter out = new BufferedWriter(w);
				out.write(InOut.problem.shortest_path_length + "\n");
				out.write(aw_best_path);
				out.close();
			} catch (IOException e) {
				System.err.print("Could not write file tour." + InOut.problem.name + " " + e.getMessage());
				System.exit(1);
			}
			System.out.println();
			System.out.println("Best tour:");
			System.out.println(InOut.problem.shortest_path_length);
			System.out.println(aw_best_path);
		}

		for (DefaultWeightedEdge edge : InOut.problem.graph.edgeSet()) {
			System.out.println(edge + "--" + InOut.problem.graph.getEdgeWeight(edge));
		}
		System.out.println("shortest_path_length: " + InOut.problem.shortest_path_length);
		System.out.println("shortest_path_edge_list: " + InOut.problem.shortest_path_edge_list);

		System.out.println(Timer.elapsed_time());

	}
}
