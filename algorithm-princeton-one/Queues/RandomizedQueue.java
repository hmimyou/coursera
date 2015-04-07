import java.util.Iterator;
import java.util.NoSuchElementException;
public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] queue;
    private int N = 0;
    public RandomizedQueue()                 // construct an empty randomized queue
    {
        queue = (Item[])new Object[1];
    }
    public boolean isEmpty()                 // is the queue empty?
    {
        return N == 0;
    }
    public int size()                        // return the number of items on the queue
    {
        return N;
    }
    public void enqueue(Item item)           // add the item
    {
        if (item == null) 
        {
            throw new NullPointerException();
        }
        if (N==queue.length) resize(N*2);
        queue[N++] = item;        
    }
    
    public Item dequeue()                    // delete and return a random item
    {
        if (N <=0)
        {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        Item removed = queue[i];
        
        queue[i] = queue[--N];
        queue[N] = null;
        
        if (N > 0 && N == queue.length/4)
        {
            resize(queue.length/2);
        }
        
        return removed;
    }
    public Item sample()                     // return (but do not delete) a random item
    {
        if (N <=0)
        {
            throw new NoSuchElementException();
        }
        int i = StdRandom.uniform(N);
        return queue[i];       
    }
    
    private void resize(int M)
    {
        Item[] newQueue = (Item[]) new Object[M];
        for (int i=0; i<N; i++)
        {
            newQueue[i] = queue[i];
        }
        queue = newQueue;
    }
    public Iterator<Item> iterator()         // return an independent iterator over items in random order
    {
       return new RandomQueueIterator();
    }
   private class RandomQueueIterator implements Iterator<Item>
   {
       private Item[] randomQueue;
       private int cur = 0;
       public RandomQueueIterator()
       {
           if (N>0)
           {
               randomQueue = (Item[]) new Object[N];
               for (int i=0; i<N; i++)
               {
                   randomQueue[i] = queue[i];
               }
               for (int i=0; i<N; i++)
               {
                   int target = StdRandom.uniform(i, N);
                   Item temp = randomQueue[target];
                   randomQueue[target] = randomQueue[i];
                   randomQueue[i] = temp;
               }
           }          
       }
       public boolean hasNext() { return randomQueue!=null && randomQueue.length > cur; }
       public void remove() { throw new UnsupportedOperationException();}
       public Item next()
       {
           if (randomQueue == null || randomQueue.length == cur) 
           {
               throw new NoSuchElementException();
           }
           return randomQueue[cur++];
       }
   }
    public static void main(String[] args)   // unit testing
    {
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("0");
        rq.enqueue("1");
        rq.enqueue("2");
        rq.enqueue("3");
        rq.enqueue("4");
        rq.enqueue("5");

        StdOut.println("size="+rq.size());
        StdOut.println("sample 1 =" + rq.sample());
        StdOut.println("sample 2 =" + rq.sample());
        StdOut.println("sample 3 =" + rq.sample());
        StdOut.println("size="+rq.size());
        StdOut.println("removed 1 =" + rq.dequeue());
        StdOut.println("removed 2 =" + rq.dequeue());
        StdOut.println("size="+rq.size());
        
        StdOut.println("iterator 1: ");        
        for (String s: rq)
        {
            StdOut.print(s +" ");
        }
        StdOut.println(" ");
        StdOut.println("iterator 2: ");
        for (String s: rq)
        {
            StdOut.print(s +" ");
        }
        StdOut.println(" ");      
        
        
        StdOut.println("removed 3 =" + rq.dequeue());
        StdOut.println("removed 4 =" + rq.dequeue());
        StdOut.println("removed 5 =" + rq.dequeue());
        StdOut.println("removed 6 =" + rq.dequeue());
        StdOut.println("size="+rq.size());
       
    }
}