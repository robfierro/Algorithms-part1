// Algorithms Part 1 Programming Assignment: Percolation
// Roberto Fierro


import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    
    private int n;
    private int trials;
    private int max;
    private double[] thresholds;
    
    public PercolationStats(int n, int trials) {
        
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException("Illegal arguments n:"+n+" and/or trials:"+trials);
        }
        
        this.n = n;
        this.trials = trials;
        this.max = this.n * this.n;
        this.thresholds = new double[this.trials];
        
        
        for (int i = 0; i < this.trials; i++) {
            this.percolationTest(i);
        }
    }
    
    private void percolationTest(int index) {
        
        Percolation per = new Percolation(this.n);
        
        while (!per.percolates()) {
            int row = StdRandom.uniform(1, this.n + 1);
            int column = StdRandom.uniform(1, this.n + 1);
            per.open(row, column);
        }
        this.thresholds[index] = (double)per.numberOfOpenSites()/(double)this.max;
    }
    
    public double mean() {
        
        return StdStats.mean(this.thresholds);
    }
    
    public double stddev() {
        
        return StdStats.stddev(this.thresholds);
    }
    
    public double confidenceLo() {
        
        return this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.trials));
    }
    
    public double confidenceHi() {
        
        return this.mean() + ((1.96 * this.stddev()) / Math.sqrt(this.trials));
    }
    
    public static void main(String[] args) {
        
        if ( args.length != 2 ) {
            System.out.println("Try: PercolationStats n trials");
            System.out.println("Where n and trials have to be integers greater than zero");
            return;
        }
        
        int n = 0;
        int trials = 0;
         
        try {
            n = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("n:"+args[0]+" is not a valid integer");
            return;
        }
        
        try {
            trials = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.out.println("trials:"+args[1]+" is not a valid integer");
            return;
        }
        
        PercolationStats perStats = new PercolationStats(n, trials);
        System.out.println("mean ="+ perStats.mean());
        System.out.println("stddev ="+ perStats.stddev());
        System.out.println("95% confidence low interval ="+perStats.confidenceLo());
        System.out.println("95% confidence high interval ="+perStats.confidenceHi());
    }

}