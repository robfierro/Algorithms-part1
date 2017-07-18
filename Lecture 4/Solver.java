
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.Stack;

public final class Solver {
    
    private SearchNode lastNode;
    private Stack<Board> solutionTree;
    private final int moves;
    private final boolean isSolvable;
    
    private class SearchNode implements Comparable<SearchNode> {
    
        private final Board board;
        private SearchNode previous;
        private int moves = 0;
        
        public SearchNode(Board board) {
        
            this.board = board;
        }
        
        public SearchNode(Board board, SearchNode previous) {
        
            this.board = board;
            this.previous = previous;
            this.moves = previous.moves + 1;
        }
        
        public int compareTo(SearchNode sn) {
            return (this.board.manhattan() - sn.board.manhattan()) + (this.moves - sn.moves);
        }
    }
    
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
    
        
        if (initial == null) {
        
            throw new IllegalArgumentException("Null board");
        }
        
        if (initial.isGoal()) {
        
            lastNode = new SearchNode(initial);
        }
        
        MinPQ<SearchNode> minPQ = new MinPQ<SearchNode>();
        minPQ.insert(new SearchNode(initial));
        
        MinPQ<SearchNode> minPQSolvable = new MinPQ<SearchNode>();
        minPQSolvable.insert(new SearchNode(initial.twin()));
        
        while (true) {
        
            lastNode = searchGoal(minPQ);
            
            if (lastNode != null || searchGoal(minPQSolvable) != null) 
                break;
        }
        
        this.isSolvable = lastNode != null;
        
        this.moves = this.isSolvable ? lastNode.moves : -1;
        
    }
    
    private SearchNode searchGoal(MinPQ<SearchNode> queue) {
    
        if (queue.isEmpty()) return null;
        SearchNode minNode = queue.delMin();
        if (minNode.board.isGoal()) return minNode;
        
        for (Board n : minNode.board.neighbors()) {
        
            if (minNode.previous == null || !n.equals(minNode.previous.board)) {
                
                queue.insert(new SearchNode(n, minNode));
            }
        }
        
        return null;
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        
        return this.isSolvable;
    }
    
    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        
        return this.moves;
    }
    
    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
    
        if (!this.isSolvable()) return null;
        
        if (solutionTree != null) return this.solutionTree;
        
        this.solutionTree = new Stack<Board>();
        while (lastNode != null) {
            solutionTree.push(lastNode.board);
            lastNode = lastNode.previous;
        }
        
        return this.solutionTree;
    }
    
    // solve a slider puzzle (given below)
    public static void main(String[] args) {
    
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
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


