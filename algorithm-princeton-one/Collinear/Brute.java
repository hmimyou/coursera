import java.util.Arrays;

public class Brute {
   public static void main(String[] args) 
   {
      StdDraw.setXscale(0, 32768);
      StdDraw.setYscale(0, 32768);
      Point[] points = readPoints(args[0]);
     
      int N = points.length;
      
      Arrays.sort(points);

      for (int i=0; i<N-3; i++)
          for (int j=i+1; j< N -2; j++)
              for (int k = j+1; k < N-1; k++)
                   for (int l=k+1; l <N; l++)
      {
          if (points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])
             && points[i].slopeTo(points[j]) == points[i].slopeTo(points[l]))
          {
              Point[] found = new Point[4];
              found[0] = points[i];
              found[1] = points[j];
              found[2] = points[k];
              found[3] = points[l];
              
              PrintPoints(found);
          }
      }  
      StdDraw.show(0);
   }
   
   private static Point[] readPoints(String fileName) 
   {
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