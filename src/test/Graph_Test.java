package test;

import java.awt.List;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.jgrapht.Graphs;
import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

public class Graph_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> sdaGraph = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);
		sdaGraph = DAG_Generator(6, 8);
		System.out.println(sdaGraph);
		for (DefaultWeightedEdge edge : sdaGraph.edgeSet()) {
			System.out.println(edge + "--" + sdaGraph.getEdgeWeight(edge));
		}
		DijkstraShortestPath<Integer, DefaultWeightedEdge> dijkstraShortestPath = new DijkstraShortestPath<Integer, DefaultWeightedEdge>(
				sdaGraph, 0, 7);
		System.out.println(dijkstraShortestPath.getPathLength());
		System.out.println(dijkstraShortestPath.getPathEdgeList());

		write_graph_to_file(sdaGraph, 1);
		read_graph_from_file("v8_e11_i1.csv");
	}

	public static void write_graph_to_file(SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph,
			Integer graph_id) {
		Integer v_number = graph.vertexSet().size();
		Integer e_number = graph.edgeSet().size();
		String file_name = "v" + v_number + "_e" + e_number + "_i" + graph_id;
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file_name + ".csv"), CSVWriter.DEFAULT_SEPARATOR,
					CSVWriter.NO_QUOTE_CHARACTER);

			for (DefaultWeightedEdge edge : graph.edgeSet()) {
				String[] entries = new String[3];
				entries[0] = String.valueOf(graph.getEdgeSource(edge));
				entries[1] = String.valueOf(graph.getEdgeTarget(edge));
				entries[2] = String.valueOf(graph.getEdgeWeight(edge));
				writer.writeNext(entries);
			}

			writer.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> read_graph_from_file(String filename) {
		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		try {
			CSVReader reader = new CSVReader(new FileReader(filename));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				System.out.println(nextLine[0] + "-" + nextLine[1] + "-" + nextLine[2]);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return graph;
	}

	public static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> DAG_Generator(Integer V_Number,
			Integer E_Number) {
		if (E_Number > (V_Number * (V_Number - 1)) / 2) {
			throw new IllegalArgumentException("Too many edges");
		}
		if (E_Number < V_Number - 1) {
			throw new IllegalArgumentException("Too little edges");
		}

		Integer startVertex = 0;
		Integer endVertex = 0 + V_Number + 1;

		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> sdaGraph = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		UniformIntegerDistribution vertexdistribution = new UniformIntegerDistribution(1, V_Number);
		UniformIntegerDistribution weightDistribution = new UniformIntegerDistribution(1, 10);
		while (sdaGraph.edgeSet().size() < E_Number) {
			Integer source = vertexdistribution.sample();
			Integer target = vertexdistribution.sample();
			Double weight = weightDistribution.sample() * 1.0;

			if ((source < target) && !sdaGraph.containsEdge(source, target)) {
				Graphs.addEdgeWithVertices(sdaGraph, source, target, weight);
			}
		}

		ArrayList<Integer> startvertexs = new ArrayList<>();
		ArrayList<Integer> endvertexs = new ArrayList<>();

		for (Integer vertex : sdaGraph.vertexSet()) {
			if (sdaGraph.inDegreeOf(vertex) == 0) {
				startvertexs.add(vertex);
			}
			if (sdaGraph.outDegreeOf(vertex) == 0) {
				endvertexs.add(vertex);
			}
		}

		System.out.println("startvertexs: " + startvertexs);
		System.out.println("endvertexs: " + endvertexs);

		for (Integer vertexindex : startvertexs) {
			Double weight = weightDistribution.sample() * 1.0;
			Graphs.addEdgeWithVertices(sdaGraph, startVertex, vertexindex, weight);
		}

		for (Integer vertexindex : endvertexs) {
			Double weight = weightDistribution.sample() * 1.0;
			Graphs.addEdgeWithVertices(sdaGraph, vertexindex, endVertex, weight);
		}

		return sdaGraph;
	}
}
