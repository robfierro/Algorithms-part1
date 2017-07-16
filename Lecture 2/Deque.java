// Algorithms Part 1 Programming Assignment: Deque
// Roberto Fierro

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
   
    private Node first;
    private Node last;
    private int count;
    private class Node {
        public Item value;
        public Node next;
        public Node previous;
    }
    // construct an empty deque
    public Deque() {
        count = 0;
        first = null;
        last = null;
    }
    
    // is the deque empty?
    public boolean isEmpty() {
        
        return first == null;
    }
        
    // return the number of items on the deque
    public int size() {
    
        return count;
    }
    
    // add the item to the front
    public void addFirst(Item item) {
    
        if (item == null) throw new IllegalArgumentException("Attempted to add a null item");
        
        Node oldFirst = first;
        first = new Node();
        first.value = item;
        first.next = oldFirst;
        first.previous = null;
        if (first.next == null) {
            
            last = first;
        } else {
            
            first.next.previous = first;
        }
        count++;
    }         
   
    // add the item to the end
    public void addLast(Item item) {
    
        if (item == null) throw new IllegalArgumentException("Attempted to add a null item");
        
        Node newLast = new Node();
        newLast.value = item;
        newLast.next = null;
        
        if (this.isEmpty()) {
            
            newLast.previous = null;
            first = newLast;
        } else {
            
            last.next = newLast;
            newLast.previous = last;
        }
        last = newLast;
        
        if (this.size() == 1) {
            first.next = last;
        }
        count++;
    }          
   
    // remove and return the item from the front
    public Item removeFirst() {
        
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        
        Item value = first.value;
        first = first.next;
        
        if (this.isEmpty()) {
            
            last = null;
        } else {
            
            first.previous = null;
        }
        count--;
        return value;
    }               
   
    // remove and return the item from the end
    public Item removeLast() {
        
        if (isEmpty()) throw new NoSuchElementException("Stack underflow");
        
        Item value = last.value;
        if (this.size() > 1) {
            last = last.previous;
            last.next = null;
        } else {
            last = null;
            first = null;
        }
        count--;
        return value;
    }              
   
    // return an iterator over items in order from front to end
    public Iterator<Item> iterator() {
    
        return new ListIterator();
    }     
   
    private class ListIterator implements Iterator<Item> {
    
        private Node current = first;
        
        public boolean hasNext() {
        
            return current != null;
        }
        
        public void remove() { throw new UnsupportedOperationException("Remove not supported"); }
        
        public Item next() {
            
            if (!this.hasNext()) throw new NoSuchElementException("Deque is empty");
            
            Item value = current.value;
            current = current.next;
            return value;
        }       
    }
    
    // unit testing (optional)
    public static void main(String[] args) {
        Deque<String> q = new Deque<String>();
        q.addFirst("1");
    }
}
