import java.util.Comparator;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new PointComparator();       // YOUR DEFINITION HERE

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    public Point() {
        this(0, 0);
    }
    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y == that.y && this.x == that.x) return Double.NEGATIVE_INFINITY;
        if (this.y == that.y) return +0d;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        return ((double) that.y - (double) this.y) / ((double) that.x - (double) this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (this.y < that.y || (this.y == that.y && this.x < that.x))
            return -1;
        if (this.y == that.y && this.x == that.x)
            return 0;
        return 1;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }
    
    private class PointComparator implements Comparator<Point> 
    {
        public int compare(Point p1, Point p2)
        {
           Point p = new Point(x, y);
           if (p.slopeTo(p1) < p.slopeTo(p2)) 
              return -1;
           if (p.slopeTo(p1) > p.slopeTo(p2))
               return 1;
           return 0;
        }
        
    }
        

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
    }
}