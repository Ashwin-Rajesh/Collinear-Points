import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class FastCollinearPoints {

    // Stores data of collinear lines found.
    private int ln_no;
    private LineSegment[] lines;

    // Constructor
    public FastCollinearPoints(Point[] arg_points)
    {
        // To handle pass of null argument
        if(arg_points == null)
            throw new IllegalArgumentException(" Argument passed was null");

        Point[] points = arg_points.clone();
        final int pt_no = arg_points.length;
        lines = new LineSegment[pt_no * (pt_no - 1) / 2];
        ln_no = 0;

        // To handle occurrence of null values in argument array
        for(int i= 0; i < pt_no; i++)
            if(points[i] == null)
                throw new IllegalArgumentException(" Array with null elements was passed.");

        Arrays.sort(points,Point::compareTo);

        // To handle occurrence of duplicate
        for(int i = 0; i < pt_no; i++)
            if (i < pt_no - 1)
                if (points[i].slopeTo(points[i + 1]) == Double.NEGATIVE_INFINITY)
                    throw new IllegalArgumentException(" Duplicate points are not permitted.");

        // To handle argument being passed with less than 4 elements.
        if(pt_no < 4)
            return;

        for (int i = 0; i < pt_no; i++)
        {
            Point[] sample_points = points.clone();
            Arrays.sort(sample_points, points[i].slopeOrder());

            for(int j = 1; j < pt_no - 1; j++)
            {
                int ln_len = 1;                 // Variable stores length of line
                boolean val_flag = true;        // Variable stores if the line has been read before (if it contains a point < origin point)

                if(sample_points[0].compareTo(sample_points[j]) > 0)        val_flag = false;
                while(sample_points[0].slopeTo(sample_points[j]) == sample_points[0].slopeTo(sample_points[j + 1]))
                {
                    j++;
                    ln_len++;

                    // Invalidate the line if a point that was checked before is found on the line.
                    if(sample_points[0].compareTo(sample_points[j]) > 0)
                        val_flag = false;

                    if(j == pt_no - 1)
                        break;
                }

                if(val_flag && ln_len >= 3)
                {
                    lines[ln_no] = new LineSegment(sample_points[0],sample_points[j]);
                    ln_no++;
                }
            }
        }
    }

    // Returns number of lines found
    public int numberOfSegments() {
        return ln_no;
    }

    // Returns all lines found in the form of LineSegment objects
    public LineSegment[] segments()
    {
        LineSegment r_lines[] = new LineSegment[ln_no];
        for (int i = 0; i < ln_no; i++) r_lines[i] = lines[i];
        return r_lines;
    }

    public static void main(String[] args)
    {
        // Retrieving points
        Point inp_points[] = Point.getpoints(args);

        FastCollinearPoints collinearPoints = new FastCollinearPoints(inp_points);
        StdOut.println("There are " + collinearPoints.numberOfSegments() + " collinear lines in the sample. They are :");
        for (int i= 0; i < collinearPoints.numberOfSegments(); i++)
        {
            StdOut.println(collinearPoints.segments()[i]);
        }

        // Drawing all points using StdDraw
        Point.drawpoints(inp_points,10);
    }
}
