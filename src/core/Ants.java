package core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;
import org.apache.commons.math3.distribution.UniformRealDistribution;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import core.utilities.InOut;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class is used to implement procedures for ants' behaviour
 *
 */

public class Ants {

	/** the default max number of ants */
	public static final Integer MAX_ANTS = 1024;

	/** the array store all the ants */
	public static Ant ants[];
	public static Ant best_so_far_ant;
	public static Ant restart_best_ant;

	/** the matrix of pheromone */
	public static Double pheromone[][];
	/** the matrix of pheromine multiply by heuristic information */
	public static Double total[][];

	/** the number of ants */
	public static Integer n_ants;

	/** parameter for evaporation */
	public static Double rho;
	/** parameter for local evaporation, which is used for ACO alforithm's local pheromone trail update */
	public static Double lrho;
	/** importance of trail */
	public static Double alpha;
	/** importance of heuristic evaluate */
	public static Double beta;
	/** probability of best choice in path construction */
	public static Double q_0;
	/** ant system */
	public static Boolean as_flag;
	/** elitist ant system */
	public static Boolean eas_flag;
	/** rank-based version of ant system */
	public static Boolean ras_flag;
	/** best-worst ant system */
	public static Boolean bwas_flag;
	/** ant colony system */
	public static Boolean acs_flag;

	/** additional parameter for elitist ant system, no. elitist ants */
	public static double elitist_ants;
	/** additional parameter for rank-based version of ant system */
	public static Integer ras_ranks;

	/** every u_gb iterations update with best-so-far ant */
	public static Integer u_gb;

	/** initial pheromone level in ACS and BWAS */
	public static Double trail_0;

	public static final Double EPSILON = 0.00000000000000000000000000000001;

	/** calulation the heuristic information of edge */
	public static Double HEURISTIC(Integer source, Integer target) {
		return (1.0 / InOut.problem.graph.getEdgeWeight(InOut.problem.graph.getEdge(source, target)));
	}

	/**
	 * allocate the memory for the ant colony, the best-so-far and the iteration best ant INPUT: none OUTPUT: none
	 * (SIDE)EFFECTS: allocation of memory for the ant colony and two ants that store intermediate tours
	 */
	public static void allocate_ants() {
		Integer vertex_num = InOut.problem.graph.vertexSet().size();
		ants = new Ant[n_ants];
		for (int i = 0; i < n_ants; i++) {
			ants[i] = new Ant(vertex_num);
		}
		best_so_far_ant = new Ant(vertex_num);
		restart_best_ant = new Ant(vertex_num);

	}

	/**
	 * initialize pheromone trails
	 * 
	 * @param initial_trail
	 *            initial value of pheromone trails "initial_trail"
	 */
	public static void init_pheromone_trails(double initial_trail) {

		/* Initialize pheromone trails */
		for (DefaultWeightedEdge edge : InOut.problem.graph.edgeSet()) {
			int source = InOut.problem.graph.getEdgeSource(edge);
			int target = InOut.problem.graph.getEdgeTarget(edge);
			pheromone[source][target] = initial_trail;
			total[source][target] = initial_trail;
		}
	}

	/**
	 * calculates heuristic info times pheromone for each arc
	 */
	public static void compute_total_information() {
		for (DefaultWeightedEdge edge : InOut.problem.graph.edgeSet()) {
			int source = InOut.problem.graph.getEdgeSource(edge);
			int target = InOut.problem.graph.getEdgeTarget(edge);
			total[source][target] = Math.pow(pheromone[source][target], alpha)
					* Math.pow(HEURISTIC(source, target), beta);
		}
	}

	/**
	 * empty the ants's memory regarding visited nodes
	 * 
	 * @param a
	 *            ant identifier
	 */
	public static void ant_empty_memory(Ant a) {
		int i;

		for (i = 0; i < InOut.problem.graph.vertexSet().size(); i++) {
			a.visited[i] = false;
		}
		a.path_length = Double.MAX_VALUE;
		a.path.clear();
	}

	/**
	 * place an ant on a specified node
	 * 
	 * @param a
	 *            pointer to ant
	 * @param node
	 *            the specified node
	 */
	public static void place_ant(Ant a, int node) {
		a.path.add(node);
		a.visited[node] = true;
	}

	/**
	 * chooses for an ant as the next node the one with maximal value of heuristic information times pheromone
	 * 
	 * @param a
	 *            pointer to ant
	 */
	public static void choose_best_next(Ant a) {
		int current_node, next_node;
		double value_best, help;

		next_node = InOut.problem.end_vertex;
		current_node = a.path.get(a.path.size() - 1);
		assert (InOut.problem.start_vertex <= current_node && current_node < InOut.problem.end_vertex);
		value_best = -1;
		List<Integer> successor_list = Graphs.successorListOf(InOut.problem.graph, current_node);
		for (Integer successor : successor_list) {
			if (a.visited[successor]) {
				; // node already visited, do nothing
			} else {
				help = total[current_node][successor];
				if (help > value_best) {
					value_best = help;
					next_node = successor;
				}
			}
		}
		assert (value_best > 0.0);
		assert (a.visited[next_node] == false);
		a.path.add(next_node);
		a.visited[next_node] = true;
	}

