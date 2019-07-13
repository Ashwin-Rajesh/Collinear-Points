import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    // Initialises value of x, y coordinates
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Draws the point
    public void draw() {StdDraw.point(x, y);}

    // Draws line to a point
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that) {
        if(this.x == that.x)
        {
            if(this.y == that.y)
                return Double.NEGATIVE_INFINITY;
            else
                return Double.POSITIVE_INFINITY;
        }

        if(this.y == that.y)
        {
            if(this.x == that.x)
                return Double.NEGATIVE_INFINITY;
        }
        double slope = (double)(this.y - that.y)/(this.x - that.x);
        return slope;
    }

    public int compareTo(Point that) {
        if(this.y == that.y)
        {
            return this.x - that.x;
        }
        else
        {
            return this.y - that.y;
        }
    }

    public Comparator<Point> slopeOrder() {
        return new compr();
    }

    private class compr implements Comparator<Point> {
        public int compare(Point A, Point B)
        {
            if(slopeTo(A) > slopeTo(B)) return 1;
            if(slopeTo(A) < slopeTo(B)) return -1;
            return 0;
        }
    }

    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int num_pt = in.readInt();
        Point[] points = new Point[num_pt];
        int max_coord = 0;
        StdOut.println(" Loaded file - " + args[0] + " . Reading file for " + num_pt + "points.");
        for (int i = 0; i < num_pt; i++) {
            int x_coord = in.readInt();
            int y_coord = in.readInt();
            points[i] = new Point(x_coord, y_coord);
            if (-x_coord > max_coord) max_coord = -x_coord;
            if (x_coord > max_coord) max_coord = x_coord;
            if (-y_coord > max_coord) max_coord = -y_coord;
            if (y_coord > max_coord) max_coord = y_coord;
            StdOut.print((i+1));
        }
        /*
        int max_coord = 0;
        if(args.length != 1)
        {
            throw new IllegalArgumentException(" One and only one argument, for number of points is expected.");
        }

        int num_pt = Integer.parseInt(args[0]);

        StdOut.println(" Enter values of x and y coordinates.");
        Point points[] = new Point[num_pt];
        for(int i = 0; i < num_pt; i++)
        {
            int x_coord, y_coord;
            StdOut.print(" Enter x-coordinate of point number " + (i + 1) + " : ");
            x_coord = StdIn.readInt();
            StdOut.print(" Enter y-coordinate of point number " + (i + 1) + " : ");
            y_coord = StdIn.readInt();
            points[i] = new Point(x_coord, y_coord);
            StdOut.println();
            if (-x_coord > max_coord) max_coord = -x_coord;
            if (x_coord > max_coord) max_coord = x_coord;
            if (-y_coord > max_coord) max_coord = -y_coord;
            if (y_coord > max_coord) max_coord = y_coord;

        }
        */
        for(int i= 0; i < num_pt; i++)
        {
            StdOut.println(" String representation of point " + i + ":" + points[i].toString());
        }
        StdOut.println("-----------------------------------------------------------------------------");

        for(int i = 0; i < num_pt; i++)
        {
            for(int j = i + 1; j < num_pt; j++)
            {
                StdOut.println(" Slope between points " + points[i].toString() + " and " + points[j].toString() + " is   : " + points[i].slopeTo(points[j]));
            }
        }
        StdOut.println("-----------------------------------------------------------------------------");

        Arrays.sort(points, points[0].slopeOrder());

        for(int i= 0; i < num_pt; i++)
        {
            StdOut.println(" String representation of point " + i + " after sort :" + points[i].toString());
        }

        // Initialising Draw
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-max_coord - (double) max_coord / 20, max_coord + (double) max_coord / 20);
        StdDraw.setYscale(-max_coord - (double) max_coord / 20, max_coord + (double) max_coord / 20);

        // Drawing axes
        StdDraw.setPenColor(128, 128, 128);
        new Point(0, max_coord).drawTo(new Point(0, -max_coord));
        new Point(max_coord, 0).drawTo(new Point(-max_coord, 0));

        // Drawing all possible lines between points
        StdDraw.setPenColor(0, 0, 0);
        for (int i = 0; i < num_pt; i++) {
            for (int j = i + 1; j < num_pt; j++) {
                points[i].drawTo(points[j]);
            }
        }

        // Drawing all points
        StdDraw.setPenColor(102,255,107);
        StdDraw.setPenRadius((double)max_coord/200);
        for(int i = 0; i < num_pt; i++)
        {
            points[i].draw();
        }
        StdDraw.show();
    }
}