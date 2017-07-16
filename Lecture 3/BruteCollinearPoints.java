
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.Arrays;

public class BruteCollinearPoints {
   
    private LineSegment[] segments;
    
    // finds all line segments containing 4 points 
    public BruteCollinearPoints(Point[] points) {
    
        if (points == null) {
        
            throw new IllegalArgumentException("Null Argument");
        }
        
        this.duplicates(points);
        
        int count = 0;
        segments = new LineSegment[points.length];
        
        for (int i = 0; i < points.length; i++) {
            
            for (int j = i + 1; j < points.length; j++) {
                
                for (int k = j + 1; k < points.length; k++) {
                    
                    for (int m = k + 1; m < points.length; m++) {
                        
                        Point p = points[i], q = points[j], r = points[k], s = points[m];
                        if (p.slopeTo(q) == q.slopeTo(r) && q.slopeTo(r) == r.slopeTo(s)) {
                        
                            if (segments.length == count) {
                        
                                this.resizeArray(count*2);
                            }
                            segments[count++] = new LineSegment(p, s);
                        }
                        
                    }
                }
            }
        }
        
        LineSegment[] finalSegments = new LineSegment[count];
        
        for (int i = 0; i < count; i++) {
        
            finalSegments[i] = segments[i];
        }
        
        segments = finalSegments;
        
    }
    
    private void duplicates(Point [] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
            
                throw new IllegalArgumentException("Duplicate Values");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(points[i]) == 0) {
                    throw new IllegalArgumentException("Duplicate Values");
                }
            }
        }
    }
    
    private void resizeArray(int capacity) {
    
        LineSegment[] temp = new LineSegment[capacity];
        for (int i = 0; i < segments.length; i++) {
             
            temp[i] = segments[i];
        }
        segments = temp;
    }
    
    // the number of line segments
    public int numberOfSegments() {
    
        return segments.length;
    } 
    
    // the line segments
    public LineSegment[] segments() {
    
        return Arrays.copyOf(segments, segments.length);
    }
    
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}