/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collinearpointsproject;

import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nsalemu2019
 */
public class FastCollinearPoints {

    private List<LineSegment> lines = new ArrayList<>();
    private Point[] points;
    private int noOfSegments;

    public FastCollinearPoints(Point[] orgPoints) {
        if (orgPoints == null) {
            throw new IllegalArgumentException();
        }

        points = new Point[orgPoints.length];
        for (int i = 0; i < orgPoints.length; i++) {
            if (orgPoints[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        points = orgPoints.clone();
        Arrays.sort(points);

        int l = 0;
        while (l < points.length - 1) {
            if (points[l].compareTo(points[++l]) == 0) {
                throw new IllegalArgumentException();
            }
        }

        for (int i = 0; i < points.length; i++) {
            Arrays.sort(points);
            Point origin = points[i];
            Point[] orherPoints = new Point[points.length - i -1];
            int k=0;
            for (int j = i + 1; j < points.length; j++) {
                    orherPoints[k++] = points[j];
                
            }
     

            Arrays.sort(orherPoints, origin.slopeOrder());
            double[] ds = new double[orherPoints.length];
            for (int j = 0; j < orherPoints.length; j++) {

                ds[j] = origin.slopeTo(orherPoints[j]);

            }

            int min = 0;
            int max = 0;
            for (int j = 0; j < orherPoints.length - 1; j++) {
                if (ds[j] == ds[j + 1]) {
                    max++;
                } else {
                    min = j;
                    max = j;
                }
                if (max - min > 1) {

                    if (j + 2 >= ds.length || ds[j + 1] != ds[j + 2]) {
                        lines.add(new LineSegment(origin, orherPoints[j + 1]));

                    }

                }

            }

        }
    }// finds all line segments containing 4 or more points

    public int numberOfSegments() {
        return noOfSegments;
    }// the number of line segments{

    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[lines.size()];
        return lines.toArray(seg);
    }// the line segments

}
