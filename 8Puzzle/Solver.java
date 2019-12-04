import java.util.Comparator;
import java.util.ArrayList;
import java.util.Collections;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
class SearchNode{
    Board board; SearchNode previous; int moves,manhattan;
    public SearchNode(SearchNode previous,Board board){
        this.previous=previous;
        this.board=board;
        this.moves=previous==null?0:previous.moves+1;;
        this.manhattan=this.board.manhattan(); 
        
    }
    
    static Comparator<SearchNode> getComparator(){
        return new NodeComparator();
    }
    static class NodeComparator implements Comparator<SearchNode>{
        public int compare(SearchNode a,SearchNode b){
            int pa=a.moves+a.manhattan;
            int pb=b.moves+b.manhattan;
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
    SearchNode currentNode;
    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial){
        MinPQ<SearchNode> pq=new MinPQ<SearchNode>(SearchNode.getComparator());
        pq.insert(new SearchNode(null,initial));
        currentNode=pq.delMin();
        while(!currentNode.board.isGoal()){
           // System.out.println(pq.size());
            for(Board b: currentNode.board.neighbors()){
                if(currentNode.previous==null||!currentNode.previous.board.equals(b))
                    pq.insert(new SearchNode(currentNode,b));
            }
            currentNode=pq.delMin();
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable(){
        return true;
    }
    // min number of moves to solve initial board
    public int moves(){
        return currentNode.moves;
    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution(){
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
    byte[][] tiles = new byte[n][n];
    for (int i = 0; i < n; i++)
        for (int j = 0; j < n; j++)
            tiles[i][j] = in.readByte();
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