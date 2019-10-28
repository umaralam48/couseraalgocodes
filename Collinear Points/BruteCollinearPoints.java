import java.util.ArrayList;
import java.util.Arrays;
public class BruteCollinearPoints {
    private ArrayList<LineSegment> ls; private Point[] copy;
    public BruteCollinearPoints(Point[] points){   // finds all line segments containing 4 points
        
        if(points==null)
            throw new IllegalArgumentException();        
        else{
            int l=points.length;
            copy=new Point[l];
            for(int i=0;i<points.length;i++){
                if(points[i]==null)
                    throw new IllegalArgumentException();
                copy[i]=points[i];
                for(int j=i+1;j<points.length;j++){
                    
                    if(points[i].compareTo(points[j])==0)
                        throw new IllegalArgumentException();
                }
            }
             ls=new ArrayList<LineSegment>();
            
            for(int x=0;x<l-3;x++){
                for(int y=x+1;y<l-2;y++){
                    for(int z=y+1;z<l-1;z++){
                for(int i=z+1;i<l;i++){
                
                if((copy[x].slopeTo(copy[y])==copy[x].slopeTo(copy[z]))&&(copy[x].slopeTo(copy[z])==copy[x].slopeTo(copy[i]))){
                    Point[] line={copy[x],copy[y],copy[z],copy[i]};
                    Arrays.sort(line);
                    //printline(line);
                    //if(copy[x].compareTo(line[0])==0){
                        LineSegment temp=new LineSegment(line[0],line[3]);
                        ls.add(temp);
               // }
                    
                }               
                }}}}
        }    
    }
    public int numberOfSegments(){        // the number of line segments
        
        return ls.size();
        
    }
    public LineSegment[] segments(){                // the line segments
        
        return ls.toArray(new LineSegment[ls.size()]);
    }
    private void printline(Point[] line){
        for(int i=0;i<line.length;i++){
            System.out.print(line[i]);
        }
        System.out.println();
    }
}