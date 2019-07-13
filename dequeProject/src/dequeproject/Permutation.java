/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dequeproject;

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/**
 *
 * @author nsalemu2019
 */
public class Permutation {
    
    public static void main(String[] args){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        // read in a sequence of N strings from standard input
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            rq.enqueue(item);
        }

        // print out exactly k of them, uniformly at random;
        // each item from the sequence can be printed out at most once
        while (k > 0) {
            StdOut.println(rq.dequeue());
            k--;
        }
        
    }
}
