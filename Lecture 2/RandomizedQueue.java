// Algorithms Part 1 Programming Assignment: RandomizedQueue
// Roberto Fierro

import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int head;
    private int tail;
    private int count;
    public Item[] q;
    
    // construct an empty randomized queue
    public RandomizedQueue() {
    
        q = (Item[]) new Object[2];
        count = 0;
        head = 0;
        tail = 0;
    }
    
    // is the queue empty?
    public boolean isEmpty() {
        
        return count == 0;
    }
    
    // return the number of items on the queue
    public int size() {
    
        return count;
    }
    
    // add the item
    public void enqueue(Item item) {
    
        if (item == null) {
            
            throw new IllegalArgumentException("Attempting to null to the queue");
        }
        
        if (tail == q.length) {
            
            this.resize(2*q.length);
        }
        
        q[tail++] = item;
        count++;
    }
    
    private void resize(int capacity) {
    
        // textbook implementation
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0, j = head; j < tail; j++) {
                
            if (q[j] == null) { continue; }
            
            temp[i++] = q[j];
        }
        
        q = temp;
        head = 0;
        tail = count;
    }
    
    // remove and return a random item
    public Item dequeue() {
        
        if (this.isEmpty()) {
            
            throw new NoSuchElementException("Queue is empty");
        }
        
        int randomIndex = StdRandom.uniform(q.length);
        
        while (q[randomIndex] == null) {
            
            randomIndex = StdRandom.uniform(q.length);
        }
        
        Item item = q[randomIndex];
        q[randomIndex] = null;
        count--;
        
        if (count == 0) {
        
            head = 0;
        } else if (randomIndex == head && randomIndex < q.length - 1) {
            
            while (q[randomIndex] == null) {
                
                randomIndex++;
            }
            head = randomIndex;
        } 
        
        if (count == 0) {
        
            tail = 0;
        } else if (randomIndex == tail - 1 && randomIndex < q.length) {
            
            while (q[randomIndex] == null) {
                
                randomIndex--;
            }
            tail = randomIndex + 1;
        }
        
        if (count > 0 && count == q.length/4) {
            this.resize(q.length/2);
        }
        
        return item;
    }
    
    // return (but do not remove) a random item
    public Item sample() {
    
        if (this.isEmpty()) {
            
            throw new NoSuchElementException("Queue is empty");
        }
        
        int randomIndex = StdRandom.uniform(q.length);
        
        while (q[randomIndex] == null) {
            
            randomIndex = StdRandom.uniform(q.length);
        }
        
        return q[randomIndex];
    }
    
    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
    
        return new RandomIterator();
    }
        
    private class RandomIterator implements Iterator<Item> {
    
        private int i;
        private Item[] randomQ;
        
        public RandomIterator() {
            
            i = 0;
            
            Item[] qCopy = (Item[]) new Object[q.length];
            for (int index = 0; index < q.length; index++) {
                
                qCopy[index] = q[index];
            }
            
            randomQ = (Item[]) new Object[q.length];
            
            while (i < count) {
                
                int randomIndex = StdRandom.uniform(qCopy.length);
                if (qCopy[randomIndex] == null) {
                    
                    continue;
                }
                
                randomQ[i++] = qCopy[randomIndex];
                qCopy[randomIndex] = null;
            }
            
            i = 0;
        }
        
        public boolean hasNext() {
            
            return i < count;
        }
        
        public void remove() { throw new UnsupportedOperationException("Remove not supported"); }
        
        public Item next() {
            
            if (!this.hasNext()) throw new NoSuchElementException("Queque is empty");
            
            return randomQ[i++];
        }      
    }
   
    public static void main(String[] args) {

        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        rq.enqueue("1");
    }
    
}