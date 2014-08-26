package test;
import org.apache.commons.math3.distribution.EnumeratedIntegerDistribution;

import core.utilities.Parse;

public class Distribution_Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] singletons = { 2, 4, 6, 8, 10 };
		double[] probabilities = { 0.6, 0.1, 0.1, 0.1, 0.1 };
		EnumeratedIntegerDistribution distribution = new EnumeratedIntegerDistribution(singletons, probabilities);
		
		for (int i = 0; i < 20; i++) {
			System.out.println(distribution.sample());
		}
	}

}
