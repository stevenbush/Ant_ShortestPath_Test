package core.utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import core.Ants;
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

	public static final String PROG_ID_STR = "Algorithms for the TSP";
	public static Double[] best_in_try;
	public static Double[] best_found_at;
	public static Double[] time_best_found;
	public static Double[] time_total_run;
	public static String[] aw_best_path_in_try;

	/** the flag to represent whether to use dijkstra algorithm */
	public static Boolean dijkstra_flag;

	/** the path and name of input DAG file */
	public static String filename;

	/** the problem instance of shortestpath */
	public static ShortestPath problem;
	/** --quiet was given in the command-line. */
	public static boolean quiet_flag;
	/** maximal allowed run time of a try */
	public static Double max_time;
	/** maximum number of independent tries */
	public static Integer max_tries;
	/** maximum number of solution constructions in one try */
	public static Integer max_solutions;
	/** optimal solution or bound to find */
	public static Double optimal;
	/** iteration counter */
	public static Integer iteration;
	/** remember iteration when restart was done if any */
	public static int restart_iteration;
	/** remember time when restart was done if any */
	public static double restart_time;
	/** try counter */
	public static Integer n_try;
	/** counter of number constructed solutions */
	public static Integer n_solutions;
	/** time used until some given event */
	public static double time_used;
	/** time passed until some moment */
	public static double time_passed;
	/** Parameter to determine branching factor */
	// public static double lambda;
	/** If branching factor < branch_fac => update trails */
	// public static double branch_fac;

	/** average path length */
	public static double mean_ants;
	/** stddev of path lengths */
	public static double stddev_ants;
	/** average node branching factor when searching */
	// public static double branching_factor;
	/** branching factor when best solution is found */
	// public static double found_branching;
	/** iteration in which best solution is found */
	public static Double found_best;

	/* ------------------------------------------------------------------------ */
	public static File report, comp_report, stat_report;
	private static Map<String, BufferedWriter> writer = new HashMap<String, BufferedWriter>();

	/**
	 * output some info about trial (best-so-far solution quality, time)
	 */
	public static void write_report() {
		System.out.println("best " + Ants.best_so_far_ant.path_length + ", iteration: " + iteration + ", time "
				+ Timer.elapsed_time());
		if (comp_report != null) {
			printToFile(comp_report, "best " + Ants.best_so_far_ant.path_length + "\t iteration " + iteration
					+ "\t solutions" + n_solutions + "\t time " + time_used);
		}
	}

	/**
	 * write parameter to file
	 * 
	 * @param file
	 *            the file object
	 */
	public static void fprintf_parameters(File file) {
		printToFile(file, "max_tries\t\t " + max_tries);
		printToFile(file, "max_solutions\t\t " + max_solutions);
		printToFile(file, "max_time\t\t " + max_time);
		printToFile(file, "random seed\t\t " + Utilities.seed);
		printToFile(file, "optimum\t\t\t " + optimal);
		printToFile(file, "n_ants\t\t\t " + Ants.n_ants);
		printToFile(file, "Ants.alpha\t\t " + Ants.alpha);
		printToFile(file, "Ants.beta\t\t " + Ants.beta);
		printToFile(file, "Ants.rho\t\t " + Ants.rho);
		printToFile(file, "Ants.q_0\t\t " + Ants.q_0);
		printToFile(file, "Ants.elitist_ants\t " + Ants.elitist_ants);
		printToFile(file, "Ants.ras_ranks\t\t " + Ants.ras_ranks);
		printToFile(file, "Ants.as_flag\t\t " + Ants.as_flag);
		printToFile(file, "Ants.eAnts.as_flag\t " + Ants.eas_flag);
		printToFile(file, "rAnts.as_flag\t\t " + Ants.ras_flag);
		printToFile(file, "Ants.bwAnts.as_flag\t " + Ants.bwas_flag);
		printToFile(file, "Ants.acs_flag\t\t " + Ants.acs_flag);
	}

	/**
	 * output default parameter settings
	 */
	public static void print_default_parameters() {
		System.err.println("\nDefault parameter settings are:\n\n");
		fprintf_parameters(null);
	}

	/**
	 * write a string to a file
	 * 
	 * @param file
	 *            the file to be written
	 * @param string
	 *            the string to write
	 */
	public static void printToFile(File file, String string) {
		if (file == null) {
			System.out.println(string);
		} else {
			try {
				writer.get(file.getName()).write(string + "\n");
			} catch (IOException e) {
				System.err.print("Could not write file " + file.getName() + " " + e.getMessage());
				System.exit(1);
			}
		}
	}

	/**
	 * set the default parameters of as
	 */
	public static void set_default_as_parameters() {
		assert (Ants.as_flag);
		Ants.n_ants = -1; /* number of ants (-1 means problem instance size) */
		Ants.alpha = 1.0;
		Ants.beta = 2.0;
		Ants.rho = 0.5;
		Ants.q_0 = 0.0;
		Ants.ras_ranks = 0;
		Ants.elitist_ants = 0;
	}

	/**
	 * set the default parameters of eas
	 */
	public static void set_default_eas_parameters() {
		assert (Ants.eas_flag);
		Ants.n_ants = -1; /* number of ants (-1 means problem instance size) */
		Ants.alpha = 1.0;
		Ants.beta = 2.0;
		Ants.rho = 0.5;
		Ants.q_0 = 0.0;
		Ants.ras_ranks = 0;
		Ants.elitist_ants = Ants.n_ants;
	}

	/**
	 * set the default parameters of ras
	 */
	public static void set_default_ras_parameters() {
		assert (Ants.ras_flag);
		Ants.n_ants = -1; /* number of ants (-1 means problem instance size) */
		Ants.alpha = 1.0;
		Ants.beta = 2.0;
		Ants.rho = 0.1;
		Ants.q_0 = 0.0;
		Ants.ras_ranks = 6;
		Ants.elitist_ants = 0;
	}

	/**
	 * set the default parameters of bwas
	 */
	public static void set_default_bwas_parameters() {
		assert (Ants.bwas_flag);
		Ants.n_ants = -1; /* number of ants (-1 means problem instance size) */
		Ants.alpha = 1.0;
		Ants.beta = 2.0;
		Ants.rho = 0.1;
		Ants.q_0 = 0.0;
		Ants.ras_ranks = 0;
		Ants.elitist_ants = 0;
	}

	/**
	 * set the default parameters of acs
	 */
	public static void set_default_acs_parameters() {
		assert (Ants.acs_flag);
		Ants.n_ants = 10; /* number of ants (-1 means problem instance size) */
		Ants.alpha = 1.0;
		Ants.beta = 2.0;
		Ants.rho = 0.1;
		Ants.q_0 = 0.9;
		Ants.ras_ranks = 0;
		Ants.elitist_ants = 0;
	}

	/**
	 * set default parameter settings
	 */
	static void set_default_parameters() {
		Ants.n_ants = 25; /* number of ants */
		Ants.alpha = 1.0;
		Ants.beta = 2.0;
		Ants.rho = 0.5;
		Ants.q_0 = 0.0;
		max_tries = 10;
		max_solutions = 0;
		Utilities.seed = (int) System.currentTimeMillis();
		max_time = 10.0;
		optimal = 1.0;
		// branch_fac = 1.00001;
		Ants.u_gb = Integer.MAX_VALUE;
		Ants.as_flag = false;
		Ants.eas_flag = false;
		Ants.ras_flag = false;
		Ants.bwas_flag = false;
		Ants.acs_flag = false;
		Ants.ras_ranks = 0;
		Ants.elitist_ants = 0;
		dijkstra_flag = false;
	}

	/**
	 * compute some population statistics like average path length, standard deviations, average distance,
	 * branching-factor and output to a file gathering statistics
	 */
	public static void population_statistics() {
		Integer k;
		Double[] l;
		double pop_mean, pop_stddev;

		l = new Double[Ants.n_ants];
		for (k = 0; k < Ants.n_ants; k++) {
			l[k] = Ants.ants[k].path_length;
		}

		pop_mean = Utilities.meanr(l, Ants.n_ants);
		pop_stddev = Utilities.std_deviation(l, Ants.n_ants, pop_mean);

		if (stat_report != null) {
			printToFile(stat_report, iteration + "\t" + pop_mean + "\t" + pop_stddev + "\t" + (pop_stddev / pop_mean));
		}
	}

	/**
	 * output a solution containing the edges of the path
	 */
	public static void output_solution() {
		if (stat_report != null) {
			for (int i = 0; i < Ants.best_so_far_ant.path.size(); i++) {
				printToFile(stat_report, Ants.best_so_far_ant.path.get(i).toString());
			}
		}
	}

	/**
	 * save some statistical information on a trial once it finishes
	 * 
	 * @param ntry
	 *            trial number
	 */
	public static void exit_try(int ntry) {
		System.out.println("Best Solution in try " + ntry + " is " + Ants.best_so_far_ant.path_length);

		if (report != null)
			printToFile(report, "Best: " + Ants.best_so_far_ant.path_length + "\t Iterations: " + found_best
					+ "\t Time " + time_used + "\t Tot.time " + Timer.elapsed_time());
		System.out.println(" Best Solution was found after " + found_best + " iterations\n");

		best_in_try[ntry] = Ants.best_so_far_ant.path_length;
		best_found_at[ntry] = found_best;
		time_best_found[ntry] = time_used;
		time_total_run[ntry] = Timer.elapsed_time();
		aw_best_path_in_try[ntry] = Ants.best_so_far_ant.path.toString();

		System.out.println("\ntry " + ntry + ", Best " + best_in_try[ntry] + ", found at iteration "
				+ best_found_at[ntry] + ", found at time " + time_best_found[ntry] + "\n");

		if (comp_report != null)
			printToFile(comp_report, "end try " + ntry + "\n");
		if (stat_report != null)
			printToFile(stat_report, "end try " + ntry + "\n");
	}

	/**
	 * save some final statistical information on a trial once it finishes
	 */
	public static void exit_program() {
		double best_path_length, worst_path_length;
		double t_avgbest, t_stdbest, t_avgtotal, t_stdtotal;
		double avg_sol_quality = 0., avg_cyc_to_bst = 0., stddev_best, stddev_iterations;

		best_path_length = Utilities.best_of_vector(best_in_try, max_tries);
		worst_path_length = Utilities.worst_of_vector(best_in_try, max_tries);

		avg_cyc_to_bst = Utilities.meanr(best_found_at, max_tries);
		stddev_iterations = Utilities.std_deviation(best_found_at, max_tries, avg_cyc_to_bst);

		avg_sol_quality = Utilities.meanr(best_in_try, max_tries);
		stddev_best = Utilities.std_deviation(best_in_try, max_tries, avg_sol_quality);

		t_avgbest = Utilities.meanr(time_best_found, max_tries);
		System.out.println(" t_avgbest = " + t_avgbest);
		t_stdbest = Utilities.std_deviationr(time_best_found, max_tries, t_avgbest);

		t_avgtotal = Utilities.meanr(time_total_run, max_tries);
		System.out.println(" t_avgtotal = " + t_avgtotal);
		t_stdtotal = Utilities.std_deviationr(time_total_run, max_tries, t_avgtotal);

		if (report != null) {
			printToFile(report, "\nAverage-Best: " + avg_sol_quality + "\t Average-Iterations: " + avg_cyc_to_bst);
			printToFile(report, "Stddev-Best: " + stddev_best + " \t Stddev Iterations: " + stddev_iterations);
			printToFile(report, "Best try: " + best_path_length + "\t\t Worst try: " + worst_path_length);
			printToFile(report, "\nAvg.time-best: " + t_avgbest + " stddev.time-best: " + t_stdbest);
			printToFile(report, "\nAvg.time-Ants.total: " + t_avgtotal + " stddev.time-Ants.total: " + t_stdtotal);

			if (optimal > 0) {
				printToFile(report, " excess best = " + ((double) (best_path_length - optimal) / (double) optimal)
						+ ", excess average = " + ((double) (avg_sol_quality - optimal) / (double) optimal) + ","
						+ " excess worst = " + ((double) (worst_path_length - optimal) / (double) optimal));
			}
		}

		if (comp_report != null)
			printToFile(comp_report, "end problem " + problem.name);

		for (String key : writer.keySet()) {
			try {
				writer.get(key).close();
			} catch (IOException e) {
				System.err.println("Could not close file " + key + " " + e.getMessage());
			}
		}
	}

	/**
	 * initialize the program
	 * 
	 * @param args
	 *            program arguments
	 */
	public static void init_program(String[] args) {

		String temp_buffer;

		set_default_parameters();
		Parse.parse_commandline(args);

		problem = new ShortestPath(filename);

		assert (max_tries <= Utilities.MAXIMUM_NO_TRIES);

		best_in_try = new Double[max_tries];
		best_found_at = new Double[max_tries];
		time_best_found = new Double[max_tries];
		time_total_run = new Double[max_tries];

		aw_best_path_in_try = new String[max_tries];

		if (Ants.n_ants < 0) {
			Ants.n_ants = problem.graph.vertexSet().size();
		}

		/*
		 * default setting for Ants.elitist_ants is 0; if EAS is applied and option Ants.elitist_ants is not used, we
		 * set the default to Ants.elitist_ants = n
		 */
		if (Ants.eas_flag && Ants.elitist_ants <= 0) {
			Ants.elitist_ants = problem.graph.vertexSet().size();
		}

		assert (Ants.n_ants < Ants.MAX_ANTS - 1);

		if (!quiet_flag) {
			Writer w;
			try {
				temp_buffer = "best." + problem.name;
				// // TRACE ( System.out.println("%s\n",temp_buffer); )
				report = new File(temp_buffer);
				w = new OutputStreamWriter(new FileOutputStream(temp_buffer), "UTF8");
				writer.put(report.getName(), new BufferedWriter(w));

				temp_buffer = "cmp." + problem.name;
				// // TRACE ( System.out.println("%s\n",temp_buffer); )
				comp_report = new File(temp_buffer);
				w = new OutputStreamWriter(new FileOutputStream(temp_buffer), "UTF8");
				writer.put(comp_report.getName(), new BufferedWriter(w));

				temp_buffer = "stat." + problem.name;
				// // TRACE ( System.out.println("%s\n",temp_buffer); )
				stat_report = new File(temp_buffer);
				w = new OutputStreamWriter(new FileOutputStream(temp_buffer), "UTF8");
				writer.put(stat_report.getName(), new BufferedWriter(w));
			} catch (IOException e) {
				System.err.println("Could not write file. " + e.getMessage());
				System.exit(1);
			}
		} else {
			report = null;
			comp_report = null;
			stat_report = null;
		}

		write_params();

		if (comp_report != null)
			printToFile(comp_report, "begin problem " + problem.name);
		System.out.println("allocate ants' memory ..");
		Ants.allocate_ants();
		System.out.println(" .. done\n");
	}

	/**
	 * writes chosen parameter settings in standard output and in report files
	 */
	public static void write_params() {
		System.out.println("Parameter-settings:\n");
		fprintf_parameters(null);
		System.out.println("\n");

		if (report != null) {
			printToFile(report, "Parameter-settings: \n\n");
			fprintf_parameters(report);
			printToFile(report, "\n");
		}

		if (comp_report != null) {
			printToFile(comp_report, PROG_ID_STR);
			printToFile(comp_report, "Parameter-settings: \n\n");
			fprintf_parameters(comp_report);
			printToFile(comp_report, "\n");
		}
	}

}
