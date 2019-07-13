import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {

    private int ln_no;
    private LineSegment[] lines;

    public FastCollinearPoints(Point[] points)
    {
        final int pt_no = points.length;
        ln_no = 0;
        lines = new LineSegment[pt_no * (pt_no - 1) / 2];

        Arrays.sort(points,Point::compareTo);

        // To occurrence of duplicate points
        for(int i = 0; i < pt_no; i++)
            if(i < pt_no -1)
                if(points[i].slopeTo(points[i+1]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException(" Duplicate points are not permitted.");

        for(int i = 0; i < pt_no - 3; i++)
        {
            Point[] sample_points = Arrays.copyOfRange(points, i + 1, pt_no);
            Arrays.sort(sample_points,points[i].slopeOrder());

            for(int j = 0; j < pt_no - i -3; j++)
            {
                if(sample_points[j].slopeTo(points[i]) == sample_points[j + 1].slopeTo(points[i]) && sample_points[j].slopeTo(points[i]) == sample_points[j + 2].slopeTo(points[i]))
                {
                    lines[ln_no] = new LineSegment(points[i], sample_points[j + 2]);
                    ln_no++;
                }
            }
        }
    }

    public int numberOfSegments()       { return ln_no;}

    public LineSegment[] segments()
    {
        LineSegment r_lines[] = new LineSegment[ln_no];
        for(int i = 0; i < ln_no; i++)  r_lines[i] = lines[i];
        return r_lines;
    }

    public static void main(String[] args) {

        int max_coord = 0;
        // This code retrieves information about points from a file.
        In in = new In(args[0]);
        int num_pt = in.readInt();
        Point[] points = new Point[num_pt];

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
        // This code retrieves information about points from StdIn.
        int num_pt = args[0];
        int max_coords = 0;
        Point[] points = new Point[num_pt];

        for (int i = 0; i < num_pt; i++) {
            int x_coord, y_coord;
            StdOut.print(" Enter x-coordinate of point number " + (i + 1) + " of " + num_pt + " : ");
            x_coord = StdIn.readInt();
            StdOut.print(" Enter y-coordinate of point number " + (i + 1) + " of " + num_pt + " : ");
            y_coord = StdIn.readInt();
            points[i] = new Point(x_coord, y_coord);
            StdOut.println();
            if (-x_coord > max_coord) max_coord = -x_coord;
            if (x_coord > max_coord) max_coord = x_coord;
            if (-y_coord > max_coord) max_coord = -y_coord;
            if (y_coord > max_coord) max_coord = y_coord;
        }
        */
        StdOut.println("-----------------------------------------------------------------------------");

        Arrays.sort(points, Point::compareTo);

        StdOut.println(" These are the points - ");
        for (int i = 0; i < num_pt; i++) {
            StdOut.println(points[i].toString());
        }
        StdOut.println("-----------------------------------------------------------------------------");

        FastCollinearPoints Collinear_points = new FastCollinearPoints(points);
        final int num_ln = Collinear_points.numberOfSegments();
        LineSegment[] lines = Collinear_points.segments();

        StdOut.println(" There are " + num_ln + " collinear lines in the given sample.");
        StdOut.println("-----------------------------------------------------------------------------");

        StdOut.println(" These are the lines:");
        for (int i = 0; i < num_ln; i++) {
            StdOut.println(lines[i].toString());
        }

        // Initialising StdDraw
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
        StdDraw.setPenColor(255,43,55);
        StdDraw.setPenRadius((double)max_coord/200);
        for(int i = 0; i < num_pt; i++)
        {
            points[i].draw();
        }
        StdDraw.show();
    }
}
