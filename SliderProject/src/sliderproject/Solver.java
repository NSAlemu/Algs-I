/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sliderproject;

import edu.princeton.cs.algs4.MinPQ;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nsalemu2019
 */
public class Solver {

    private final MinPQ<Node> pq;
    private Node min;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        this.pq = new MinPQ<>();
        final Board tempBoard = initial;
        pq.insert(new Node(tempBoard, null));
        min = pq.min();
        while (!min.board.isGoal()) {
            min = pq.delMin();
          
            for (Board newNode:min.board.neighbors()) {
                boolean ins=false;
                if (min.previousNode == null || !newNode.equals(min.previousNode.board)) {
                    pq.insert(new Node(newNode, min));
                    ins=true;
                }
            }
    
            
        }
        
    }

//    // is the initial board solvable? (see below)
//    public boolean isSolvable() {
//
//    }
    // min number of moves to solve initial board
    public int moves() {
        return min.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        return () -> getBoardList();
    }

    // test client (see below) 
    public static void main(String[] args) {

    }

    public boolean isSolvable() {
        return true;
    }

    private final class Node implements Comparable<Node> {

        final Board board;
        final int moves;
        final Node previousNode;

        public Node(Board board, Node previousNode) {
            this.board = board;
            this.previousNode = previousNode;

            if (previousNode == null) {
                moves = 0;
            } else {
                this.moves = this.previousNode.moves + 1;

            }
        }

        @Override
        public int compareTo(Node o) {
            return Integer.compare(this.priority(), o.priority());
        }

        private int priority() {
            return this.board.manhattan() + this.moves;
        }
    }

    private List<Board> getMins(){
        List<Board> list = new ArrayList<>();
        Node cur=new Node(min.board,min.previousNode);
        while(cur!=null){
            list.add(cur.board);
            cur=cur.previousNode;
        }
        Collections.reverse(list);
        return list;
        
        
    }
    private Iterator<Board> getBoardList() {
        return new Iterator<Board>() {
            List<Board> mins=getMins();
            int index=0;

            @Override
            public boolean hasNext() {
                return index < mins.size();
            }

            @Override
            public Board next() {

                return mins.get(index++);
            }
        };
    }
}
