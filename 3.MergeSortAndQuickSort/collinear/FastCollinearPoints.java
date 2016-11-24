import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shizhan on 16/11/22.
 */
public class FastCollinearPoints {
    private int count = 0;
    private List<LineSegment> segmentList;
    private LineSegment[] lineSegments;
    private Point[] pointsTemp;
    private List<Point> start;
    private List<Point> end;
    private Point[] ps;

    private boolean isRepeat(Point[] points) {
        boolean flag = false;
        int len = points.length;
        Arrays.sort(points);
        for (int i = 0; i < len; i++) {
            if (i != len - 1) {
                if (points[i].compareTo(points[i + 1]) == 0) {
                    flag = true;
                    break;
                }
            } else if (i == len - 1 && i != 0) {
                if (points[i].compareTo(points[i - 1]) == 0) {
                    flag = true;
                }
            }
        }
        return flag;
    }

    private LineSegment createLineSegment(Point[] points, int p, int length, int cur) {
        int begin = cur - (length - 2);
        ps = new Point[length];
        ps[0] = points[p];
        for (int j = 1; j < length; j++) {
            ps[j] = points[begin + j - 1];
        }
        Arrays.sort(ps);
//        System.out.println("&&&&&&&");
//        for (int i = 0; i < length; i++) {
//            System.out.println(ps[i]);
//        }
        LineSegment lineSegment = new LineSegment(ps[0], ps[length - 1]);
        if (start.size() != 0 || end.size() != 0) {
            for (int i = 0; i < start.size(); i++) {//现有起点小于已经存在的那些起点 并且与对应终点组成的线段斜率跟之前的一样
                double newSlope = ps[0].slopeTo(ps[length - 1]);
                double oldSlope = start.get(i).slopeTo(end.get(i));
                if ((newSlope == oldSlope && (newSlope == start.get(i).slopeTo(ps[0]) || newSlope == start.get(i).slopeTo(ps[length - 1])))
                        && ps[0].compareTo(start.get(i)) >= 0
                        && ps[length - 1].compareTo(end.get(i)) <= 0) {
                    //System.out.println("repeat linesegment");
                    lineSegment = null;
                    break;
                }
            }
        }
        //if(lineSegment!=null)
        //System.out.println("createLineSegment: " + ps[0] + "->" + ps[length - 1] + ",slope is " + ps[0].slopeTo(ps[length - 1]));
        return lineSegment;
    }

    public FastCollinearPoints(Point[] points) {
        // finds all line segments containing 4 or more points
        if (points == null) throw new NullPointerException("argument is null!");
        int len = points.length;
        pointsTemp = new Point[len];
        for (int i = 0; i < len; i++) {
            pointsTemp[i] = points[i];
        }
        if (isRepeat(pointsTemp)) throw new IllegalArgumentException("repeat points");
        segmentList = new ArrayList<>();
        start = new ArrayList<>();
        end = new ArrayList<>();

        for (int p = 0; p < len; p++) {
            if (pointsTemp[p] == null) throw new NullPointerException("point is null!");
            if (len - p < 3) break;
            Arrays.sort(pointsTemp, p + 1, len, pointsTemp[p].slopeOrder());//按斜率排序
//            System.out.println("******" + pointsTemp[p] + "******");
//            for (int i = p + 1; i < len; i++) {
//                System.out.println(pointsTemp[i] + " -> " + pointsTemp[p].slopeTo(pointsTemp[i]));
//            }
            int num = 0;
            LineSegment lineSegment = null;
            for (int i = p + 1; i < len; i++) {
                if (i <= len - 2) {
                    if (pointsTemp[p].slopeTo(pointsTemp[i]) == pointsTemp[p].slopeTo(pointsTemp[i + 1]))
                        num++;
                    else {
                        if (num >= 2) {
                            lineSegment = createLineSegment(pointsTemp, p, num + 2, i);
                            num = 0;
                        } else {
                            lineSegment = null;
                            num = 0;
                        }
                    }
                } else if (i == len - 1) {
                    if (num >= 2) {//最后一个值等于之前的
                        lineSegment = createLineSegment(pointsTemp, p, num + 2, i);
                        num = 0;
                    }
                }
                if (lineSegment != null) {
                    start.add(ps[0]);
                    end.add(ps[ps.length - 1]);
                    segmentList.add(lineSegment);
                    count++;
                    lineSegment = null;
                }
            }
            //Arrays.sort(pointsTemp, p + 1, len);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        System.out.println(collinear.count);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
