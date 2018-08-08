/** Created by Mohd Umar Alam
  * Date: 6'aug'18
  * Execution: Percolation API. Run through PercolationStats API
  * 
  */
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;
public class PercolationStats{
    private double data[],mn,vr;
   
        public PercolationStats(int n,int trials){
            if(n<=0||trials<=0){
                throw new IllegalArgumentException("n is not in range");
            }
        data=new double[trials];
        for(int i=0;i<trials;i++){
            Percolation perc=new Percolation(n);
            while(!perc.percolates()){
                int r=StdRandom.uniform(1,n+1);
                int c=StdRandom.uniform(1,n+1);
                if(!perc.isOpen(r,c)){
                perc.open(r,c);
            }
            }
            data[i]=(double)perc.numberOfOpenSites()/(n*n);
        }
    }
    public double mean(){
        if(mn==0){
        mn=StdStats.mean(data);
        }
        return mn;
    }
    public double stddev(){
        if(vr==0){
       vr=StdStats.stddev(data);
        }
       return vr;
    }
    public double confidenceLo(){
        double r=mean()-1.96*stddev()/Math.sqrt(data.length);
        return r;
    }
    public double confidenceHi(){
        double r=mean()+1.96*stddev()/Math.sqrt(data.length);
        return r;
    }
    public static void main(String[] args){
        int n=Integer.parseInt(args[0]);
        int t=Integer.parseInt(args[1]);
        PercolationStats st=new PercolationStats(n,t);
        StdOut.println(st.mean());
        StdOut.println(st.stddev());
        StdOut.println(st.confidenceLo());
        StdOut.println(st.confidenceHi());
      
        
    }
}
        
        
            
        
        