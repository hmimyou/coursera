import java.util.*;

public class PointSET {
   
    private Set<Point2D> pointSet = new TreeSet<Point2D> ();
    
   public PointSET()                               // construct an empty set of points
   {
       pointSet = new TreeSet<Point2D> ();
   }
   public boolean isEmpty()                        // is the set empty?
   {
       return pointSet.isEmpty();
   }
   public int size()                               // number of points in the set
   {
       return pointSet.size();
   }
   public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
   {
       pointSet.add(p);
   }
   public boolean contains(Point2D p)              // does the set contain the point p?
   {
       return pointSet.contains(p);
   }
   public void draw()                              // draw all of the points to standard draw
   {
       for (Point2D point: pointSet) 
       {
           point.draw();
       }
   }
   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
   {
       List<Point2D> inRange = new LinkedList<Point2D> ();
       for (Point2D point: pointSet) 
       {
           if (rect.contains(point))
               inRange.add(point);
       }
       return inRange;
   }
   public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
   {
       Point2D nearest = null;
       double minDist = -1;
       for (Point2D point: pointSet) 
       {
           double dist = p.distanceSquaredTo(point);
           if (minDist < 0 || minDist > dist) 
           {
               minDist = dist;
               nearest = point;
           }          
       }
       return nearest;
   }
}