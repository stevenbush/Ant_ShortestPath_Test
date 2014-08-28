package core.utilities;

import core.ShortestPath;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class's purpose is mainly input / output / statistic routines Checking
 *
 */
public class InOut {

	/** the flag to represent whether to use dijkstra algorithm */
	public static Boolean dijkstra_flag;

	/** the path and name of input DAG file */
	public static String filename;

	/** the problem instance of shortestpath */
	public static ShortestPath problem;

	/**
	 * set default parameter settings
	 */
	static void set_default_parameters() {
		dijkstra_flag = false;
	}

	/**
	 * initialize the program
	 * 
	 * @param args
	 *            program arguments
	 */
	public static void init_program(String[] args) {
		set_default_parameters();
		Parse.parse_commandline(args);
		problem = new ShortestPath(filename);

	}

}
