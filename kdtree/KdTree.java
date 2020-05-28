import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdOut;
import java.util.ArrayList;
import java.util.Scanner;

public class KdTree {
    
    private Node root; private int size;
    private double xmin,xmax,ymin,ymax;
    
    private static class Node{
        
        private Point2D p;      
        private RectHV rect;    
        private Node lb;        
        private Node rt; 
        
        public Node(Point2D p,RectHV rect){
            this.p=p;
            this.rect=rect;
        } 
    }
    
    // construct an empty set of points 
    public KdTree(){
        this.size=0;
        this.root=null;
    }
    
    // is the set empty? 
    public boolean isEmpty(){
        if(this.size==0)
            return true;
        else
            return false;
    }    
    
    // number of points in the set 
    public int size(){
        return this.size;
    }                         
    
    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p){
        if(!contains(p)){
            if(p==null)
                throw new IllegalArgumentException();
            xmax=ymax=1.0;
            xmin=ymin=0.0;
            this.root=insert(root,p,true);
            this.size++;
        }
    } 
    
    private Node insert(Node x,Point2D p,boolean o){
        //o is true then point divides by x co-ordinate
        if(x==null) return new Node(p,new RectHV(xmin,ymin,xmax,ymax));
        if(o){
            if(p.x()<x.p.x()){ 
                xmax=x.p.x();
                x.lb=insert(x.lb,p,!o);
            }
            else {
                xmin=x.p.x();
                x.rt=insert(x.rt,p,!o);
            }
            return x;
        }
        else{
            if(p.y()<x.p.y()) {
                ymax=x.p.y();
                x.lb=insert(x.lb,p,!o);
            }
            else{
                ymin=x.p.y();
                x.rt=insert(x.rt,p,!o);
            }
            return x;
        }
    }
    
    // does the set contain point p? 
    public boolean contains(Point2D p){
        if(p==null)
            throw new IllegalArgumentException();
        return contains(this.root,p,true);
    }            
    
    private boolean contains(Node x,Point2D p, boolean o){
        if(x==null) return false;
        
        if(x.p.equals(p)) return true;
        
        if(o){
            if(p.x()<x.p.x()) return contains(x.lb,p,!o);
            else return contains(x.rt,p,!o);
        }
        else{
            if(p.y()<x.p.y()) return contains(x.lb,p,!o);
            else return contains(x.rt,p,!o);
        }
    }
    
    // draw all points to standard draw
    public void draw(){        
        draw(root,true);
    }
    
    private void draw(Node x,boolean o){
        if(x!=null){
            StdDraw.setPenColor(StdDraw.BLACK);
            StdDraw.setPenRadius(0.01);
            x.p.draw();
            StdDraw.setPenRadius();
            if(o){
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.line(x.p.x(),x.rect.ymin(),x.p.x(),x.rect.ymax());                
            }
            else{
                StdDraw.setPenColor(StdDraw.BLUE);
                StdDraw.line(x.rect.xmin(),x.p.y(),x.rect.xmax(),x.p.y());               
            }
            draw(x.lb,!o);
            draw(x.rt,!o);
        }
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect){
        //long start = System.currentTimeMillis(); 
        if(rect==null) throw new IllegalArgumentException();
        // start of function
        ArrayList<Point2D> points=new ArrayList<Point2D>();
        range(root,rect,points);
        
        // end of function 
        
        // ending time 
        // long end = System.currentTimeMillis(); 
        //System.out.println("Range takes " + 
        //                 (end - start) + "ms");
        return points;        
    }
    
    private void range(Node x,RectHV rect, ArrayList<Point2D> points){
        if(x!=null){
            if(x.rect.intersects(rect)){
                if(rect.contains(x.p))
                    points.add(x.p);
                range(x.lb,rect,points);
                range(x.rt,rect,points);
            }            
        }
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p){
        
        if(p==null) throw new IllegalArgumentException();
        //long start = System.currentTimeMillis(); 
        if(root==null)
            return null;
        Point2D nearest=nearest(root,p,root.p);
        // end of function 
        
        // ending time 
        //System.out.println("Nearest takes " + 
        //                 (System.currentTimeMillis() - start) + "ms");
        return nearest;
    }      
    
    private Point2D nearest(Node x, Point2D p,Point2D min){
        
        if(x!=null&&x.rect.distanceSquaredTo(p)<min.distanceSquaredTo(p)){
            
//            StdDraw.setPenColor(StdDraw.PINK);
//            StdDraw.setPenRadius(0.01);
//            x.rect.draw();
//            x.p.draw();
//            StdDraw.setPenColor(StdDraw.GREEN);
//            min.draw();
//            StdDraw.setPenColor(StdDraw.RED);
//            p.draw();             
//            int y=new Scanner(System.in).nextInt();
            
            if(x.p.distanceSquaredTo(p)<min.distanceSquaredTo(p))
                min=x.p;
            if(x.lb!=null&&x.rt!=null){
                if(x.lb.rect.distanceSquaredTo(p)<x.rt.rect.distanceSquaredTo(p)){
                    min=nearest(x.lb,p,min);
                    min=nearest(x.rt,p,min);
                }
                else{
                    min=nearest(x.rt,p,min);
                    min=nearest(x.lb,p,min);
                }
            }
            else{               
                min=nearest(x.rt,p,min);
                min=nearest(x.lb,p,min);
            }            
        }
        return min;
    }
    
    // unit testing of the methods (optional) 
    public static void main(String[] args) {
        // initialize the data structures from file
        String filename = args[0];
        In in = new In(filename);
        KdTree kdtree = new KdTree();
        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }
        kdtree.draw();
        //for(int i=1;i<10;i++){
        
        //  double x = StdRandom.uniform(0.0, 1.0);
        //double y = StdRandom.uniform(0.0, 1.0);
        long start=System.currentTimeMillis(); 
        Point2D nearest=kdtree.nearest(new Point2D(0.78,0.31));
        System.out.println(System.currentTimeMillis()-start+" ms"); 
        StdOut.printf("Nearest Neighbour of %8.6f %8.6f is %8.6f %8.6f \n", 0.78, 0.31,nearest.x(),nearest.y());
        
        //}
    }       
}