import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Point2D;
import java.util.Arrays;
import edu.princeton.cs.algs4.ResizingArrayStack;
public class ConvexHull{
    
    private static Point2D[] computeHull(Point2D[] points){
        ResizingArrayStack<Point2D> stack=new ResizingArrayStack<Point2D>();
        Arrays.sort(points,Point2D.Y_ORDER);
        Point2D min=points[0];
        //System.out.println(min);
        stack.push(min);
        Arrays.sort(points,min.polarOrder());
        stack.push(points[1]);
        
        for(int i=2;i<points.length;i++){
            Point2D top=stack.pop();
            while(Point2D.ccw(stack.peek(),top,points[i])==-1)
                top=stack.pop();
            stack.push(top);
            stack.push(points[i]);
        }
        Point2D[] chull=new Point2D[stack.size()];
        int i=0;
        for(Point2D point:stack){
            //System.out.println(point);
            chull[i]=point;
            i++;
        }
        return chull;
        
//        for(Point2D point:points){
//            System.out.println(point);
//        }
    }
    
    
    public static void main(String[] args){
        
        int n=Integer.parseInt(args[0]);
        Point2D points[]=new Point2D[n];
        StdDraw.setPenColor(StdDraw.BOOK_BLUE);
        double[] x ;
        double[] y ;
        double pointx=0.0;
        double pointy=0.0;
        StdDraw.setPenRadius(.01);
        while(n>0){
            //System.out.println(StdDraw.isMousePressed());
            if(StdDraw.isMousePressed()){
                pointx=StdDraw.mouseX();
                pointy=StdDraw.mouseY();
                n--;
                points[n]=new Point2D(pointx,pointy);
                StdDraw.point(pointx,pointy);
                while(StdDraw.isMousePressed());
            }            
        }
        Point2D[] hull=computeHull(points);
        x=new double[hull.length];
        y=new double[hull.length];
        int i=0;
        for(Point2D point:hull){
            x[i]=point.x();
            y[i]=point.y();
            i++;
        }
         StdDraw.setPenColor(StdDraw.BLACK);
         StdDraw.setPenRadius(.002);
        StdDraw.polygon(x, y);
        
        
        
        
    }
    
}
