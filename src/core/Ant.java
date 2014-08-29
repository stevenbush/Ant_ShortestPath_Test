package core;

import java.util.ArrayList;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class is used to represent a ant in ACO algorithm
 *
 */
public class Ant {
	/** the path this Ant has travered */
	public ArrayList<Integer> path;
	/** the vertex this Ant has visited */
	public Boolean[] visited;
	/** the length of the path constructed by this Ant */
	public Double path_length;

	public Ant(Integer vertex_num) {
		super();
		// TODO Auto-generated constructor stub
		path = new ArrayList<>();
		visited = new Boolean[vertex_num];
	}

}
