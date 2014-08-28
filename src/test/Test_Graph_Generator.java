package test;

import org.jgrapht.alg.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import core.utilities.Graph_Generator;

public class Test_Graph_Generator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Graph_Generator.DAG_Files_Generator(10, 6, 8, "/Users/jiyuanshi/Downloads/SimpleDAG/");
		Graph_Generator.DAG_Files_Generator(10, 4, 6, "/Users/jiyuanshi/Downloads/SimpleDAG/");
	}

}
