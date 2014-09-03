package core.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import core.ShortestPath;

public class Graph_Utilities {

	/**
	 * compute the length of a path
	 * 
	 * @param graph
	 *            the graph
	 * @param path
	 *            the path
	 * @return
	 */
	public static double compute_path_length(SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph,
			ArrayList<Integer> path) {
		double length = 0.0;

		for (int i = 0; i < path.size() - 1; i++) {
			length = length + graph.getEdgeWeight(graph.getEdge(path.get(i), path.get(i + 1)));
		}

		return length;
	}

	/**
	 * Find all the paths between the start and end vertex in a graph
	 * 
	 * @param graph
	 *            the graph
	 * @param start_vertex
	 *            the start vertex
	 * @param end_vertex
	 *            the end vertex
	 * @return
	 */
	public static ArrayList<ArrayList<Integer>> find_all_path(
			SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph, Integer start_vertex, Integer end_vertex) {
		ArrayList<ArrayList<Integer>> all_paths = new ArrayList<>();
		HashMap<Integer, List<Integer>> successor_list = new HashMap<>();

		// System.out.println("start_vertex: " + start_vertex);
		// System.out.println("end_vertex: " + end_vertex);

		for (Integer vertex : graph.vertexSet()) {
			successor_list.put(vertex, Graphs.successorListOf(graph, vertex));
		}

		Stack<Integer> stack = new Stack<>();
		stack.push(start_vertex);
		while (!stack.empty()) {
			Integer current_vertex = stack.peek();
			if (current_vertex.intValue() == end_vertex.intValue()) {
				// System.out.println("stack: " + stack);
				ArrayList<Integer> path = new ArrayList<>();
				for (Integer vertex : stack) {
					path.add(vertex);
				}
				all_paths.add(path);
				stack.pop();
			} else {
				if (successor_list.get(current_vertex).isEmpty()) {
					stack.pop();
					successor_list.put(current_vertex, Graphs.successorListOf(graph, current_vertex));
				} else {
					Integer successor = successor_list.get(current_vertex).get(0);
					successor_list.get(current_vertex).remove(0);
					stack.push(successor);
				}
			}
		}

		return all_paths;
	}

	/**
	 * Calculate the average path length of a graph
	 * 
	 * @param problem
	 *            the problem instance
	 * @return
	 */
	public static double average_path_length(ShortestPath problem) {
		ArrayList<ArrayList<Integer>> all_path = find_all_path(problem.graph, problem.start_vertex, problem.end_vertex);
		double sum = 0.0;
		for (ArrayList<Integer> path : all_path) {
			for (int i = 0; i < path.size() - 1; i++) {
				sum = sum + problem.graph.getEdgeWeight(problem.graph.getEdge(i, i + 1));
			}
		}
		return sum / all_path.size();
	}

}
