/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package collinearpointsproject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author nsalemu2019
 */
public class BruteCollinearPoints {

    private List<LineSegment> lines = new ArrayList<>();

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException();
            }
        }
        Arrays.sort(points);

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                for (int k = j + 1; k < points.length; k++) {
                    for (int l = k + 1; l < points.length; l++) {

                        if (points[i].slopeTo(points[k]) == points[i].slopeTo(points[j])) {
                            if (points[i].slopeTo(points[l]) == points[i].slopeTo(points[j])) {

                                lines.add(new LineSegment(points[i], points[l]));

                            }
                        }

                    }
                }
            }

        }
    }// finds all line segments containing 4 points

    public int numberOfSegments() {
        return lines.size();
    }// the number of line segments{

    public LineSegment[] segments() {
        LineSegment[] seg = new LineSegment[lines.size()];
        return lines.toArray(seg);
    }// the line segments

}
