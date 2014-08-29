package core;

import org.jgrapht.Graphs;

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
	/** importance of trail */
	public static Double alpha;
	/** importance of heuristic evaluate */
	public static Double beta;
	/** probability of best choice in tour construction */
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
	public static Integer elitist_ants;
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

}
