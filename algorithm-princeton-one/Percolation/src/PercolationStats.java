import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class PercolationStats {

	private int t;
	private double[] x;
	private double mean = -1;
	private double stddev = -1;
	private double confLo = -1;
	private double confHi = -1;
	
    public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
    {
    	if (N <= 0|| T <= 0)
    	{
    		throw new IllegalArgumentException();
    	}
    		
	   t = T;
	   x = new double[T];
	   for (int i=0; i<T; i++)
	   {
		   x[i] = this.getProb(N);
	   }
	   
    }
    public double mean()                     // sample mean of percolation threshold
    {    	
    	mean = StdStats.mean(x);
    	return mean;
    }
    public double stddev()                   // sample standard deviation of percolation threshold
    {
	   stddev = StdStats.stddev(x);
	   return stddev;
    }
    public double confidenceLo()             // returns lower bound of the 95% confidence interval
    {
	   if(stddev<0) stddev();
	   confLo = mean -  1.96 * stddev / Math.sqrt(t);
	   return confLo;
    }
    public double confidenceHi()             // returns upper bound of the 95% confidence interval
    {
 	   if(stddev<0) stddev();
 	   confHi = mean + 1.96 * stddev / Math.sqrt(t);
 	   return confHi;	   
    }
    
    private double getProb(int N)
    {
    	Percolation percolation = new Percolation(N);
    	int count = 0;
    	while(!percolation.percolates())
    	{
    		int i = StdRandom.uniform(N) + 1;
    		int j = StdRandom.uniform(N) + 1;
    		
    		if (!percolation.isOpen(i, j))
    		{
    			percolation.open(i, j);
    			count ++;
    		}  		
    	}
    	return ((double)count)/((double)(N*N));
    }
    
    public static void main(String[] args)   // test client, described below
    {
    	
	   int N = Integer.parseInt(args[0]);
	   int T = Integer.parseInt(args[1]);
	   PercolationStats perco = new PercolationStats(N,T);
	   StdOut.println("mean                    = " + perco.mean());
	   StdOut.println("stddev                  = " + perco.stddev());
	   StdOut.println("95% confidence interval = " + perco.confidenceLo() + " " + perco.confidenceHi());	   
    }
}
