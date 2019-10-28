/** Created by Mohd Umar Alam
  * Date: 6'aug'18
  * Execution: Percolation API. Run through PercolationStats API
  * 
  */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation
{   
    private boolean grid[][]; private int count,size;
    private WeightedQuickUnionUF uf,u;
        public Percolation(int n){
            if(n<=0){
                throw new IllegalArgumentException("n is not in range");
            }
            u=new WeightedQuickUnionUF(n*n+1);
        uf=new WeightedQuickUnionUF(n*n+2);
            grid=new boolean[n+1][n+1];
        for(int i=1;i<=n;i++)
            for(int j=0;j<=n;j++)
             grid[i][j]=false;
        count=0;
        size=n;
              
    }
        /* validate that p is a valid index*/
    private void validate(int r,int c) {
       
        if (r<= 0 || r > size||c<=0||c>size) {
            throw new IllegalArgumentException("row index i out of bounds");  
        }
    }

    public void open(int row,int col){
        
        if(!isOpen(row,col)){
            
        grid[row][col]=true;
        if(row==1){
             u.union(size*size,convert(row,col)); 
            uf.union(size*size,convert(row,col));   
        }
        if(row!=1){
            if(isOpen(row-1,col)){
                u.union(convert(row-1,col),convert(row,col));
        uf.union(convert(row-1,col),convert(row,col));
            }
        }
        if(row!=size){
            if(isOpen(row+1,col)){
                u.union(convert(row+1,col),convert(row,col));
        uf.union(convert(row+1,col),convert(row,col));
        }
        }
        if(col!=1){
            if(isOpen(row,col-1)){
                u.union(convert(row,col-1),convert(row,col));
        uf.union(convert(row,col-1),convert(row,col));
            }
        }
        if(col!=size){
            if(isOpen(row,col+1)){
                u.union(convert(row,col+1),convert(row,col));
        uf.union(convert(row,col+1),convert(row,col));
            }
        }
        if(row==size){
            uf.union(convert(row,col),size*size+1);
          /*per[c][0]=row;
          per[c][1]=col;
          c++;
            }*/
        }
                count++;
        }
    }
    public boolean isOpen(int row,int col){
        validate(row,col);
        return grid[row][col];
           
    }
    private int convert(int row,int col){
        row--; col--;
        return row*size+col;
    }        
    public boolean isFull(int row,int col){
         validate(row,col);
        
        if(u.connected(convert(row,col),size*size))
               return true;
        else
               return false;
    }
    public int numberOfOpenSites(){
        return count;
    }
    public boolean percolates(){
        /*for(int i=0;i<c;i++){
            call=call+2;
            if(uf.connected(convert(per[i][0],per[i][1]),size*size)){
            return true;
        }  
        }
        return false;*/
      
        if(uf.connected(size*size,size*size+1))
        return true;
        else
            return false;
    }
    /*public void calls()
    {
        StdOut.println("union calls "+ca);
        StdOut.println("find calls "+call);
        StdOut.println(c);
    }*/
    }

        
           
           
                                          
    
           