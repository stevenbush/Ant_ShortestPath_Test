/**
 * 
 */
package core.utilities;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.math3.distribution.UniformIntegerDistribution;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class is used for graph generation and related operation
 *
 */
public class Graph_Generator {

	/**
	 * This function is used to construct a new simple directed weighted graph containing specified vertex number and
	 * edge number
	 * 
	 * @param V_Number
	 *            the number of vertex
	 * @param E_Number
	 *            the number of edge
	 * @return a simple directed weighted graph
	 */
	public static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> Generate_DAG(Integer V_Number,
			Integer E_Number) {
		if (E_Number > (V_Number * (V_Number - 1)) / 2) {
			throw new IllegalArgumentException("Too many edges");
		}
		if (E_Number < V_Number - 1) {
			throw new IllegalArgumentException("Too little edges");
		}

		Integer startVertex = 0;
		Integer endVertex = 0 + V_Number + 1;

		// System.out.println("startVertex: " + startVertex);
		// System.out.println("endVertex: " + endVertex);

		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> sdaGraph = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		UniformIntegerDistribution vertexdistribution = new UniformIntegerDistribution(1, V_Number);
		UniformIntegerDistribution weightDistribution = new UniformIntegerDistribution(1, 10);

		while (sdaGraph.edgeSet().size() < E_Number || sdaGraph.vertexSet().size() < V_Number) {

			// random generate the edge of graph
			Integer source = vertexdistribution.sample();
			Integer target = vertexdistribution.sample();
			Double weight = weightDistribution.sample() * 1.0;

			if ((source < target) && !sdaGraph.containsEdge(source, target)) {
				Graphs.addEdgeWithVertices(sdaGraph, source, target, weight);
			}
		}

		ArrayList<Integer> startvertexs = new ArrayList<>();
		ArrayList<Integer> endvertexs = new ArrayList<>();

		// find all the node with 0 indegree and 0 outdegree
		for (Integer vertex : sdaGraph.vertexSet()) {
			if (sdaGraph.inDegreeOf(vertex) == 0) {
				startvertexs.add(vertex);
			}
			if (sdaGraph.outDegreeOf(vertex) == 0) {
				endvertexs.add(vertex);
			}
		}

		// System.out.println("startvertexs: " + startvertexs);
		// System.out.println("endvertexs: " + endvertexs);

		// a the a start and end vertex to this graph
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

	/**
	 * write the DAG in to file in CSV format
	 * 
	 * @param graph
	 *            the graph need to be written
	 * @param graph_id
	 *            the id go this graph
	 * @param file_path
	 *            the path to store these files
	 */
	public static void write_graph_to_file(SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph,
			Integer graph_id, String file_path) {
		Integer v_number = graph.vertexSet().size();
		Integer e_number = graph.edgeSet().size();
		String file_name = "v" + v_number + "_e" + e_number + "_i" + graph_id;
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(file_path + file_name + ".csv"),
					CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER);

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

	/**
	 * read graph from the file
	 * 
	 * @param filename
	 *            the file name
	 * @return a simple directed weighted graph
	 */
	public static SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> read_graph_from_file(String filename) {
		SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph = new SimpleDirectedWeightedGraph<>(
				DefaultWeightedEdge.class);

		// read the graph from the file
		try {
			CSVReader reader = new CSVReader(new FileReader(filename));
			String[] nextLine;
			while ((nextLine = reader.readNext()) != null) {
				Integer source = Integer.valueOf(nextLine[0]);
				Integer target = Integer.valueOf(nextLine[1]);
				Double weight = Double.valueOf(nextLine[2]);
				Graphs.addEdgeWithVertices(graph, source, target, weight);
			}

			reader.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return graph;
	}

	/**
	 * Generate the specified number of DAG graph with specified vertex number and edge number
	 * 
	 * @param DGA_Number
	 *            the specified number of DAGs
	 * @param V_Number
	 *            the specified number of vertex number
	 * @param E_number
	 *            the specified number of edge number
	 * @param file_path
	 *            the path to store these files
	 */
	public static void DAG_Files_Generator(Integer DAG_Number, Integer V_Number, Integer E_number, String file_path) {
		for (int i = 0; i < DAG_Number; i++) {
			write_graph_to_file(Generate_DAG(V_Number, E_number), i, file_path);
		}
	}

}
