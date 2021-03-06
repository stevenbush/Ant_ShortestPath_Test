package test;

import java.net.MalformedURLException;
import java.net.URL;

import org.jgrapht.DirectedGraph;
import org.jgrapht.Graphs;
import org.jgrapht.UndirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class HelloJGraphT {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		UndirectedGraph<String, DefaultEdge> stringGraph = createStringGraph();

		// note undirected edges are printed as: {<v1>,<v2>}
		System.out.println(stringGraph.toString());

		UndirectedGraph<String, DefaultEdge> stringGraph1 = createStringGraph1();

		// note undirected edges are printed as: {<v1>,<v2>}
		System.out.println(stringGraph1.toString());

		// create a graph based on URL objects
		DirectedGraph<URL, DefaultEdge> hrefGraph = createHrefGraph();

		// note directed edges are printed as: (<v1>,<v2>)
		System.out.println(hrefGraph.toString());

		// create a graph based on URL objects
		DirectedGraph<URL, DefaultEdge> hrefGraph1 = createHrefGraph1();

		// note directed edges are printed as: (<v1>,<v2>)
		System.out.println(hrefGraph1.toString());
	}

	/**
	 * Creates a toy directed graph based on URL objects that represents link structure.
	 *
	 * @return a graph based on URL objects.
	 */
	private static DirectedGraph<URL, DefaultEdge> createHrefGraph() {
		DirectedGraph<URL, DefaultEdge> g = new DefaultDirectedGraph<URL, DefaultEdge>(DefaultEdge.class);

		try {
			URL amazon = new URL("http://www.amazon.com");
			URL yahoo = new URL("http://www.yahoo.com");
			URL ebay = new URL("http://www.ebay.com");

			// add the vertices
			g.addVertex(amazon);
			g.addVertex(yahoo);
			g.addVertex(ebay);

			// add edges to create linking structure
			g.addEdge(yahoo, amazon);
			g.addEdge(yahoo, ebay);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return g;
	}

	/**
	 * Craete a toy graph based on String objects.
	 *
	 * @return a graph based on String objects.
	 */
	private static UndirectedGraph<String, DefaultEdge> createStringGraph() {
		UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";

		// add the vertices
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);
		g.addVertex(v4);

		// add edges to create a circuit
		g.addEdge(v1, v2);
		g.addEdge(v2, v3);
		g.addEdge(v3, v4);
		g.addEdge(v4, v1);

		return g;
	}

	/**
	 * Creates a toy directed graph based on URL objects that represents link structure.
	 *
	 * @return a graph based on URL objects.
	 */
	private static DirectedGraph<URL, DefaultEdge> createHrefGraph1() {
		DirectedGraph<URL, DefaultEdge> g = new DefaultDirectedGraph<URL, DefaultEdge>(DefaultEdge.class);

		try {
			URL amazon = new URL("http://www.amazon.com");
			URL yahoo = new URL("http://www.yahoo.com");
			URL ebay = new URL("http://www.ebay.com");

			Graphs.addEdgeWithVertices(g, yahoo, amazon);
			Graphs.addEdgeWithVertices(g, yahoo, ebay);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return g;
	}

	/**
	 * Craete a toy graph based on String objects.
	 *
	 * @return a graph based on String objects.
	 */
	private static UndirectedGraph<String, DefaultEdge> createStringGraph1() {
		UndirectedGraph<String, DefaultEdge> g = new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);

		String v1 = "v1";
		String v2 = "v2";
		String v3 = "v3";
		String v4 = "v4";

		Graphs.addEdgeWithVertices(g, v1, v2);
		Graphs.addEdgeWithVertices(g, v2, v3);
		Graphs.addEdgeWithVertices(g, v3, v4);
		Graphs.addEdgeWithVertices(g, v4, v1);

		return g;
	}

}
