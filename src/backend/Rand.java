package backend;

public abstract class Rand {
	
	/**
	 * Generates a random int from within the interval provided
	 * @param min; Minimum value INCLUSIVE
	 * @param max; Maximum value EXCLUSIVE
	 * @return Returns the random int
	 */
	public static int generateRandInt(int min, int max){
		int n = max;
		while(n == max)
			n = (int) Math.floor(Math.random()*max);
		return n;
	}
}
