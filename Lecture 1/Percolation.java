// Algorithms Part 1 Programming Assignment: Percolation
// Roberto Fierro


import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
    private WeightedQuickUnionUF wQuickUnionUF;
    private WeightedQuickUnionUF wQuickUnionUFFull;
    private int n;
    private boolean[] nxnGrid;
    private int openedSites;
    private int topSite;
    private int bottomSite;
    
    
    public Percolation(int n) {
        
        if (n <= 0) {
            throw new IllegalArgumentException("n ≤ 0");
        }
        this.n = n;
        this.openedSites = 0;
        // n^2  + top site + bottom site
        int size = n*n + 2;
        
        // init top and bottom sites
        this.topSite = 0;
        this.bottomSite = size - 1;
        
        // Quick Unions
        this.wQuickUnionUF = new WeightedQuickUnionUF(size);
        // Used to check full sites
        this.wQuickUnionUFFull = new WeightedQuickUnionUF(size - 1);
        
        this.nxnGrid = new boolean[size];
        // Set all sites blocked
        for (int i = 1; i <= size - 1; i++) {
            this.nxnGrid[i] = false;
        }
        
        // Set top and bottom sites open 
        this.nxnGrid[this.topSite] = true;
        this.nxnGrid[this.bottomSite] = true;

    }
    
    public void open(int row, int col) {
    
        if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
            
            throw new IndexOutOfBoundsException("row:"+ row +" or/and col:"+ col +" out of bounds n:"+ this.n);
        }
        
        if (this.isOpen(row, col)) {
            
            return;
        }
        // Translate xy coordinates to array index
        int index = this.xyCoordinateToIndex(row, col);
        
        // Open the nxn grid at index
        this.nxnGrid[index] = true;
        
        // Connect to adjacent sites 
        
        if (!this.isRowTopEdge(row) && this.isOpen(row - 1, col)) {
            
            int q = this.xyCoordinateToIndex(row - 1, col);
            this.union(index, q);
        }
        
        if (!this.isRowBottomEdge(row) && this.isOpen(row + 1, col)) {
            
            int q = this.xyCoordinateToIndex(row + 1, col);
            this.union(index, q);
        }
        
        if (!this.isColumnLeftEdge(col) && this.isOpen(row, col - 1)) {
            
            int q = this.xyCoordinateToIndex(row, col - 1);
            this.union(index, q);
        }
        
        if (!this.isColumnRightEdge(col) && this.isOpen(row, col + 1)) {
            
            int q = this.xyCoordinateToIndex(row, col + 1);
            this.union(index, q);
        }
        
        // Check if opened site is on the top, if so connect it to topSite
        if (index <= this.n) {
            
            this.union(this.topSite, index);
        }
        
        if (index >= (this.n - 1) * this.n + 1) {
            
            this.wQuickUnionUF.union(this.bottomSite, index);
        }
        
        this.openedSites++;
    }
    
    public boolean isOpen(int row, int col) {
    
        if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
            
            throw new IndexOutOfBoundsException("row:"+ row +" or/and col:"+ col +" out of bounds n:"+ this.n);
        }
        // Translate xy coordinates to array index
        int index = this.xyCoordinateToIndex(row, col);
        return this.nxnGrid[index];
    }
    
    public boolean isFull(int row, int col) {
    
        if (row <= 0 || row > this.n || col <= 0 || col > this.n) {
            
            throw new IndexOutOfBoundsException("row:"+ row +" or/and col:"+ col +" out of bounds n:"+ this.n);
        }
        // Translate xy coordinates to array index
        int index = this.xyCoordinateToIndex(row, col);
        return this.wQuickUnionUFFull.connected(this.topSite, index);
    }
    
    public int numberOfOpenSites() {
       
        return this.openedSites;
    }
    
    public boolean percolates() {
        
        return this.wQuickUnionUF.connected(this.topSite, this.bottomSite);
    }
    
    private void union(int p, int q) {
        
        this.wQuickUnionUF.union(p,q);
        this.wQuickUnionUFFull.union(p,q);
    }
    
    private int xyCoordinateToIndex(int x, int y) {
        
        if (x <= 0 || x > this.n || y <= 0 || y > this.n) {
            
            throw new IndexOutOfBoundsException("X:"+ x +" or/and Y:"+ y +" out of bounds N:"+ this.n);
        }
        return (x -1) * this.n + y;
    }
    
    private boolean isRowTopEdge(int row) {
        
        return row == 1;
    }
    
    private boolean isRowBottomEdge(int row) {
        
        return row == this.n;
    }
    
    private boolean isColumnLeftEdge(int col) {
        
        return col == 1;
    }
    
    private boolean isColumnRightEdge(int col) {
        
        return col == this.n;
    }
    
    public static void main (String[] args) {
        
//        testIsOpenAllFalse();
//        testIsOpenAllOpen();
//        testIsFull();
//        testPercolatesFalse();
//        testPercolatesTrue();
        testNumberOfOpenSites();
    }   
    
    private static void testIsOpenAllFalse() {
        
        final Percolation per = new Percolation(3);
        
        System.out.println("isOpen(1, 1) = " + per.isOpen(1, 1));
        System.out.println("isOpen(2, 1) = " + per.isOpen(2, 1));
        System.out.println("isOpen(3, 1) = " + per.isOpen(3, 1));
        System.out.println("isOpen(1, 2) = " + per.isOpen(1, 2));
        System.out.println("isOpen(2, 2) = " + per.isOpen(2, 2));
        System.out.println("isOpen(3, 2) = " + per.isOpen(3, 2));
        System.out.println("isOpen(1, 3) = " + per.isOpen(1, 3));
        System.out.println("isOpen(2, 3) = " + per.isOpen(2, 3));
        System.out.println("isOpen(3, 3) = " + per.isOpen(3, 3));
    }
    
    private static void testIsOpenAllOpen() {
        
        final Percolation per = new Percolation(3);
        per.open(1, 1);
        per.open(2, 1);
        per.open(3, 1);
        per.open(1, 2);
        per.open(2, 2);
        per.open(3, 2);
        per.open(1, 3);
        per.open(2, 3);
        per.open(3, 3);
        
        System.out.println("isOpen(1, 1) = " + per.isOpen(1, 1));
        System.out.println("isOpen(2, 1) = " + per.isOpen(2, 1));
        System.out.println("isOpen(3, 1) = " + per.isOpen(3, 1));
        System.out.println("isOpen(1, 2) = " + per.isOpen(1, 2));
        System.out.println("isOpen(2, 2) = " + per.isOpen(2, 2));
        System.out.println("isOpen(3, 2) = " + per.isOpen(3, 2));
        System.out.println("isOpen(1, 3) = " + per.isOpen(1, 3));
        System.out.println("isOpen(2, 3) = " + per.isOpen(2, 3));
        System.out.println("isOpen(3, 3) = " + per.isOpen(3, 3));
    }
    
    private static void testIsFull() {
        
        final Percolation per = new Percolation(3);
        System.out.println("isOpen(1, 1) = " + per.isOpen(1, 1));
        per.open(1, 1);
        System.out.println("isOpen(1, 1) = " + per.isOpen(1, 1));
        System.out.println("isFull(1, 1) = " + per.isFull(1, 1));

        System.out.println("isOpen(3, 3) = " + per.isOpen(3, 3));
        per.open(3, 3);
        System.out.println("isOpen(3, 3) = " + per.isOpen(3, 3));
        System.out.println("isFull(3, 3) = " + per.isFull(3, 3));
    }
    
    private static void testPercolatesFalse() {
        
        final Percolation per = new Percolation(3);
        per.open(1, 1);
        per.open(2, 1);
        per.open(3, 3);

        System.out.println("percolates() = " + per.percolates());
    }
    
    private static void testPercolatesTrue() {
        
        final Percolation per = new Percolation(3);
        per.open(1, 1);
        per.open(2, 1);
        per.open(3, 1);

        System.out.println("percolates() = " + per.percolates());
    }

    private static void testNumberOfOpenSites() {
    
        final Percolation per = new Percolation(5);
        
        for (int i = 1; i <= 5; i++) {
            
            for (int j = 1; j <= 5; j++) {
            
                per.open(i, j);
            }
        }
        
        System.out.println("Open sites: " + per.numberOfOpenSites());
    }
}
