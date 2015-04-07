public class Board {
    private int[][] board;
    private int N;
    private int blankX=0;
    private int blankY=0;

    
    public Board(int[][] blocks)           // construct a board from an N-by-N array of blocks
    {                                      // (where blocks[i][j] = block in row i, column j)
        N = blocks.length;
        board = copy(blocks);
        setBlankPosition();
    }
    private void setBlankPosition()
    {
        if (board == null) return;
        for (blankX=0; blankX<N; blankX++)
            for (blankY=0; blankY<N; blankY++)
            if (board[blankX][blankY] == 0)
            return;
    }
    private int[][] copy(int[][] blocks)
    {
        int[][] temp = new int[N][N];
        for (int i=0;i<N;i++)
            for(int j=0;j<N;j++)
            temp[i][j] = blocks[i][j];
        return temp;
    }
    public int dimension()                 // board dimension N
    {
        return N;
    }
    public int hamming()                   // number of blocks out of place
    {
        int distance = 0;
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
        {
            if (i==N-1 && j== N-1) return distance;
            if (board[i][j] != (i*N + j + 1))
               distance ++;
        }
        return distance;
    }
    public int manhattan()                 // sum of Manhattan distances between blocks and goal
    {
        int distance = 0;
        for (int i=0; i<N; i++)
            for (int j=0; j<N; j++)
        {
            distance += manhattanDistance(i, j, board[i][j]);
        }
        return distance;
    }
    private int manhattanDistance(int i, int j, int val)
    {
        if (val == 0) return 0;
        int x = (val-1)/N;
        int y = (val-1)%N;
        return Math.abs(x-i) + Math.abs(y-j);
    }
    public boolean isGoal()                // is this board the goal board?
    {
        return hamming() == 0;
    }
    public Board twin()                    // a board obtained by exchanging two adjacent blocks in the same row
    {
        int targetX = (blankX + 1)%N;
        int[][] twin =  copy(board);
        twin[targetX][0] = board[targetX][1];
        twin[targetX][1] = board[targetX][0];
        
        return new Board(twin);
    }
    public boolean equals(Object y)        // does this board equal y?
    {
        if (this == y) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        
        Board that = (Board) y;
        
        if (this.dimension() != that.dimension()) return false;
        return this.toString().equals(that.toString());
    }
    public Iterable<Board> neighbors()     // all neighboring boards
    {
        Queue<Board> neighbors = new Queue<Board>();
        if (blankX < N-1) 
        {
            int[][] neighbor =  copy(board);
            neighbor[blankX][blankY] = neighbor[blankX+1][blankY];
            neighbor[blankX+1][blankY] = 0;
            neighbors.enqueue(new Board(neighbor));
        }
        if (blankX > 0) 
        {
            int[][] neighbor =  copy(board);
            neighbor[blankX][blankY] = neighbor[blankX-1][blankY];
            neighbor[blankX-1][blankY] = 0;
            neighbors.enqueue(new Board(neighbor));
        }
        if (blankY < N-1) 
        {
            int[][] neighbor =  copy(board);
            neighbor[blankX][blankY] = neighbor[blankX][blankY+1];
            neighbor[blankX][blankY+1] = 0;
            neighbors.enqueue(new Board(neighbor));
        }
        if (blankY > 0) 
        {
            int[][] neighbor =  copy(board);
            neighbor[blankX][blankY] = neighbor[blankX][blankY-1];
            neighbor[blankX][blankY-1] = 0;
            neighbors.enqueue(new Board(neighbor));
        }
        return neighbors;
    }
    public String toString()               // string representation of the board (in the output format specified below)
    {
        StringBuffer str = new StringBuffer();
        str.append(N + "\n");
        for (int i=0; i<N; i++)
        {
            for (int j=0; j<N; j++)
                str.append(" " + board[i][j] + " ");
           str.append("\n");
        }
        return str.toString();
    }
}