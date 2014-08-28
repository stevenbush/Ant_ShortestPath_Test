package core;

import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-26
 * 
 *       This class is used to describe a ShortestPath problem, including some related procedures and computation
 *
 */
public class ShortestPath {

	/** The graph of this problem */
	SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> graph;

	public ShortestPath(String inputfile) {
		super();
		graph = read_graph_from_file(inputfile);
	}
	
	public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> read_graph_from_file(String file_name) {
		SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> tmp_graph = new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		
		return tmp_graph;
	}
}