	/**
	 * Choose for an ant probabilistically a next node among all unvisited node in the current node's successor node
	 * list
	 * 
	 * @param a
	 *            pointer to the ant
	 */
	public static void neighbour_choose_and_move_to_next(Ant a) {
		UniformRealDistribution uniformRealDistribution = new UniformRealDistribution();

		if ((q_0 > 0.0) && (uniformRealDistribution.sample() > q_0)) {
			choose_best_next(a);
		} else {
			int current_node = a.path.get(a.path.size() - 1);
			assert (InOut.problem.start_vertex <= current_node && current_node < InOut.problem.end_vertex);
			List<Integer> successor_list = Graphs.successorListOf(InOut.problem.graph, current_node);
			ArrayList<Integer> non_visited_successors = new ArrayList<>();
			for (Integer successor : successor_list) {
				if (!a.visited[successor]) {
					non_visited_successors.add(successor);
				}
			}
			int[] successor_nodes = new int[non_visited_successors.size()];
			for (int i = 0; i < successor_nodes.length; i++) {
				successor_nodes[i] = non_visited_successors.get(i);
			}
			double[] probabilities = new double[successor_list.size()];
			for (int i = 0; i < probabilities.length; i++) {
				probabilities[i] = total[current_node][successor_nodes[i]];
			}
			EnumeratedIntegerDistribution enumeratedIntegerDistribution = new EnumeratedIntegerDistribution(
					successor_nodes, probabilities);
			int next_node = enumeratedIntegerDistribution.sample();
			assert (next_node >= 0 && next_node < InOut.problem.end_vertex);
			assert (a.visited[next_node] == false);
			a.path.add(next_node);
			a.visited[next_node] = true;
		}
	}

	/**
	 * find the best ant of the current iteration
	 * 
	 * @return
	 */
	public static int find_best() {

		double min;
		int i_min;
		min = ants[0].path_length;
		i_min = 0;
		for (int i = 1; i < n_ants; i++) {
			if (ants[i].path_length < min) {
				min = ants[i].path_length;
				i_min = i;
			}
		}

		return i_min;
	}

	/**
	 * copy solution from ant a1 into ant a2
	 * 
	 * @param a1
	 *            pointer to ant a1
	 * @param a2
	 *            pointer to ant a2
	 */
	public static void copy_from_to(Ant a1, Ant a2) {
		a2.path_length = a1.path_length;
		a2.path.clear();
		for (Integer node : a1.path) {
			a2.path.add(node);
		}
	}

	/**
	 * implements the pheromone trail evaporation
	 */
	public static void evaporation() {
		for (DefaultWeightedEdge edge : InOut.problem.graph.edgeSet()) {
			int source = InOut.problem.graph.getEdgeSource(edge);
			int target = InOut.problem.graph.getEdgeTarget(edge);

			pheromone[source][target] = (1 - rho) * pheromone[source][target];
		}
	}

	/**
	 * reinforces edges used in ant k's solution
	 * 
	 * @param a
	 *            pointer to the ant that updates the pheromone
	 */
	public static void global_update_pheromone(Ant a) {
		double d_tau = 1.0 / a.path_length;
		for (int i = 0; i < a.path.size() - 2; i++) {
			pheromone[a.path.get(i)][a.path.get(i + 1)] = pheromone[a.path.get(i)][a.path.get(i + 1)] + d_tau;
		}
	}

	/**
	 * reinforces edges of the ant's tour with weight "weight"
	 */
	public static void global_update_pheromone_weighted(Ant a, double weight) {
		double d_tau = weight / a.path_length;
		for (int i = 0; i < a.path.size() - 2; i++) {
			pheromone[a.path.get(i)][a.path.get(i + 1)] = pheromone[a.path.get(i)][a.path.get(i + 1)] + d_tau;
		}
	}

	/**
	 * reinforces the edges used in ant's solution as in ACS
	 * 
	 * @param a
	 *            pointer to ant
	 */
	public static void global_acs_pheromone_update(Ant a) {

		double d_tau = 1.0 / a.path_length;
		for (int i = 0; i < a.path.size() - 2; i++) {
			pheromone[a.path.get(i)][a.path.get(i + 1)] = (1.0 - rho) * pheromone[a.path.get(i)][a.path.get(i + 1)]
					+ rho * d_tau;
			total[a.path.get(i)][a.path.get(i + 1)] = Math.pow(pheromone[a.path.get(i)][a.path.get(i + 1)], alpha)
					* Math.pow(HEURISTIC(a.path.get(i), a.path.get(i + 1)), beta);
		}
	}

	/**
	 * emoves some pheromone on edge just passed by the ant. COMMENT: I did not do experiments with with different
	 * values of the parameter xi for the local pheromone update; therefore, here xi is fixed to 0.1 as suggested by
	 * Gambardella and Dorigo for the TSP. If you wish to run experiments with that parameter it may be reasonable to
	 * use it as a commandline parameter
	 * 
	 * @param a
	 *            pointer to ant
	 */
	public static void local_acs_pheromone_update(Ant a) {

		int pre_node = a.path.get(a.path.size() - 2);
		int cur_node = a.path.get(a.path.size() - 1);

		assert (pre_node >= InOut.problem.start_vertex && pre_node <= InOut.problem.end_vertex);
		assert (cur_node >= InOut.problem.start_vertex && cur_node <= InOut.problem.end_vertex);

		pheromone[pre_node][cur_node] = (1.0 - lrho) * pheromone[pre_node][cur_node] + 0.1 * trail_0;
		total[pre_node][cur_node] = Math.pow(pheromone[pre_node][cur_node], alpha)
				* Math.pow(HEURISTIC(pre_node, cur_node), beta);

	}
}
