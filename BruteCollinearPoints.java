import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;

public class BruteCollinearPoints {

    // Stores data of collinear lines found
    private int ln_no;
    private LineSegment[] lines;

    public BruteCollinearPoints(Point[] arg_points)
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

        // To detect 4 - point long line segments by iterating through all possible combinations.
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
        LineSegment[] r_lines = new LineSegment[ln_no];
        for(int i = 0; i < ln_no; i++)  r_lines[i] = lines[i];
        return r_lines;
    }

    public static void main(String[] args)
    {
        // Retrieving points
        Point[] inp_points = Point.getpoints(args);

        BruteCollinearPoints collinearPoints = new BruteCollinearPoints(inp_points);
        StdOut.println("There are " + collinearPoints.numberOfSegments() + " collinear lines in the sample. They are :");
        for (int i= 0; i < collinearPoints.numberOfSegments(); i++)
        {
            StdOut.println(collinearPoints.segments()[i]);
        }

        // Draws all points using StdDraw
        Point.drawpoints(inp_points,10);
    }
}
