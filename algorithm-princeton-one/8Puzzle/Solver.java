import java.util.Comparator;

public class Solver {
   
    private class Node {
        public Board current;
        public Node previous;
        public int steps = 0;
        
        public Node(Board board) {
            this(board, null, 0);
        }
        public Node(Board board, Node prev, int step) {
            current = board;
            previous = prev;
            steps = step;
        }
    }
    
    private class NodeComparator implements Comparator<Node> {
        public int compare(Node o1, Node o2) {
           return (o1.steps + o1.current.manhattan()) - (o2.steps + o2.current.manhattan());  
        }
        public boolean equals(Object obj) {
            return false;
        }
    }
    
    private int moves = -1;
    private Iterable<Board> solution;
    
    private Comparator<Node> comparator = new NodeComparator();
    
    private int maxTryTime = 0;
    
    public Solver(Board initial)            // find a solution to the initial board (using the A* algorithm)
    {
       maxTryTime = initial.dimension()*initial.dimension();
       Node initial1 = new Node(initial);
       Node initial2 = new Node(initial.twin());
       Node result1 = null;
       Node result2 = null;
       while (result1 == null && result2 == null) {
           result1 = solve(initial1);
           if (result1!=null) break;
           result2 = solve(initial2);
           maxTryTime *= 2;
       }
       if (result1!=null) {
           moves = -1;
           Stack<Board> trace = new Stack<Board>();
           Node cursor = result1; 
           
           while(cursor!=null) {
              trace.push(cursor.current);
              moves++;
              cursor = cursor.previous;
           }
           solution = trace;
       }
    }
    
    private Node solve(Node initial)
    {
        
        MinPQ<Node> minPQ = new MinPQ<Node>(comparator);
        minPQ.insert(initial);
        while(!minPQ.isEmpty()) {
            
            Node min = minPQ.delMin();
         //   StdOut.println("debug: steps = "+min.steps + "  maxTryTime = " + maxTryTime);
            if (min.current.isGoal()) return min;
            if (min.steps >= maxTryTime) return null;
            for (Board neighbor: min.current.neighbors()) {
                if (min.previous == null || !neighbor.equals(min.previous.current)) {
                    Node newNode = new Node(neighbor, min, min.steps + 1);
                    minPQ.insert(newNode);
                    
                }
            }
        }
        return null;
    }
    public boolean isSolvable()             // is the initial board solvable?
    {
        return solution != null;
    }
    public int moves()                      // min number of moves to solve initial board; -1 if no solution
    {
        return moves;
    }
    public Iterable<Board> solution()       // sequence of boards in a shortest solution; null if no solution
    {
        return solution;
    }
    public static void main(String[] args)  // solve a slider puzzle (given below)
    {
         // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}