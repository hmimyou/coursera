import java.util.Arrays;

public class Fast {

   public static void main(String[] args) 
   {
      Point[] orig_points = readPoints(args[0]);
     
      int N = orig_points.length;
      
      Arrays.sort(orig_points);

      for (int i=0; i<N; i++)
      {
          Point[] points = orig_points.clone();
          Point target = orig_points[i];
          Arrays.sort(points, target.SLOPE_ORDER);  
          double lastSlope = Double.NEGATIVE_INFINITY;
          int lastLength = 1;
          for (int j=1; j<N; j++)
          {
              if ( target.slopeTo(points[j]) == lastSlope)
              {
                  lastLength ++;
              }
              else 
              {
                  if (lastLength >= 3 && target.compareTo(points[j-lastLength]) < 0)
                  {
                      Point[] found = new Point[lastLength + 1];
                      found[0] = target;
                      for (int k=1; k<lastLength+1; k++)
                      {
                          found[k] = points[j - lastLength + k -1];
                      }
                      PrintPoints(found);
                  }
                  
                  lastSlope = target.slopeTo(points[j]);
                  lastLength = 1; 
              }
          }
          
          if (lastLength >= 3 && target.compareTo(points[N-lastLength]) < 0)
          {
              Point[] found = new Point[lastLength + 1];
              found[0] = target;
              for (int k=1; k<lastLength+1; k++)
              {
                  found[k] = points[N - lastLength + k -1];
              }
              PrintPoints(found);
          }
          
      }
      StdDraw.show(0);
   }
   
   private static Point[] readPoints(String fileName) 
   {
       StdDraw.setXscale(0, 32768);
       StdDraw.setYscale(0, 32768);
       In in = new In(fileName);
       int N = in.readInt();
       Point[] points = new Point[N];
       for (int i=0; i<N;i++)
       {
           int x = in.readInt();
           int y = in.readInt();
           points[i] = new Point(x,y);
           points[i].draw();
       }
       return points;
   }
   
   private static void PrintPoints(Point[] points)
   {
       for (int i=0; i< points.length - 1; i++)
       {
           StdOut.print(points[i] + " -> ");
       }
       StdOut.print(points[points.length-1]);
       StdOut.println();
       points[0].drawTo(points[points.length-1]);
   }
   
}
