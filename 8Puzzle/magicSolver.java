import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
class SearchNode{
    Board board; SearchNode previous; int moves,priority; 
    public SearchNode(SearchNode previous,Board board){
        this.previous=previous;
        this.board=board;
        this.moves=previous==null?0:previous.moves+1;
        this.priority=this.moves+this.board.manhattan(); 
        
    }
    
     static Comparator<SearchNode> getComparator(){
        return new NodeComparator();
    }
    static class NodeComparator implements Comparator<SearchNode>{
        public int compare(SearchNode a,SearchNode b){
            int pa=a.priority;
            int pb=b.priority;
            if(pa>pb)
                return 1;
            else if(pa<pb)
                return -1;
            else
                return 0;
        }
    }
}

public class Solver {
    private SearchNode currentNode,twinNode; private boolean isSolvable;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        if(initial==null)
            throw new IllegalArgumentException();
        MinPQ<SearchNode> pq=new MinPQ<SearchNode>(SearchNode.getComparator());
        MinPQ<SearchNode> twinpq=new MinPQ<SearchNode>(SearchNode.getComparator());
        pq.insert(new SearchNode(null,initial));
        twinpq.insert(new SearchNode(null,initial.twin()));
        currentNode=pq.delMin();
        twinNode=twinpq.delMin();
        while(!currentNode.board.isGoal()&&!twinNode.board.isGoal()){
            // System.out.println(pq.size());
            for(Board b: currentNode.board.neighbors()){
                if(currentNode.previous==null||!currentNode.previous.board.equals(b))
                    pq.insert(new SearchNode(currentNode,b));
            }
            
            for(Board b: twinNode.board.neighbors()){
                if(twinNode.previous==null||!twinNode.previous.board.equals(b))
                    twinpq.insert(new SearchNode(twinNode,b));
            }
            currentNode=pq.delMin();
            twinNode=twinpq.delMin();
        }
        if(currentNode.board.isGoal())
            this.isSolvable=true;
        else
            this.isSolvable=false;
    }
    
    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return this.isSolvable;
    }
    // min number of moves to solve initial board
    public int moves(){
        return isSolvable?currentNode.moves:-1;
    }
    
    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
        if(!isSolvable)
            return null;
        ArrayList<Board> list=new ArrayList<Board>();
        SearchNode node=this.currentNode;
        while(node!=null){
            list.add(node.board);
            node=node.previous;
        }
        Collections.reverse(list);
        return list;
    }
    
    // test client (see below) 
    public static void main(String[] args){
        
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readByte();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
    
}