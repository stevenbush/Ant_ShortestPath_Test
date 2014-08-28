package core;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;

import core.utilities.InOut;
import core.utilities.Timer;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class is used to control the main procedures of this algorithm
 *
 */
public class AlgControl {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		for (String argument : args) {
			System.out.println(argument);
		}
		Timer.start_timers();

		InOut.init_program(args);

		if (InOut.dijkstra_flag) {
			System.out.println("using dijkstra algorithm");
			DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<Integer, DefaultWeightedEdge>(
					InOut.problem.graph, InOut.problem.start_vertex, InOut.problem.end_vertex);
			InOut.problem.shortest_path_edge_list = dijkstraShortestPath.getPathEdgeList();
			InOut.problem.shortest_path_length = dijkstraShortestPath.getPathLength();
		} else {
			System.out.println("using ACO algorithm");
		}

		System.out.println(InOut.problem.graph);
		for (DefaultWeightedEdge edge : InOut.problem.graph.edgeSet()) {
			System.out.println(edge + "--" + InOut.problem.graph.getEdgeWeight(edge));
		}
		System.out.println(InOut.problem.shortest_path_length);
		System.out.println(InOut.problem.shortest_path_edge_list);

		System.out.println(Timer.elapsed_time());

	}

}
