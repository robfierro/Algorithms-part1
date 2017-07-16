
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class FastCollinearPoints {
   
    private HashMap<Double, List<Point>> foundSegments = new HashMap<>();
    private List<LineSegment> segments = new ArrayList<>();
    
    // finds all line segments containing 4 points 
    public FastCollinearPoints(Point[] points) {
    
        if (points == null) {
        
            throw new IllegalArgumentException("Null Argument");
        }
        this.duplicates(points);
        
        Point[] pointsCopy = Arrays.copyOf(points, points.length);
        
        for (Point startPoint : points) {
        
            Arrays.sort(pointsCopy, startPoint.slopeOrder());
            List<Point> slopePoints = new ArrayList<>();
            double slope = 0;
            double previousSlope = Double.NEGATIVE_INFINITY;
     
            for (int i = 1; i < pointsCopy.length; i++) {
            
                slope = startPoint.slopeTo(pointsCopy[i]);
                if (slope == previousSlope) {
                
                    slopePoints.add(pointsCopy[i]);
                } else {
                    if (slopePoints.size() >= 3) {
                        slopePoints.add(startPoint);
                        addSegmentIfNew(slopePoints, previousSlope);
                    }
                    slopePoints.clear();
                    slopePoints.add(pointsCopy[i]);
                }
                previousSlope = slope;
            }
            
            if (slopePoints.size() >= 3) {
                slopePoints.add(startPoint);
                addSegmentIfNew(slopePoints, slope);
            }
        }
    }
    
    private void addSegmentIfNew(List<Point> slopePoints, double slope) {
        List<Point> endPoints = foundSegments.get(slope);
        Collections.sort(slopePoints);

        Point startPoint = slopePoints.get(0);
        Point endPoint = slopePoints.get(slopePoints.size() - 1);

        if (endPoints == null) {
            endPoints = new ArrayList<>();
            endPoints.add(endPoint);
            foundSegments.put(slope, endPoints);
            segments.add(new LineSegment(startPoint, endPoint));
        } else {
            for (Point currentEndPoint : endPoints) {
                if (currentEndPoint.compareTo(endPoint) == 0) {
                    return;
                }
            }
            endPoints.add(endPoint);
            segments.add(new LineSegment(startPoint, endPoint));
        }
    }
    
    private void duplicates(Point [] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
            
                throw new IllegalArgumentException("Null Values");
            }
            for (int j = i + 1; j < points.length; j++) {
                if (points[j].compareTo(points[i]) == 0) {
                    throw new IllegalArgumentException("Duplicate Values");
                }
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
    
        return segments.size();
    } 
    
    // the line segments
    public LineSegment[] segments() {
    
        return segments.toArray(new LineSegment[segments.size()]);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}