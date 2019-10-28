import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
public class FastCollinearPoints {
    
    private ArrayList<LineSegment> ls; 
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        
        if(points==null)
            throw new IllegalArgumentException();        
        else{
            
            int l=points.length;
            Point[] copy=new Point[l];
            Point[] copyforsort=new Point[l];
            
            for(int i=0;i<l;i++){
                if(points[i]==null)
                    throw new IllegalArgumentException();
                copy[i]=points[i];
                copyforsort[i]=points[i];
                
                for(int j=i+1;j<l;j++){                    
                    if(points[i].compareTo(points[j])==0)
                        throw new IllegalArgumentException();
                }
            }
            
            ls=new ArrayList<LineSegment>();
            ArrayList<Point> line=new ArrayList<Point>();
            for(int i=0;i<l;i++){
                
                Arrays.sort(copyforsort,copy[i].slopeOrder());
                for(int j=0;j<l;j++){
                    line.clear();
                    while(j<l-1&&(copy[i].slopeTo(copyforsort[j])==copy[i].slopeTo(copyforsort[j+1]))){
                        
                        line.add(copyforsort[j]);
                        j++;
                    }
                    line.add(copyforsort[j]);
                    if(line.size()>2){
                        line.add(copy[i]);
                        Collections.sort(line);
                        
                        if(copy[i].compareTo(line.get(0))==0){
                            LineSegment temp=new LineSegment(line.get(0),line.get(line.size()-1));
                            ls.add(temp);
                           
                        }
                    }
                }
                
            }
            
        }
    }
    public int numberOfSegments(){        // the number of line segments
        return ls.size();}
    public LineSegment[] segments(){                // the line segments
        return ls.toArray(new LineSegment[ls.size()]);}
}