import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;

public class PointSET {
    
    private SET<Point2D> m;
    
    // construct an empty set of points 
    public PointSET(){                              
        
        m=new SET<Point2D>();
        
    }
    
    // is the set empty? 
    public boolean isEmpty()                      
    {
        return m.isEmpty();
    }
    
    // number of points in the set 
    public int size()                         
    {
        return m.size();
    }
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p)              
    {
        if(p==null)
            throw new IllegalArgumentException();
        m.add(p);
    }
    
    // does the set contain point p? 
    public boolean contains(Point2D p)            
    {
        if(p==null)
            throw new IllegalArgumentException();
        return m.contains(p);
    }
    
    // draw all points to standard draw 
    public void draw()                       
    {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(.01);
        for(Point2D point:m){
            StdDraw.point(point.x(),point.y());
        }
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect){
        if(rect==null) throw new IllegalArgumentException();
        ArrayList<Point2D> containedPoints=new ArrayList<Point2D>();
        for(Point2D point:m){
            if(rect.contains(point))
                containedPoints.add(point);         
        }
        return containedPoints;
    }             
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p){
        
        //long start = System.currentTimeMillis();        
        if(p==null)
            throw new IllegalArgumentException();
        if(m.isEmpty())
            return null;
        double dmin=2.0; Point2D nearest=p;
        for(Point2D point:m){
            if(point.equals(p)){
                nearest=point;
                break;
            }
                
            double dis=p.distanceSquaredTo(point);
            if(dis<dmin){
                dmin=dis;
                nearest=point;
            }
        }
        //System.out.println("Nearest takes " + 
        //                 (System.currentTimeMillis() - start) + "ms");
        return nearest;
    }             
    
    // unit testing of the methods (optional) 
    public static void main(String[] args)   {
        String filename = args[0];
        In in = new In(filename);
        PointSET set = new PointSET();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            set.insert(p);
        }
        //kdtree.draw();
        for(int i=1;i<10;i++){
            
            double x = StdRandom.uniform(0.0, 1.0);
            double y = StdRandom.uniform(0.0, 1.0);
            long start=System.currentTimeMillis(); 
            Point2D nearest=set.nearest(new Point2D(x,y));
            System.out.println(System.currentTimeMillis()-start+" ms"); 
            StdOut.printf("Nearest Neighbour of %8.6f %8.6f is %8.6f %8.6f \n", x, y,nearest.x(),nearest.y());
            
        }
    }              
}