import java.util.Iterator;
import java.util.ArrayList;
import edu.princeton.cs.algs4.StdRandom;
public class Board {
    byte[][] tiles; int n;
    int[] pos0=new int[2];
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(byte[][] tiles){
        
        int l=tiles.length;
        
        n=l;
        this.tiles=new byte[n][n];
        
        for(int i=0;i<l;i++){
            for(int j=0;j<l;j++){
                this.tiles[i][j]=(byte)tiles[i][j];
                if(tiles[i][j]==0){
                    pos0[0]=i;
                    pos0[1]=j;
                }                
            }
        }
    }
    
    private boolean swap(int i,int j,int u,int v){
        try{
            byte temp=this.tiles[i][j];
            this.tiles[i][j]=this.tiles[u][v];
            this.tiles[u][v]=temp;
            return true;
        }
        catch(Exception e){
            return false;
        }
    }
    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", tiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }
    // board dimension n
    public int dimension(){
        return n;
    }
    
    // number of tiles out of place
    public int hamming(){
        int count=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j]!=0&&tiles[i][j]!=i*n+j+1)
                    count++;
            }
        }
        return count;
    }
    
    // sum of Manhattan distances between tiles and goal
    public int manhattan(){
        int distance=0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j]==0)
                    continue;
                int diff=Math.abs(tiles[i][j]-(i*n+j+1));
                distance+=diff/n+diff%n;
                //System.out.println(diff+" "+distance);
            }
        }
        return distance;    
    }
    
    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j]!=0&&tiles[i][j]!=i*n+j+1)
                    return false;
            }
        }
        return true;
    }
    
    // does this board equal y?
    public boolean equals(Object y){
        if(y==null)
            return false;
        Board otherBoard=(Board)y;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if(tiles[i][j]!=otherBoard.tiles[i][j])
                    return false;
            }
        }
        return true;
    }
    
    // all neighboring boards
    public Iterable<Board> neighbors(){
        int i=pos0[0]; int j=pos0[1];
         ArrayList<Board> neighbours=new ArrayList<Board>();
        if(swap(i,j,i-1,j)){
            neighbours.add(new Board(this.tiles));
            swap(i,j,i-1,j);
        }
        if(swap(i,j,i+1,j)){
            neighbours.add(new Board(this.tiles));
            swap(i,j,i+1,j);
        }
        if(swap(i,j,i,j-1)){
            neighbours.add(new Board(this.tiles));
            swap(i,j,i,j-1);
        }
        if(swap(i,j,i,j+1)){
            neighbours.add(new Board(this.tiles));
            swap(i,j,i,j+1);
        }
        
        return neighbours;
    }
    
    
    
    // a board that is obtained by exchanging any pair of tiles
    public Board twin(){
        
        int i=StdRandom.uniform(n);
        int j=StdRandom.uniform(n);
        int u=StdRandom.uniform(n);
        int v=StdRandom.uniform(n);
        swap(i,j,u,v);
        Board newBoard=new Board(this.tiles);
        swap(i,j,u,v);
        return newBoard;
        
    }
    
    // unit testing (not graded)
    public static void main(String[] args){
    
        byte[][] tiles={{1,0,3},{4,2,5},{7,8,6}};
        //int[][] tiles={{1,2,3},{4,5,6},{7,8,0}};
        Board myBoard=new Board(tiles);
        System.out.println(myBoard.toString());   
        for(Board b:myBoard.neighbors())
        System.out.println(b.toString()); 
        
        System.out.println(myBoard.twin().toString());   
         System.out.println("Is Goal? : "+myBoard.isGoal());
          System.out.println("Manhattan : "+myBoard.manhattan());  
          System.out.println("Hamming : "+myBoard.hamming()); 
              System.out.println(myBoard.toString());
          
    }
    
}