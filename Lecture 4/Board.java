import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import java.util.Arrays;

public class Board {
    
    private int[][] blocks;
    private int hamming;
    private int manhattan;
    private int n;
    private int[] matrixArray;
    private int emptySpaceIndex;
    private Queue<Board> neighbors;
    
    // construct a board from an n-by-n array of blocks
    // (where blocks[i][j] = block in row i, column j)
    public Board(int[][] blocks) {
    
        this.blocks = blocks;
        this.hamming = -1;
        this.manhattan = -1;
        this.n = blocks.length;
        this.matrixArray = new int[n * n];
        int count = 0;
        for (int i = 0; i < blocks.length; i++) {
        
            for (int j = 0; j < blocks.length; j++) {
        
                if (blocks[i][j] == 0) {
                
                    this.emptySpaceIndex = count;
                }
                this.matrixArray[count++] = blocks[i][j];
            }
        }
    }
    
    // board dimension n    
    public int dimension() {
    
        return this.n;
    }
    
    // number of blocks out of place
    public int hamming() {
    
        if (this.hamming != -1) {
            
            return this.hamming;
        }
        
        this.hamming = 0;
        for (int i = 1; i <= matrixArray.length; i++) {
        
            if (i != matrixArray[i-1] && matrixArray[i-1] != 0) {
            
                this.hamming++;
            }
        }
        
        return this.hamming;
    }
    
    // sum of Manhattan distances between blocks and goal
    public int manhattan() {
    
        if (this.manhattan != -1) {
            
            return this.manhattan;
        }
        
        this.manhattan = 0;
        for (int i = 0; i < matrixArray.length; i++) { 
        
            if (matrixArray[i] == 0) {
            
                continue;
            }
            this.manhattan += calculateManhattanDistanceForBlock(matrixArray[i], i);
        }
        
        return this.manhattan;
    }
    
    private int calculateManhattanDistanceForBlock(int block, int index) {
    
        int blockX = xCoordinate(index);
        int blockY = yCoordinate(index);
        
        int goalX = xCoordinate(block - 1);
        int goalY = yCoordinate(block - 1);
        
        return Math.abs(blockX - goalX) + Math.abs(blockY - goalY);
    }
    
    private int yCoordinate(int num) {
    
        return num % n;
    }
    
    private int xCoordinate(int num) {
    
        return num/n;
    }
    
    // is this board the goal board?
    public boolean isGoal() {
    
        return manhattan() == 0 && hamming() == 0;
    }
    
    // a board that is obtained by exchanging any pair of blocks
    public Board twin() {
    
        int randomBlock1 = StdRandom.uniform(n*n);
        while (matrixArray[randomBlock1] == 0) {
        
            randomBlock1 = StdRandom.uniform(n*n);
        }
        int randomBlock2 = StdRandom.uniform(n*n);
        
        while (matrixArray[randomBlock2] == 0 || matrixArray[randomBlock2] == matrixArray[randomBlock1]) {
        
            randomBlock2 = StdRandom.uniform(n*n);
        }
        int[] newMatrixArray = Arrays.copyOf(matrixArray, matrixArray.length);
        int temp = newMatrixArray[randomBlock1];
        newMatrixArray[randomBlock1] = newMatrixArray[randomBlock2];
        newMatrixArray[randomBlock2] = temp;
        
        return new Board(arrayToMatrix(newMatrixArray));
    }
    
    private int[][] arrayToMatrix(int[] array) {
        
        int[][] matrix = new int[n][n];
        for (int i = 0; i < n; i++) {
        
            for (int j = 0; j < n; j++) {
                matrix[i][j] = array[i*n + j];
            }
        }
        return matrix;
    }
    
