package core;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import core.utilities.Graph_Generator;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-26
 * 
 *       This class is used to describe a ShortestPath problem, including some related procedures and computation
 *
 */
public class ShortestPath {

	/** the name of this problem */
	public String name;
	/** The graph of this problem */
	public SimpleDirectedWeightedGraph<Integer, DefaultWeightedEdge> graph;
	/** the length of shortest path */
	public Double shortest_path_length;
	/** the path edge list of shortest path */
	public List<DefaultWeightedEdge> shortest_path_edge_list;
	/** the start vertex */
	public Integer start_vertex;
	/** the end vertex */
	public Integer end_vertex;

	public ShortestPath(String inputfile) {
		super();
		String[] strarray = inputfile.split("/");
		String filename = (strarray[strarray.length - 1]).split("\\.")[0];
		// System.out.println(filename);
		name = filename;
		graph = Graph_Generator.read_graph_from_file(inputfile);
		start_vertex = 0;
		end_vertex = graph.vertexSet().size() - 1;
	}
}
