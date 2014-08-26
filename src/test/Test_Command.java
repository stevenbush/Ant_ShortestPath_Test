package test;

import java.util.LinkedList;
import java.util.List;

public class Test_Command {

	private static final String TEST_FILE = "tsp/d1291.tsp";

	private static List<String> argList = new LinkedList<String>();

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		argList.add("-quiet");
		argList.add("-i");
		argList.add(TEST_FILE);

		System.out.println(argList);

		Test_Main.main(argList.toArray(new String[0]));
	}

}
