/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kdtreesProject;

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author nsalemu2019
 */
public class PointSET {

    private SET<Point2D> set;

    public PointSET() {
        set = new SET();
    }// construct an empty set of points 

    public boolean isEmpty() {
        return set.isEmpty();
    }// is the set empty? 

    public int size() {
        return set.size();
    }// number of points in the set 

    public void insert(Point2D p) {
        set.add(p);
    }// add the point to the set (if it is not already in the set)

    public boolean contains(Point2D p) {
        return set.contains(p);
    }// does the set contain point p? 

    public void draw() {
        StdDraw.enableDoubleBuffering();

        for (Point2D point : set) {
            StdDraw.point(point.x(), point.y());
        }

    }// draw all points to standard draw 

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("called range() with a null key");
        }

        List<Point2D> range = new ArrayList<>();
        for (Point2D point2D : set) {
            if (rect.contains(point2D)) {
                range.add(point2D);
            }
        }
        return () -> new Iterator<Point2D>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < range.size();
            }

            @Override
            public Point2D next() {
                return range.get(index++);
            }
        };
    }// all points that are inside the rectangle (or on the boundary) 

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("called nearest() with a null key");
        }
        Point2D close = null;
        double bestDistance = -1;
        for (Point2D point2D : set) {
            double curDistance = p.distanceSquaredTo(point2D);
            if (!p.equals(point2D)) {
                if (bestDistance < 0) {
                    bestDistance = curDistance;
                } else if (curDistance < bestDistance) {
                    bestDistance = curDistance;
                    close = point2D;
                }
            }
        }
        return close;
    }// a nearest neighbor in the set to point p; null if the set is empty 

    public static void main(String[] args) {
    }// unit testing of the methods (optional) 

}
