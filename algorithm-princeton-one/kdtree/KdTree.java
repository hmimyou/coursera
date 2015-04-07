import java.util.*;

public class KdTree {
    
    private class Node {
    
        public Node(Point2D point, boolean isVertical, RectHV container) 
        {
            value = point;
            left = null;
            right = null;
            this.isVertical = isVertical;
            this.container =container;
        }
        public boolean isVertical;
        public Point2D value;
        public Node left;
        public Node right;
        public RectHV container;
        
    }
    
    private Node root = null;
    private int size = 0;
    public KdTree() {}                               // construct an empty set of points
   public boolean isEmpty()                        // is the set empty?
   {
       return size == 0;
   }
   public int size()                               // number of points in the set
   {
       return size;
   }
   public void insert(Point2D p)                   // add the point p to the set (if it is not already in the set)
   {
       if (root == null)
       {
           root = new Node(p, true, new RectHV(0d, 0d, 1d, 1d));
           size++;
           return;
       }
       
       Node current = root;
       Node parent = null;
       boolean shouldBeVertical = true;
       boolean parentLeft = false;
       
       while(current != null) 
       {
           Point2D value = current.value;
           shouldBeVertical = !current.isVertical;
           parent = current;
           
           if (value.equals(p))
           {
               return;
           }
           
           if (current.isVertical)
           {
               if (p.x() < value.x())
               {
                   current = current.left;
                   parentLeft = true;
               }
               else 
               {
                   current = current.right;
                   parentLeft = false;
               }
           }
           else
           {
               if (p.y() < value.y())
               {
                   current = current.left;
                   parentLeft = true;
               }
               else 
               {
                   current = current.right;
                   parentLeft = false;
               }
           }           
       }
       
       RectHV container;
       if (parent.isVertical)
       {
           if (p.x() < parent.value.x())
           {
               container =  new RectHV(parent.container.xmin(),
                                       parent.container.ymin(),
                                       parent.value.x(),
                                       parent.container.ymax());
                                       
           }
           else
           {
               container =  new RectHV(parent.value.x(),
                                       parent.container.ymin(),
                                       parent.container.xmax(),
                                       parent.container.ymax());
                                       
           }
       }
       else
       {
           if (p.y() < parent.value.y())
           {
               container =  new RectHV(parent.container.xmin(),
                                       parent.container.ymin(),
                                       parent.container.xmax(),
                                       parent.value.y());
           }
           else
           {
               container =  new RectHV(parent.container.xmin(),
                                       parent.value.y(),
                                       parent.container.xmax(),
                                       parent.container.ymax());
                                       
           }
       }
       
       if (parentLeft)
       {
           parent.left = new Node(p, shouldBeVertical, container);
       }
       else 
       {
           parent.right = new Node(p, shouldBeVertical, container);
       }
       size++;
   }
   public boolean contains(Point2D p)              // does the set contain the point p?
   {    
       Node current = root;

       while(current != null) 
       {
           Point2D value = current.value;
           if (value.equals(p))
           {
               return true;
           }
           
           if (current.isVertical)
           {
               if (p.x() < value.x())
               {
                   current = current.left;
               }
               else 
               {
                   current = current.right;
               }
           }
           else
           {
               if (p.y() < value.y())
               {
                   current = current.left;
               }
               else 
               {
                   current = current.right;
               }
           }           
       }
       return false;
   }
   public void draw()                              // draw all of the points to standard draw
   {
     drawHelper(root);   
   }
   private void drawHelper(Node current)
   {
       if (current != null) 
       {
           StdDraw.setPenColor();
           current.value.draw();
           if (current.isVertical)
           {
               StdDraw.setPenColor(StdDraw.RED);
               StdDraw.line(current.value.x(), current.container.ymax(), current.value.x(), current.container.ymin());
           }
           else
           {
               StdDraw.setPenColor(StdDraw.BLUE);
               StdDraw.line(current.container.xmin(), current.value.y(), current.container.xmax(), current.value.y()); 
           }
           drawHelper(current.left);
           drawHelper(current.right);
       }
           
   }
   public Iterable<Point2D> range(RectHV rect)     // all points in the set that are inside the rectangle
   {
       return rangeHelper(rect, root);       
   }
   
   private List<Point2D> rangeHelper(RectHV rect, Node current)
   {
       List<Point2D> result = new LinkedList<Point2D>();
       if (current == null)
       {
           return result;
       }
       if (rect.contains(current.value))
       {
           result.add(current.value);
       }
       if (current.left != null && rect.intersects(current.left.container))
       {
           result.addAll(rangeHelper(rect, current.left));
       }
       if (current.right != null && rect.intersects(current.right.container))
       {
           result.addAll(rangeHelper(rect, current.right));
       }
       return result;
   }
   
   public Point2D nearest(Point2D p)               // a nearest neighbor in the set to p; null if set is empty
   {
       if (size == 0) return null;
       return nearestHelper(p, root, root).value;
   }
   
   private Node nearestHelper(Point2D p, Node current, Node nearestSoFar)
   {       
       if(current == null) return nearestSoFar;
       
       Node nearest = nearestSoFar;
       
       
       if (p.distanceSquaredTo(current.value) < p.distanceSquaredTo(nearestSoFar.value) ) 
       {
           nearest = current;
       }
       
       double minSquareDistSoFar = nearest.value.distanceSquaredTo(p);
       
       
       
       if ((current.isVertical && p.x() < current.value.x()) || (!current.isVertical && p.y() <current.value.y()))
       {
           if (current.left!=null && current.left.container.distanceSquaredTo(p) < minSquareDistSoFar)
           {
               nearest = nearestHelper(p, current.left, nearest);
               minSquareDistSoFar = nearest.value.distanceSquaredTo(p);
           }
           if (current.right!=null && current.right.container.distanceSquaredTo(p) < minSquareDistSoFar)
           {
               nearest = nearestHelper(p, current.right, nearest);
               minSquareDistSoFar = nearest.value.distanceSquaredTo(p);
           }     
       }
       else 
       {
           if (current.right!=null && current.right.container.distanceSquaredTo(p) < minSquareDistSoFar)
           {
               nearest = nearestHelper(p, current.right, nearest);
               minSquareDistSoFar = nearest.value.distanceSquaredTo(p);
           } 
           if (current.left!=null && current.left.container.distanceSquaredTo(p) < minSquareDistSoFar)
           {
               nearest = nearestHelper(p, current.left, nearest);
               minSquareDistSoFar = nearest.value.distanceSquaredTo(p);
           }
       }
            
       return nearest;
   }
}