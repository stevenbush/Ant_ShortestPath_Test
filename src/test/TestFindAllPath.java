package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javax.management.Query;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import core.utilities.Graph_Generator;

public class TestFindAllPath {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String filepath = "/Users/jiyuanshi/Downloads/SimpleDAG/v6_e8_i1.csv";
		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = Graph_Generator
				.read_graph_from_file(filepath);
		System.out.println(graph);
		DepthFirstIterator<Integer, DefaultWeightedEdge> iterator = new DepthFirstIterator<Integer, DefaultWeightedEdge>(
				graph, 0);
		System.out.println("DepthFirst--------");
		while (iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		System.out.println("BreadthFirst--------");
		BreadthFirstIterator<Integer, DefaultWeightedEdge> breadthFirstIterator = new BreadthFirstIterator<Integer, DefaultWeightedEdge>(
				graph, 0);
		while (breadthFirstIterator.hasNext()) {
			System.out.println(breadthFirstIterator.next());
		}
		System.out.println("DepthFirst--------");
		DepthFirst(graph);
		System.out.println("BreadthFirst--------");
		BreadthFirst(graph);
	}

	public static ArrayList<ArrayList<Integer>> find_all_path(
			SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		ArrayList<ArrayList<Integer>> all_paths = new ArrayList<>();
		boolean[] visitedflag = new boolean[graph.vertexSet().size()];
		Stack<Integer> stack = new Stack<>();
		System.out.println(0);
		visitedflag[0] = true;
		stack.push(0);
		while (!stack.isEmpty()) {
			Integer current_vertex = stack.peek();
			List<Integer> successorList = Graphs.successorListOf(graph, current_vertex);
			boolean flag = false;
			for (Integer successor : successorList) {
				if (!visitedflag[successor]) {
					System.out.println(successor);
					visitedflag[successor] = true;
					stack.push(successor);
					flag = true;
					break;
				}
			}
			if (!flag) {
				stack.pop();
			}
		}

		return all_paths;
	}

	public static ArrayList<ArrayList<Integer>> BreadthFirst(
			SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		ArrayList<ArrayList<Integer>> all_paths = new ArrayList<>();
		boolean[] visitedflag = new boolean[graph.vertexSet().size()];
		Queue<Integer> queue = new LinkedList<>();
		System.out.println(0);
		visitedflag[0] = true;
		queue.add(0);
		while (!queue.isEmpty()) {
			Integer current_vertex = queue.poll();
			List<Integer> successorList = Graphs.successorListOf(graph, current_vertex);
			for (Integer successor : successorList) {
				if (!visitedflag[successor]) {
					System.out.println(successor);
					visitedflag[successor] = true;
					queue.add(successor);
				}
			}
		}

		return all_paths;
	}

	public static ArrayList<ArrayList<Integer>> DepthFirst(
			SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph) {
		ArrayList<ArrayList<Integer>> all_paths = new ArrayList<>();
		boolean[] visitedflag = new boolean[graph.vertexSet().size()];
		Stack<Integer> stack = new Stack<>();
		stack.push(0);
		while (!stack.isEmpty()) {
			Integer current_vertex = stack.pop();
			if (!visitedflag[current_vertex]) {
				System.out.println(current_vertex);
				visitedflag[current_vertex] = true;
			}
			List<Integer> successorList = Graphs.successorListOf(graph, current_vertex);
			for (Integer successor : successorList) {
				if (!visitedflag[successor]) {
					stack.push(successor);
				}
			}
		}

		return all_paths;
	}
}
