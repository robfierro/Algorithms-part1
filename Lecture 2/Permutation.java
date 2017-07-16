// Algorithms Part 1 Programming Assignment: RandomizedQueue
// Roberto Fierro

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class Permutation {
    
    public static void main(String[] args) {
    
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        rq.enqueue(StdIn.readString());
        for (int i = 1; !StdIn.isEmpty() && i < k - 1; i++) {
        
            int r = StdRandom.uniform(i);
            (String)rq.q[i] = rq.q[r];
            rq.q[r] = (Object)StdIn.readString();
        }
        
        for (int i = k; !StdIn.isEmpty(); i++) {
        
            int r = StdRandom.uniform(i);
            if (r < k) {
            
                rq.q[r] = StdIn.readString();
            }
        }
        
        for (int i = 0; i < k; i++) {
            
            StdOut.println(rq.dequeue());
        }
    }
}