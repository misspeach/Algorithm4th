/**
 * Created by shizhan on 16/11/21.
 */
/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

//import java.util.Comparator;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (this.x == that.x) {
            if (this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            else
                return Double.POSITIVE_INFINITY;
        } else {
            if (this.y == that.y)
                return +0.0;
            else {
                return (double) (that.y - this.y) / (that.x - this.x);
            }
        }
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if(this.y<that.y)
            return -1;
        else if(this.y==that.y){
            if(this.x<that.x)
                return -1;
            else if(this.x>that.x)
                return 1;
            return 0;
        }
        else
            return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new Comparator<Point>() {
            @Override
            public int compare(Point a, Point b) {
                double aT_S = slopeTo(a);//this 与 a
                double bT_S = slopeTo(b);//this 与 b
                //System.out.println(aT_S+","+bT_S);
                if (aT_S < bT_S)
                    return -1;
                else if (aT_S == bT_S)
                    return 0;
                else
                    return 1;
            }
        };
    }
//    private static Comparator<Point> comparator(){
//        return new Comparator<Point>() {
//            @Override
//            public int compare(Point o1, Point o2) {
//                if(o1.y<o2.y)
//                    return -1;
//                else if(o1.y==o2.y){
//                    if(o1.x<o2.x)
//                        return -1;
//                    else if(o1.x>o2.x)
//                        return 1;
//                    return 0;
//                }
//                else
//                    return 1;
//            }
//        };
//    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        //Arrays.sort(points,this.slopeOrder());
        for(int i=0;i<points.length;i++){
            System.out.println(points[i].x+","+points[i].y);
        }
    }
}