    // does this board equal y?
    public boolean equals(Object y) {
    
        if (y == this) return true;
        if (y == null) return false;
        if (y.getClass() != this.getClass()) return false;
        Board that = (Board)y;
        if (this.dimension() != that.dimension()) return false;
        
        for (int i = 0; i < this.blocks.length; i++) {
        
            for (int j = 0; j < this.blocks.length; j++) {
        
                if (this.blocks[i][j] != that.blocks[i][j]) return false;
            }
        }
        
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors() {
    
        getNeighbors();
        return this.neighbors;
    }
    
    private void getNeighbors() {
    
        this.neighbors = new Queue<Board>();
        int emptyBlockX = xCoordinate(emptySpaceIndex);
        int emptyBlockY = yCoordinate(emptySpaceIndex);
 
        if (canMoveLeft(emptyBlockX)) {
            
            int[] newMatrixArray = Arrays.copyOf(matrixArray, n*n);
            int leftIndex = emptyBlockX - 1 + n*emptyBlockY;  
            addNeighbor(newMatrixArray, leftIndex);
        }
        
        if (canMoveRight(emptyBlockX)) {
            
            int[] newMatrixArray = Arrays.copyOf(matrixArray, n*n);
            int rightIndex = emptyBlockX + 1 + n*emptyBlockY;
            addNeighbor(newMatrixArray, rightIndex);
        }
        
        if (canMoveUp(emptyBlockY)) {
            
            int[] newMatrixArray = Arrays.copyOf(matrixArray, n*n);
            int topIndex = emptyBlockX + n*(emptyBlockY - 1); 
            addNeighbor(newMatrixArray, topIndex);
        }
        
        if (canMoveDown(emptyBlockY)) {
            
            int[] newMatrixArray = Arrays.copyOf(matrixArray, n*n);
            int bottomIndex = emptyBlockX + n*(emptyBlockY + 1); 
            addNeighbor(newMatrixArray, bottomIndex);
        }
    }
    
    private void addNeighbor(int[] array, int exchIndex) {
        int temp = array[emptySpaceIndex];
        array[emptySpaceIndex] = array[exchIndex];
        array[exchIndex] = temp;
        neighbors.enqueue(new Board(arrayToMatrix(array)));
    }
    
    private boolean canMoveLeft(int emptyBlockX) {
        
        if (emptyBlockX - 1 >= 0) {
            
            return true;
        }
            
        return false;
    }
        
    private boolean canMoveRight(int emptyBlockX) {
        
        if (emptyBlockX + 1 < n) {
            
            return true;
        }
            
        return false;
    }
        
    private boolean canMoveUp(int emptyBlockY) {
        
        if (emptyBlockY - 1 >= 0) {
            
            return true;
        }
            
        return false;
    }
        
    private boolean canMoveDown(int emptyBlockY) {
        
        if (emptyBlockY + 1 < n) {
            
            return true;
        }
            
        return false;
    }
    
    // string representation of this board (in the output format specified below)
    public String toString() {
        
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", this.blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // unit tests (not graded)
    public static void main(String[] args) {
        
        int[][] blocks = new int[3][3];
        blocks[0][0] = 8;
        blocks[0][1] = 1;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 0;
        blocks[1][2] = 2;
        blocks[2][0] = 7;
        blocks[2][1] = 6;
        blocks[2][2] = 5;
        
        Board b = new Board(blocks);
        System.out.println("Board: "+ b);
        System.out.println("Hamming: "+ b.hamming());
        System.out.println("Manhattan: "+ b.manhattan());
        System.out.println("Is Goal?: "+ b.isGoal());
        Board b2 = b.twin();
        System.out.println("Board Twin: "+ b2);
        System.out.println("Hamming: "+ b2.hamming());
        System.out.println("Manhattan: "+ b2.manhattan());
        System.out.println("Is Goal?: "+ b2.isGoal());
        
        for (Board n : b.neighbors()){
        
            System.out.println("Board: "+ n);
        }
        
        blocks[0][0] = 1;
        blocks[0][1] = 2;
        blocks[0][2] = 3;
        blocks[1][0] = 4;
        blocks[1][1] = 5;
        blocks[1][2] = 6;
        blocks[2][0] = 7;
        blocks[2][1] = 8;
        blocks[2][2] = 0;
        
        b = new Board(blocks);
        System.out.println("Board: "+ b);
        System.out.println("Hamming: "+ b.hamming());
        System.out.println("Manhattan: "+ b.manhattan());
        System.out.println("Is Goal?: "+ b.isGoal());
        
        for (Board n : b.neighbors()){
        
            System.out.println("Board: "+ n);
        }
    }
}




