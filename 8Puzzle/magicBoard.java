import java.util.Iterator;
import java.util.ArrayList;
public class magicBoard {
    public int[][] tiles; private int n;
    private int pos0r,pos0c; private Board twin=null;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    
    public magicBoard(int[][] tiles){
        
        int l=tiles.length;
        
        n=l;
        this.tiles=new int[n][n];
        
        for(int i=0;i<l;i++){
            for(int j=0;j<l;j++){
                this.tiles[i][j]=tiles[i][j];
                if(tiles[i][j]==0){
                    pos0r=i;
                    pos0c=j;
                }                
            }
        }
    }
    
    public int dimension(){return n;}
    
    private void rotaterow(int i,int j){
        
        if(j==0){ //left
            int first=tiles[i][0];
            for(int p=0;p<n-1;p++)
                tiles[i][p]=tiles[i][p+1];
            tiles[i][n-1]=first;
        }
        else{
//right
            int last=tiles[i][n-1];
            for(int p=n-1;p>0;p--)
                tiles[i][p]=tiles[i][p-1];
            tiles[i][0]=last;
            
        }
    }
    
    private void rotatecol(int i,int j){
        if(j==0){ //up
            int first=tiles[j][0];
            for(int p=0;p<n-1;p++)
                tiles[p][i]=tiles[p+1][i];
            tiles[n-1][i]=first;
        }
        else{
//down
            int last=tiles[n-1][i];
            for(int p=n-1;p>0;p--)
                tiles[p][i]=tiles[p-1][i];
            tiles[0][i]=last;
            
        }
    }
    
    // number of tiles out of place
    public int hamming(){
        int c=0; 
        for (int i = 0; i < n; i++) {
            int dr=0,dc=0;
            for (int j = 0; j < n; j++) {
                dr+=tiles[i][j];
                dc+=tiles[j][i];
            }
            if(dr!=15)
                c++;
            if(dc!=15)
                c++;
        }
        return c;
    }
    
    
    // is this board the goal board?
    public boolean isGoal(){
        for (int i = 0; i < n; i++) {
            int dr=0,dc=0;
            for (int j = 0; j < n; j++) {
                dr+=tiles[i][j];
                dc+=tiles[j][i];
                    
                    if(dr>15)
                    return false;
                    if(dc>15)
                    return false;
            }
            if(dr<15)
                return false;
            if(dc<15)
                return false;
        }
        int dg=0,gd=0;
        for (int i = 0; i < n; i++) {
            dg+=tiles[i][i];
            gd+=tiles[2-i][i];
        }
        if(dg>15||dg<15)
            return false;
        if(gd>15||gd<15)
            return false;
        return true;
        
    }
    
    // does this board equal y?
    public boolean equals(Object obj){
        if (obj == this) { return true; }
        
        if (obj == null || obj.getClass() != this.getClass()) 
        { return false; }
        
        magicBoard otherBoard=(magicBoard)obj;
        
        if(otherBoard.dimension()!=this.dimension())
            return false;
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
       
        ArrayList<Board> neighbours=new ArrayList<Board>();
        for(int i=0;i<n;i++){
            rotaterow(i,1);
            neighbours.add(new Board(this.tiles));
            rotaterow(i,0);
            rotaterow(i,0);
            neighbours.add(new Board(this.tiles));
            rotaterow(i,1);
            rotatecol(i,1);
            neighbours.add(new Board(this.tiles));
            rotatecol(i,0);
            rotatecol(i,0);
            neighbours.add(new Board(this.tiles));
            rotatecol(i,1);
        }
    
    return neighbours;
}

    private boolean swap(int i,int j,int u,int v){
        try{
            int temp=this.tiles[i][j];
            this.tiles[i][j]=this.tiles[u][v];
            this.tiles[u][v]=temp;
            return true;
        }
        catch(ArrayIndexOutOfBoundsException e){
            
            return false;
        }
    }


// a board that is obtained by exchanging any pair of tiles
public Board twin(){
    if(this.twin!=null)
        return this.twin;
    int i=Math.random(n);
    int j=Math.random(n);
    int u=Math.random(n);
    int v=Math.random(n);
    while((i==pos0r&&j==pos0c)||(u==pos0r&&v==pos0c)||(i==u&&j==v)){
        
        i=Math.random(n);
        j=Math.random(n);
        u=Math.random(n);
        v=Math.random(n);
    }
    swap(i,j,u,v);
    this.twin=new Board(this.tiles);
    swap(i,j,u,v);
    return this.twin;
    
}

// unit testing (not graded)
public static void main(String[] args){
    
    //int[][] tiles={{1,0,3},{4,2,5},{7,8,6}};
    int[][] tilesb={{1,0,3},{4,2,5},{7,6,8}};
    int[][] tiles={{2,0},{3,1}};
    Board myBoard=new Board(tiles);
    Board otherBoard=new Board(tilesb);
    System.out.println(myBoard.toString());   
    for(Board b:myBoard.neighbors())
        System.out.println(b.toString()); 
    
    System.out.println(myBoard.twin().toString()); 
    System.out.println(myBoard.equals(otherBoard));
    System.out.println("Is Goal? : "+myBoard.isGoal());
    System.out.println("Manhattan : "+myBoard.manhattan());  
    System.out.println("Hamming : "+myBoard.hamming()); 
    System.out.println(myBoard.toString());
    
}

}