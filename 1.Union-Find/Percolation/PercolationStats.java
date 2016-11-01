import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
	private double[] myArray;
	private double myMean;
	private double mySD;
	private double tmp;
	private Percolation[] percolations;

	public PercolationStats(int n, int trials) {
		if (n <= 0 || trials <= 0)
			throw new IllegalArgumentException("n or trials is an illegal argument");
		myArray = new double[trials];
		percolations = new Percolation[trials];
		for (int i = 0; i < trials; i++)
			percolations[i] = new Percolation(n);
	}

	public double mean() {
		myMean = StdStats.mean(myArray);
		return myMean;
	}

	public double stddev() {
		mySD = StdStats.stddev(myArray);
		return mySD;
	}

	public double confidenceLo() {
		tmp = 1.96 * mySD / Math.sqrt(myArray.length);
		return myMean - tmp;
	}

	public double confidenceHi() {
		return myMean + tmp;
	}

	public static void main(String[] args) {
		int n = 200;
		int myT = 100;
		n = Integer.parseInt(args[0]);
		myT = Integer.parseInt(args[1]);
		PercolationStats percolationStats = new PercolationStats(n, myT);
		double pThreshold;
		int fraction;
		for (int i = 0; i < myT; i++) {
			fraction = 0;
			while (true) {
				int row = StdRandom.uniform(n) + 1;
				int col = StdRandom.uniform(n) + 1;
				if (!percolationStats.percolations[i].isOpen(row, col)) {
					fraction++;
					percolationStats.percolations[i].open(row, col);
					if (percolationStats.percolations[i].percolates()) {
						pThreshold = fraction / Math.pow(n, 2);
						break;
					}
				}
			}
			percolationStats.myArray[i] = pThreshold;
		}

		System.out.println("mean                    = " + percolationStats.mean());
		System.out.println("stddev                  = " + percolationStats.stddev());
		System.out.println("95% confidence interval = " + percolationStats.confidenceLo() + ", "
				+ percolationStats.confidenceHi());
	}
}