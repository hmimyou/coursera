import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

   private class Node
   {
       public Item item;
       public Node next;
       public Node prev;
       public Node(Item i)
       {
           item = i;
           next = null;
           prev = null;
       }
   }
   private Node head = null;
   private Node tail = null;
   private int size = 0;
   public Deque()                           // construct an empty deque
   {}
   public boolean isEmpty()                 // is the deque empty?
   {
       return size == 0;
   }
   public int size()                        // return the number of items on the deque
   {
       return size;
   }
   public void addFirst(Item item)          // insert the item at the front
   {
       if (item == null)
       {
           throw new NullPointerException();
       }
       Node newNode = new Node(item);
       if (size == 0)
       {
           head = newNode;
           tail = newNode;
       }
       else
       {
           newNode.next = head;
           head.prev = newNode;
           head = newNode;
       }
       size++;                   
   }
   public void addLast(Item item)           // insert the item at the end
   {
       if (item == null)
       {
           throw new NullPointerException();
       }
       Node newNode = new Node(item);
       if (size == 0)
       {
           head = newNode;
           tail = newNode;
       }
       else
       {
           tail.next = newNode;
           newNode.prev = tail;
           tail = tail.next;
       }
       size++; 
   }
   public Item removeFirst()                // delete and return the item at the front
   {
       if (size == 0) 
       {
           throw new NoSuchElementException();
       }
       Item removedItem = head.item;
       if (size == 1) 
       {         
           head = null;
           tail = null;
       }
       else
       {
           Node oldHead = head;
           head = head.next;
           head.prev = null;
           oldHead.next = null;
       }
       size--;
       return removedItem;
   }
   public Item removeLast()                 // delete and return the item at the end
   {
       if (size == 0) 
       {
           throw new NoSuchElementException();
       }
       Item removedItem = tail.item;
       if (size == 1) 
       {         
           head = null;
           tail = null;
       }
       else
       {
           Node oldTail = tail;
           tail = tail.prev;
           tail.next = null;
           oldTail.prev = null;
       }
       size--;
       return removedItem;
   }
   public Iterator<Item> iterator()         // return an iterator over items in order from front to end
   {
       return new DequeIterator();
   }
   private class DequeIterator implements Iterator<Item>
   {
       private Node current = head;
       public boolean hasNext() { return current != null;}
       public void remove() { throw new UnsupportedOperationException();}
       public Item next()
       {
           if (current == null) 
           {
               throw new NoSuchElementException();
           }
           Item item = current.item;
           current = current.next;
           return item;
       }
   }
   public static void main(String[] args)   // unit testing
   {
      Deque<String> dequeStr = new Deque<String>();
      
      dequeStr.addFirst("2");
      dequeStr.addLast("3");
      dequeStr.addLast("4");
      dequeStr.addFirst("1");
      
       for (String s: dequeStr)
           StdOut.print(s+ " ");
       StdOut.println("--------");
       
      StdOut.println("size = " + dequeStr.size());
      StdOut.println(dequeStr.removeFirst());
      StdOut.println(dequeStr.removeLast());
      StdOut.println(dequeStr.removeFirst());
      StdOut.println(dequeStr.removeLast());
      
      StdOut.println(dequeStr.isEmpty());
      
      
   }
}