/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dequeproject;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;

/**
 *
 * @author nsalemu2019
 */
public class DequeProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int k = Integer.parseInt("10");
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        int i = 0;
        // read in a sequence of N strings from standard input
        while (i < 9) {

            StdOut.println((i++) + " next num");
            String item = StdIn.readString();
            rq.enqueue(item);
        }
        
        // print out exactly k of them, uniformly at random;
        // each item from the sequence can be printed out at most once
        Iterator<String> itr = rq.iterator();
        while (itr.hasNext()) {
            StdOut.print(itr.next()+", ");
        }

    }

}
