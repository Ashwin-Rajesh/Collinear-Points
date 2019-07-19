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

    // Returns slope to some other point
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
            else
                return 0;
        }
        double slope = (double)(this.y - that.y)/(this.x - that.x);
        return slope;
    }

    // Compares with another point - First on the basis of y-coordinates, then breaking ties using x-coordinates
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

    // Compares two points - based on slope made with the reference point, from which function is called
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

    // Returns a simple string giving x and y coordinates of point
    public String toString() { return "(" + x + ", " + y + ")";}

    // Static function to retrieve array of points from file or StdIn.
    public static Point[] getpoints(String[] args)
    {
        if(args == null)
        {
            StdOut.println(" Illegal arguments received by Points.getpoints(). Getting arguments from StdIn.");
            StdOut.println(" Enter mode of data entry f/c (f for reading from file, c for from StdIn) :");
            args = new String[2];
            args[0] = StdIn.readString();
            if(args[0].equals("f"))
            {
                StdOut.println(" Enter name of file for data entry     :");
                args[1] = StdIn.readString();
            }
            else if(args[0].equals("c"))
            {
                StdOut.println(" Enter number of points to be entered  :");
                args[1] = StdIn.readString();
            }
            else
            {
                StdOut.println(" No legal arguments received. Default values are given.");
                args[0] = "c";
                args[1] = "5";
            }
        }

        Point[] points;

        // Read from file mode
        if(args[0].equals("f"))              // f for file
        {
            // Initialising reader, and array.
            In read = new In(args[1]);
            final int num_pt = read.readInt();
            points = new  Point[num_pt];
            StdOut.println("Loaded file '" + args[1] + "'. Reading points...");

            // Reading points
            for(int i = 0; i < num_pt; i++)
            {
                int xcoord = read.readInt();
                int ycoord = read.readInt();
                points[i] = new Point(xcoord,ycoord);
                StdOut.println((i + 1) + ") " + points[i].toString());
            }

            if(read.hasNextChar())
                StdOut.println(num_pt + " points were read. File was not completely read.");
            else
                StdOut.println(num_pt + " points were read. File read completely");
            read.close();
        }
        else if(args[0].equals("c"))         // c for console
        {
            // Initialising array
            final int num_pt = Integer.parseInt(args[1]);
            points = new Point[num_pt];
            StdOut.println(" Reading console for " + args[1] + " points.");

            // Reading points from StdIO
            for(int i = 0; i < num_pt; i++)
            {
                StdOut.print((i + 1) + "/" + num_pt + " ) ");
                int x_coord = StdIn.readInt();
                StdOut.print((i + 1) + "/" + num_pt + " ) ");
                int y_coord = StdIn.readInt();
                StdOut.println();
                points[i] = new Point(x_coord,y_coord);
            }
        }
        else
        {
            throw new IllegalArgumentException(" Mode not recognized.");
        }

        return points;
    }

    public static void drawpoints(Point[] arg_points, int size)
    {

        final int num_pt = arg_points.length;

        // Initialising StdDraw
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-size, size);
        StdDraw.setYscale(-size, size);

        // Drawing axes and border
        StdDraw.setPenColor(0,0,0);
        StdDraw.line(0,(double)-size * 98 / 100, 0, (double) size * 98 / 100);
        StdDraw.line((double) -size * 98 / 100, 0, (double) size * 98 / 100, 0);
        StdDraw.rectangle(0,0,(double) size * 99 / 100, (double) size * 99 / 100);

        // Drawing all lines joining the points
        StdDraw.setPenColor(128,128,128);
        StdDraw.setPenRadius((double) size / 5000);
        for(int i = 0; i < num_pt; i++)
        {
            for(int j = i + 1; j < num_pt; j++)
            {
                arg_points[i].drawTo(arg_points[j]);
            }
        }

        // Drawing the points
        StdDraw.setPenColor(153, 204, 0);
        StdDraw.setPenRadius((double) size / 500);
        for(int i = 0; i < num_pt; i++)
        {
            arg_points[i].draw();
        }

        StdDraw.show();
    }

    public static void main(String[] args) {
        Point[] points = Point.getpoints(args);

        Arrays.sort(points,points[2].slopeOrder());

        for (int i = 0; i < points.length; i++)
        {
            StdOut.println(points[i].toString());
        }

        Point.drawpoints(points,10);
    }
}
