package test;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import core.AlgControl;
import core.utilities.Parse;

public class Test_Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("ant------------------------");
		String command = "-z -r 3 -t 5 -i /Users/jiyuanshi/Downloads/SimpleDAG/v5002_e26491_i0.csv";
		List<String> argList = new LinkedList<String>();
		String[] strarray = command.split(" ");
		for (int i = 0; i < strarray.length; i++) {
			argList.add(strarray[i]);
		}
		System.out.println(argList);
		AlgControl.main(strarray);

		System.out.println();
		System.out.println();

		System.out.println("dij------------------------");
		command = "-dij -i /Users/jiyuanshi/Downloads/SimpleDAG/v5002_e26491_i0.csv";
		argList.clear();
		strarray = command.split(" ");
		for (int i = 0; i < strarray.length; i++) {
			argList.add(strarray[i]);
		}
		System.out.println(argList);
		AlgControl.main(strarray);
	}
}
