import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;
public class RandomizedQueue<Item> implements Iterable<Item>{
    
    private Item[] a;
    private int n;
    
    public RandomizedQueue(){
        a=(Item[])new Object[2];
        n=0;
    }
    
    public boolean isEmpty(){
        return n==0;
    }
    
    public int size(){
        return n;
    }
    
    private void resize(int capacity){
        Item[] temp=(Item[])new Object[capacity];
        for(int i=0;i<n;i++)
            temp[i]=a[i];
        StdRandom.shuffle(temp,0,n);
        a=temp;
        //System.out.println("Shuffle and Resize"+n+" "+capacity);
    }
    
    public void enqueue(Item item){
        if(item==null) throw new IllegalArgumentException();
        if(n==a.length) resize(2*n);
        a[n++]=item;
    }
    
    public Item dequeue(){
        if(isEmpty()) throw new NoSuchElementException("underflow");
        Item item = a[n-1];
        a[n-1] = null;                           
        n--;
        if (n > 0 && n == a.length/4) resize(a.length/2);
        return item;
    }
    
    public Item sample(){
        if(isEmpty()) throw new NoSuchElementException("underflow");
        return a[StdRandom.uniform(n)];
    }
    
    public Iterator<Item> iterator(){
        return new RandomizedQueueIterator();
    }
    
    private class RandomizedQueueIterator implements Iterator<Item>{
        private int i;
        private Item[] data;

        public RandomizedQueueIterator() {
            i = n-1;
            data=(Item[])new Object[n];
            for(int c=0;c<n;c++){
                data[c]=a[c];
            }
            StdRandom.shuffle(data,0,n);
        }

        public boolean hasNext() {
            return i >= 0;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return data[i--];
        }
        
    }
    
    public static void main(String[] args){
        int n=5;
        RandomizedQueue<Integer> queue=new RandomizedQueue<Integer>();
        for(int i=0;i<5;i++){
            queue.enqueue(i);
        }
       
        for (int a : queue) {
            for (int b : queue)
                StdOut.print(a + "-" + b + " ");
            StdOut.println();
        }
        
         System.out.println();
         
         for(int i=0;i<5;i++){
            StdOut.print(queue.dequeue());
        }
       
    }
}