import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shizhan on 16/11/21.
 */
public class BruteCollinearPoints {
    private LineSegment[] lineSegments;
    private int count = 0;
    private List<LineSegment> segmentList;
    private Point[] ps;
    private Point[] pointsTemp;

    private boolean isRepeat(Point[] points) {
        boolean flag = false;
        int len = points.length;
        Arrays.sort(points);
//        for(int i=0;i<len;i++){
//            System.out.println(points[i].x+","+points[i].y);
//        }
        for (int i = 0; i < len; i++) {
            if (i != len - 1) {
                if (points[i].compareTo(points[i + 1])==0) {
                    flag = true;
                    break;
                }
            } else if (i == len - 1 && i != 0) {
                if (points[i].compareTo(points[i - 1])==0) {
                    flag = true;
                }
            }
        }
        //System.out.println(flag);
        return flag;
    }

    public BruteCollinearPoints(Point[] points) {
        // finds all line segments containing 4 points
        if (points == null) throw new NullPointerException("argument is null!");
        int len = points.length;
        pointsTemp=new Point[len];
        for(int i=0;i<len;i++){
            pointsTemp[i]=points[i];
        }
        if (isRepeat(pointsTemp)) throw new IllegalArgumentException("repeat points!");
        segmentList = new ArrayList<>();
        ps = new Point[4];

        for (int p = 0; p < len; p++) {
            if (points[p] == null) throw new NullPointerException("point is null!");
//            if (isRepeat(points, p)) throw new IllegalArgumentException("repeat points");

            for (int q = p + 1; q < len; q++) {
                if (points[q] == null) throw new NullPointerException("point is null!");
//                if (isRepeat(points, q)) throw new IllegalArgumentException("repeat points");
                double p_qS = points[p].slopeTo(points[q]);//p and q
//
                for (int r = q + 1; r < len; r++) {
                    if (points[r] == null) throw new NullPointerException("point is null!");
//                    if (isRepeat(points, r)) throw new IllegalArgumentException("repeat points");

                    double p_rS = points[p].slopeTo(points[r]);//p and r

                    if (p_qS != p_rS)
                        continue;

                    for (int s = r + 1; s < len; s++) {
                        if (points[s] == null) throw new NullPointerException("point is null!");
//                        if (isRepeat(points, s)) throw new IllegalArgumentException("repeat points");

                        double p_sS = points[p].slopeTo(points[s]);//p and s

                        if (p_rS != p_sS)
                            continue;
                        ps[0] = points[p];
                        ps[1] = points[q];
                        ps[2] = points[r];
                        ps[3] = points[s];
                        Arrays.sort(ps);
                        count++;
//                        for(int m=0;m<4;m++)
//                            System.out.println(ps[m].x+","+ps[m].y);
                        LineSegment lineSegment = new LineSegment(ps[0], ps[3]);
                        segmentList.add(lineSegment);
                    }
                }
            }
        }
    }

    public int numberOfSegments() {
        // the number of line segments
        return count;
    }

    public LineSegment[] segments() {
        // the line segments
        int len = numberOfSegments();
        lineSegments = segmentList.toArray(new LineSegment[len]);
//        for (int i = 0; i < len; i++) {
//            lineSegments[i] = segmentList.get(i);
//        }
        return lineSegments;
    }

    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        //System.out.println(collinear.count);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
