package core.utilities;

import java.util.Random;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class contains some additional useful procedures
 *
 */
public class Utilities {

	public static final int MAXIMUM_NO_TRIES = 100;

	public static Random random;

	public static int seed;

	/**
	 * compute the average value of an integer array of length max
	 * 
	 * @param values
	 *            pointer to array
	 * @param max
	 *            length of array
	 * @return average
	 */
	public static double mean(int[] values, int max) {
		int j;
		double m;

		m = 0.;
		for (j = 0; j < max; j++) {
			m += (double) values[j];
		}
		m = m / (double) max;
		return m;
	}

	/**
	 * compute the average value of a floating number array of length max
	 * 
	 * @param values
	 *            pointer to array
	 * @param max
	 *            length of array
	 * @return average
	 */
	public static Double meanr(Double[] values, int max) {
		int j;
		double m;

		m = 0.;
		for (j = 0; j < max; j++) {
			m += values[j];
		}
		m = m / (double) max;
		return m;
	}

	/**
	 * compute the standard deviation of an integer array
	 * 
	 * @param values
	 *            pointer to array
	 * @param max
	 *            length of array
	 * @param mean
	 *            mean
	 * @return standard deviation
	 */
	public static Double std_deviation(Double[] values, int max, Double mean) {
		int j;
		double dev = 0.;

		if (max <= 1)
			return 0.;
		for (j = 0; j < max; j++) {
			dev += ((double) values[j] - mean) * ((double) values[j] - mean);
		}
		return Math.sqrt(dev / (double) (max - 1));
	}

	/**
	 * compute the standard deviation of a floating number array
	 * 
	 * @param values
	 *            pointer to array
	 * @param max
	 *            length of array
	 * @param mean
	 *            mean
	 * @return standard deviation
	 */
	public static double std_deviationr(Double[] values, int max, double mean) {
		int j;
		double dev;

		if (max <= 1)
			return 0.;
		dev = 0.;
		for (j = 0; j < max; j++) {
			dev += ((double) values[j] - mean) * ((double) values[j] - mean);
		}
		return Math.sqrt(dev / (double) (max - 1));
	}

	/**
	 * return the minimum value in an integer value
	 * 
	 * @param values
	 *            pointer to array
	 * @param l
	 *            length of array
	 * @return smallest number in the array
	 */
	public static double best_of_vector(Double[] values, int l) {
		double min;

		int k = 0;
		min = values[k];
		for (k = 1; k < l; k++) {
			if (values[k] < min) {
				min = values[k];
			}
		}
		return min;
	}

	/**
	 * return the maximum value in an integer value
	 * 
	 * @param values
	 *            pointer to array
	 * @param l
	 *            length of array
	 * @return largest number in the array
	 */
	public static double worst_of_vector(Double[] values, int l) {
		double max;

		int k = 0;
		max = values[k];
		for (k = 1; k < l; k++) {
			if (values[k] > max) {
				max = values[k];
			}
		}
		return max;
	}

	/**
	 * return the q-quantil of an ordered integer array
	 * 
	 * @param v
	 *            one array
	 * @param q
	 *            desired quantil q
	 * @param l
	 *            length of array
	 * @return q-quantil of array
	 */
	public static double quantil(int v[], double q, int l) {
		int i, j;
		double tmp;

		tmp = q * (double) l;
		if ((double) ((int) tmp) == tmp) {
			i = (int) tmp;
			j = (int) (tmp + 1.);
			return ((double) v[i - 1] + (double) v[j - 1]) / 2.;
		} else {
			i = (int) (tmp + 1.);
			return v[i - 1];
		}
	}

	/**
	 * auxiliary routine for sorting an integer array: elements at position i and j of array are swapped
	 * 
	 * @param v
	 *            array
	 * @param i
	 *            two indices
	 * @param j
	 */
	public static void swap(int v[], int i, int j) {
		int tmp;

		tmp = v[i];
		v[i] = v[j];
		v[j] = tmp;
	}

	/**
	 * recursive routine (quicksort) for sorting an array
	 * 
	 * @param v
	 *            ne array
	 * @param left
	 *            two indices
	 * @param right
	 */
	public static void sort(int v[], int left, int right) {
		int k, last;

		if (left >= right)
			return;
		swap(v, left, (left + right) / 2);
		last = left;
		for (k = left + 1; k <= right; k++)
			if (v[k] < v[left])
				swap(v, ++last, k);
		swap(v, left, last);
		sort(v, left, last);
		sort(v, last + 1, right);
	}

	/**
	 * auxiliary routine for sorting an integer array
	 * 
	 * @param v
	 *            two array
	 * @param v2
	 *            two indices
	 * @param i
	 * @param j
	 */
	public static void swap2(int v[], int v2[], int i, int j) {
		int tmp;

		tmp = v[i];
		v[i] = v[j];
		v[j] = tmp;
		tmp = v2[i];
		v2[i] = v2[j];
		v2[j] = tmp;
	}

	/**
	 * FUNCTION: recursive routine (quicksort) for sorting one array; second arrays does the same sequence of swaps
	 * 
	 * @param v
	 *            two arrays, two indices
	 * @param v2
	 * @param left
	 * @param right
	 */
	public static void sort2(int v[], int v2[], int left, int right) {
		int k, last;

		if (left >= right)
			return;
		swap2(v, v2, left, (left + right) / 2);
		last = left;
		for (k = left + 1; k <= right; k++)
			if (v[k] < v[left])
				swap2(v, v2, ++last, k);
		swap2(v, v2, left, last);
		sort2(v, v2, left, last);
		sort2(v, v2, last + 1, right);
	}

	/**
	 * generate a random number that is uniformly distributed in [0,1]
	 * 
	 * @param idum
	 * @return
	 */
	public static double ran01(long idum) {
		if (random == null) {
			random = new Random(seed);
		}

		return random.nextDouble();
	}

	/**
	 * generate an integer random number
	 * 
	 * @param idum
	 * @return
	 */
	public static int random_number(long idum) {
		if (random == null) {
			random = new Random(seed);
		}

		return random.nextInt(2147483647);
	}

	/**
	 * malloc a matrix and return pointer to it
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static int[][] generate_int_matrix(int n, int m) {
		return new int[n][m];
	}

	/**
	 * malloc a matrix and return pointer to it
	 * 
	 * @param n
	 * @param m
	 * @return
	 */
	public static Double[][] generate_double_matrix(int n, int m)
	/*
	 * FUNCTION: malloc a matrix and return pointer to it INPUT: size of matrix as n x m OUTPUT: pointer to matrix
	 * (SIDE)EFFECTS:
	 */
	{
		return new Double[n][m];
	}

	public static int aw_best_tour_index() {
		Double min;

		Double[] values = InOut.best_in_try;
		int l = InOut.max_tries;

		Integer k = 0;
		min = values[k];
		int minIndex = 0;
		for (k = 1; k < l; k++) {
			if (values[k] < min) {
				min = values[k];
				minIndex = k;
			}
		}
		return minIndex;
	}
}
