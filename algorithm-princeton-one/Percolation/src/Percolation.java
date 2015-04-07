
public class Percolation 
{
	private boolean[][] grid;
	private int size;
	private WeightedQuickUnionUF uf;
	private WeightedQuickUnionUF uf2;
	
	private int topSite;
	private int bottomSite;
	
	public Percolation(int N)              // create N-by-N grid, with all sites blocked
	{

    	if (N <= 0)
    	{
    		throw new IllegalArgumentException();
    	}
    	
		size = N;
		grid = new boolean[N][N];
		uf = new WeightedQuickUnionUF(N*N+2);
		topSite = 0;
		bottomSite = N*N+1;
		uf2 = new WeightedQuickUnionUF(N*N+2);
	}
	public void open(int i, int j) throws IndexOutOfBoundsException         // open site (row i, column j) if it is not already
	{
		
		if (i < 1 || i > size || j < 1 || j > size)
			throw new IndexOutOfBoundsException();
		
		if (grid[i-1][j-1])
			return;
		
		
		grid[i-1][j-1] = true;
		
		
		int current = convert2OneDimension(i,j);
		if (i == 1) 
		{
		   uf.union(topSite, current);	
		   uf2.union(topSite, current);	
		   
		}
        if (i == size)
        {
        	uf.union(bottomSite, current);
        }
		if (i>1 && this.isOpen(i-1, j)) 
		{
			int up = this.convert2OneDimension(i-1, j);
			uf.union(current, up);
			uf2.union(current, up);
		}
		if (i<size && this.isOpen(i+1, j))
		{
			int down = this.convert2OneDimension(i+1, j);
			uf.union(current, down);
			uf2.union(current, down);
			
		}

		if (j>1 && this.isOpen(i, j-1)) 
		{
			int left = this.convert2OneDimension(i, j-1);
			uf.union(current, left);
			uf2.union(current, left);
			
		}
		if (j<size && this.isOpen(i, j+1))
		{
			int right = this.convert2OneDimension(i, j+1);
			uf.union(current, right);
			uf2.union(current, right);
			
		}
	}
	public boolean isOpen(int i, int j) throws IndexOutOfBoundsException    // is site (row i, column j) open?
	{
		if (i < 1 || i > size || j < 1 || j > size)
			throw new IndexOutOfBoundsException();

		return grid[i-1][j-1];
	}
	public boolean isFull(int i, int j) throws IndexOutOfBoundsException     // is site (row i, column j) full?
	{
		if (i < 1 || i > size || j < 1 || j > size)
			throw new IndexOutOfBoundsException();
		int current = convert2OneDimension(i,j);
		return uf2.connected(topSite, current);
	}
	public boolean percolates()            // does the system percolate?
	{
		return uf.connected(topSite, bottomSite);
	}
	
	private int convert2OneDimension(int i, int j)  // i, j => 1~N
	{
		return (i-1)*size + j;
	}
	
}
