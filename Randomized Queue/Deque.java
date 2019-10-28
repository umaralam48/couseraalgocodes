import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.NoSuchElementException;
import java.util.Iterator;
public class Deque<Item> implements Iterable<Item>{
    
    private int n;
    private Node first,last;
    
    private class Node{
        private Item item;
        private Node next;
        private Node previous;
    }
    
    public Deque(){
        first=null;
        last=null;
        n=0;
    }
    
    public boolean isEmpty() {
        return first == null;
    }
    
    public int size(){
        return n;
    }
    
    public void addFirst(Item item){
       
        if(item==null) throw new IllegalArgumentException();
        Node newfirst=new Node();
        newfirst.item=item;
        newfirst.previous=null;
        newfirst.next=first;
        if(n!=0)
            first.previous=newfirst;
        first=newfirst;
        if(n==0)
            last=first;
        n++;
    }
    
    public void addLast(Item item){
       
        if(item==null) throw new IllegalArgumentException();
        Node newlast=new Node();
        newlast.item=item;
        newlast.next=null;
        newlast.previous=last;
        if(n!=0)
            last.next=newlast;
        last=newlast;
        if(n==0)
            first=last;
        n++;
    }
    
    public Item removeFirst(){
        if(isEmpty()) throw new NoSuchElementException("underflow");
        Item item=first.item;
        if(n==1){
            first=null;
            last=null;
            n--;
            return item;            
        }       
        Node second=first.next;
        second.previous=null;
        first=null;
        first=second;
        n--;
        return item;        
    }
    
    public Item removeLast(){
        if(isEmpty()) throw new NoSuchElementException("underflow");
        Item item=last.item;
        if(n==1){
            first=null;
            last=null;
            n--;
            return item;
        }
        Node secondlast=last.previous;
        secondlast.next=null;
        last=null;
        last=secondlast;
        n--;
        return item;
    }
    
    public Iterator<Item> iterator(){
        return new DequeIterator();
    }
    
    private class DequeIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){ 
            return current != null; 
        }
        public void remove(){ 
            throw new UnsupportedOperationException(); 
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.item;
            current = current.next; 
            return item;
        }
    }
    
    public static void main(String[] args){
       
        Deque<String> sample=new Deque<String>();
        sample.addLast("Last");
        sample.addFirst("second");
        System.out.println(sample.size());
        sample.addFirst("First");
        for(String item:sample)
            System.out.println(item);
        System.out.println(sample.removeLast());
        System.out.println(sample.removeFirst());
        System.out.println(sample.size());
        System.out.println(sample.removeFirst());
        System.out.println(sample.size());
        //sample.removeLast();
        //sample.removeFirst();
        sample.addLast(null);
    }

}