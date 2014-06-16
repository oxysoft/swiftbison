package tools

import java.util.Random

public class RNG {

	private final static Random rand = new Random()

	/** Returns a random number between 0 and 2<font size="-1"><sup>32</sup></font>.*/
	public static int next() {
		return rand.nextInt()
	}

	/** Returns a random number between 0 (inclusive) and the specified value (inclusive). */
	public static int next(final int range) {
		return rand.nextInt(range + 1)
	}

	/** Returns a random number between start (inclusive) and end (inclusive). */
	public static int next(final int start, final int end) {
		return start + rand.nextInt(end - start + 1)
	}

	/** Returns a random boolean value. */
	public static boolean nextBoolean() {
		return rand.nextBoolean()
	}

	/** Returns random number between 0.0 (inclusive) and 1.0 (exclusive). */
	public static float nextFloat() {
		return rand.nextFloat()
	}

	/** Returns random number between 0.0 (inclusive) and 1.0 (exclusive). */
	public static double nextDouble() {
		return rand.nextDouble()
	}

	/** Returns random number between Long.MIN_VALUE (inclusive) and Long.MAX_VALUE (exclusive). */
	public static long nextLong() {
		return rand.nextLong()
	}

	/** Fills the byte array with random bytes. */
	public static void nextBytes(final byte[] bytes) {
		rand.nextBytes(bytes)
	}
}