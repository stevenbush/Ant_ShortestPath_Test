package core.utilities;

/**
 * @author jiyuanshi (shi_jiyuan@outlook.com)
 * @version 1.0
 * @date 2014-08-28
 * 
 *       This class contains routines for measuring elapsed time (CPU or real)
 *
 */
public class Timer {

	/** the start time */
	private static long startTime;

	/**
	 * FUNCTION: virtual and real time of day are computed and stored to allow at later time the computation of the
	 * elapsed time (virtual or real) INPUT: none OUTPUT: none (SIDE)EFFECTS: virtual and real time are computed
	 */
	public static void start_timers() {
		startTime = System.currentTimeMillis();
	}

	/**
	 * FUNCTION: return the time used in seconds (virtual or real, depending on type) INPUT: TUtilities.IMER_TYPE
	 * (virtual or real time) OUTPUT: seconds since last call to start_timers (virtual or real) (SIDE)EFFECTS: none
	 * 
	 * @return the elapsed time
	 */
	public static double elapsed_time() {
		return (System.currentTimeMillis() - startTime) / 1000.0;
	}
}
