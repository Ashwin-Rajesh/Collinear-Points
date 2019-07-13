import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {

    private int ln_no;
    private LineSegment[] lines;

    public BruteCollinearPoints(Point[] points)
    {
        final int pt_no = points.length;
        lines = new LineSegment[pt_no / 2 - 1];
        ln_no = 0;

        Arrays.sort(points,Point::compareTo);

        // To handle corner case of occurrence of duplicate point
        for(int i = 0; i < pt_no; i++)
            if(i < pt_no -1)
                if(points[i].slopeTo(points[i+1]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException(" Duplicate points are not permitted.");

        // To detect 4 - point long line segments.
        for(int i = 0; i < pt_no - 3; i++)
        {
            for(int j = i + 1; j < pt_no - 2; j++)
            {
                for(int k = j + 1; k < pt_no - 1; k++)
                {
                    for(int l = k + 1; l < pt_no; l++)
                    {
                        if(points[i].slopeTo(points[j]) == points[i].slopeTo(points[k]) && points[i].slopeTo(points[k]) == points[i].slopeTo(points[l]))
                        {
                            lines[ln_no] = new LineSegment(points[i],points[l]);
                            ln_no++;
                        }
                    }
                }
            }
        }
    }

    public int numberOfSegments()
    {
        return ln_no;
    }

    public LineSegment[] segments()
    {
        LineSegment r_lines[] = new LineSegment[ln_no];
        for(int i = 0; i < ln_no; i++)  r_lines[i] = lines[i];
        return r_lines;
    }

    public static void main(String[] args)
    {
        final int num_pt = Integer.parseInt(args[0]);
        int num_ln;
        int max_coord = 0;
        BruteCollinearPoints brute;
        Point[] points = new Point[num_pt];

        for(int i = 0; i < num_pt; i++)
        {
            int x_coord, y_coord;
            StdOut.print(" Enter x-coordinate of point number " + (i+1) + " : ");
            x_coord = StdIn.readInt();
            StdOut.print(" Enter y-coordinate of point number " + (i+1) + " : ");
            y_coord = StdIn.readInt();
            points[i] = new Point(x_coord,y_coord);
            StdOut.println();
            if(-x_coord > max_coord)     max_coord = -x_coord;
            if(x_coord > max_coord)     max_coord = x_coord;
            if(-y_coord > max_coord)     max_coord = -y_coord;
            if(y_coord > max_coord)     max_coord = y_coord;
        }
        StdOut.println("-----------------------------------------------------------------------------");

        Arrays.sort(points,Point::compareTo);

        StdOut.println(" These are the points - ");
        for(int i = 0; i < num_pt; i++)
        {
            StdOut.println(points[i].toString());
        }
        StdOut.println("-----------------------------------------------------------------------------");

        brute = new BruteCollinearPoints(points);

        num_ln = brute.numberOfSegments();
        LineSegment[] lines = brute.segments();

        StdOut.println(" There are " + num_ln + " collinear lines in the given sample.");
        StdOut.println("-----------------------------------------------------------------------------");

        StdOut.println(" These are the lines:");
        for(int i = 0; i < num_ln; i++)
        {
            StdOut.println(lines[i].toString());
        }

        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-max_coord - (double)max_coord/20, max_coord + (double)max_coord/20);
        StdDraw.setYscale(-max_coord - (double)max_coord/20, max_coord + (double)max_coord/20);

        // Drawing axes
        StdDraw.setPenColor(128,128,128);
        new Point(0, max_coord).drawTo(new Point(0, -max_coord));
        new Point(max_coord, 0).drawTo(new Point(-max_coord, 0));

        // Drawing all possible lines between points
        StdDraw.setPenColor(0,0,0);
        for(int i = 0; i < num_pt; i++)
        {
            for(int j= i + 1; j < num_pt; j++) {
                points[i].drawTo(points[j]);
            }
        }

        // Drawing all points
        StdDraw.setPenRadius((double)max_coord/200);
        for(int i = 0; i < num_pt; i++)
        {
            points[i].draw();
        }
        StdDraw.show();
    }
}