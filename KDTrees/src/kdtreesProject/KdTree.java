/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kdtreesProject;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import java.util.NoSuchElementException;

/**
 *
 * @author nsalemu2019
 */
public class KdTree {

    private Node root;

    private class Node {

        private double key;           // sorted by key
        private boolean useX;
        private Point2D point;         // associated data
        private Node left, right;  // left and right subtrees
        private int size;          // number of nodes in subtree

        public Node(double key, boolean useX, Point2D point, int size) {
            this.key = key;
            this.useX = useX;
            this.point = point;
            this.size = size;
        }

    }

    public KdTree() {

    }                       // construct an empty set of points 

    public boolean isEmpty() {
        return size() == 0;

    }            // is the set empty? 

    public int size() {
        return size(root);
    }            // number of points in the set 

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("calls put() with a null key");
        }
        if (get(p) == null) {
            root = put(root, p, true);
        }
    }          // add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return get(p) != null;
    }            // does the set contain point p? 

    public void draw() {
        StdDraw.enableDoubleBuffering();

        for (Point2D point : points()) {
            StdDraw.point(point.x(), point.y());
        }
    }            // draw all points to standard draw 

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("first argument to keys() is null");
        }

        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, queue, rect, true);
        return queue;
    }            // all points that are inside the rectangle (or on the boundary) 

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("argument to floor() is null");
        }
        if (isEmpty()) {
            throw new NoSuchElementException("calls floor() with empty symbol table");
        }
        Point2D x = bestIn(root, p, null);
        if (x == null) {
            return null;
        } else {
            return x;
        }

    }            // a nearest neighbor in the set to point p; null if the set is empty 

    public static void main(String[] args) {
    }// unit testing of the methods (optional) 

    private Node put(Node x, Point2D point, boolean useX) {
        Double key;
        if (useX) {
            key = point.x();
        } else {
            key = point.y();
        }
        if (x == null) {
            return new Node(key, useX, point, 1);
        }

        int cmp = key.compareTo(x.key);
        if (cmp < 0) {
            x.left = put(x.left, point, !useX);
        } else {
            x.right = put(x.right, point, !useX);
        }
        x.size = 1 + size(x.left) + size(x.right);
        return x;
    }

    private Point2D get(Point2D point) {
        return get(root, point, true);
    }

    private Point2D get(Node x, Point2D point, boolean useX) {
        if (point == null) {
            throw new IllegalArgumentException("calls get() with a null key");
        }
        if (x == null) {
            return null;
        }
        Double key;
        if (useX) {
            key = point.x();
        } else {
            key = point.y();
        }

        int cmp = key.compareTo(x.key);
        if (point.equals(x.point)) {
            return x.point;
        } else if (cmp < 0) {
            return get(x.left, point, !useX);
        } else {
            return get(x.right, point, !useX);
        }
    }

    private void range(Node x, Queue<Point2D> queue, RectHV rect, boolean useX) {
        if (x == null) {
            return;
        }
        int cmplo;
        int cmphi;
        int cmploOther;
        int cmphiOther;
        if (useX) {
            cmplo = Double.compare(rect.xmin(), x.key);
            cmphi = Double.compare(rect.xmax(), x.key);
            cmploOther = Double.compare(rect.ymin(), x.point.y());
            cmphiOther = Double.compare(rect.ymax(), x.point.y());
        } else {
            cmplo = Double.compare(rect.ymin(), x.key);
            cmphi = Double.compare(rect.ymax(), x.key);
            cmploOther = Double.compare(rect.xmin(), x.point.x());
            cmphiOther = Double.compare(rect.xmax(), x.point.x());
        }

        if (cmplo < 0) {
            range(x.left, queue, rect, !useX);
        }
        if (cmplo <= 0 && 0 <= cmphi && cmploOther <= 0 && 0 <= cmphiOther) {
            queue.enqueue(x.point);
        }
        if (cmphi >= 0) {
            range(x.right, queue, rect, !useX);
        }
    }

    private Point2D bestIn(Node x, Point2D ref, Point2D best) {
        if (x == null) {
            return best;
        }
        if (best == null) {
            best = x.point;
        }
        double curDist = ref.distanceSquaredTo(x.point);
        double bestDist = ref.distanceSquaredTo(best);

        if (curDist < bestDist) {
            best = x.point;
        }
        Point2D leftPt = bestIn(x.left, ref, best);
        Point2D rightPt = bestIn(x.right, ref, best);
        Double leftBest = ref.distanceSquaredTo(leftPt);
        Double rightBest = ref.distanceSquaredTo(rightPt);
        if (leftBest < rightBest) {
            return leftPt;
        } else {
            return rightPt;
        }
    }

    private Iterable<Point2D> points() {

        Queue<Point2D> queue = new Queue<Point2D>();
        keys(root, queue);
        return queue;
    }

    private void keys(Node x, Queue<Point2D> queue) {
        if (x == null) {
            return;
        }

        queue.enqueue(x.point);
        keys(x.left, queue);
        keys(x.right, queue);
    }

    private int size(Node x) {
        if (x == null) {
            return 0;
        } else {
            return x.size;
        }
    }
}
