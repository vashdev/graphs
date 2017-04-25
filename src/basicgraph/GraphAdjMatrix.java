package basicgraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** A class that implements a directed graph. 
 * The graph may have self-loops, parallel edges. 
 * Vertices are labeled by integers 0 .. n-1
 * and may also have String labels.
 * The edges of the graph are not labeled.
 * Representation of edges via an adjacency matrix.
 * 
 * @author UCSD MOOC development team and YOU
 *
 */
public class GraphAdjMatrix extends Graph {

	private final int defaultNumVertices = 5;
	private int[][] adjMatrix;
	
	/** Create a new empty Graph */
	public GraphAdjMatrix () {
		adjMatrix = new int[defaultNumVertices][defaultNumVertices];
	}
	public GraphAdjMatrix (int[][] m) {
		adjMatrix = m;
	}
	/** 
	 * Implement the abstract method for adding a vertex.
	 * If need to increase dimensions of matrix, double them
	 * to amortize cost. 
	 */
	public void implementAddVertex() {
		int v = getNumVertices();
		if (v >= adjMatrix.length) {
			int[][] newAdjMatrix = new int[v*2][v*2];
			for (int i = 0; i < adjMatrix.length; i ++) {
				for (int j = 0; j < adjMatrix.length; j ++) {
					newAdjMatrix[i][j] = adjMatrix[i][j];
				}
			}
			adjMatrix = newAdjMatrix;
		}
	}
	
	/** 
	 * Implement the abstract method for adding an edge.
	 * Allows for multiple edges between two points:
	 * the entry at row v, column w stores the number of such edges.
	 * @param v the index of the start point for the edge.
	 * @param w the index of the end point for the edge.  
	 */	
	public void implementAddEdge(int v, int w) {
		adjMatrix[v][w] += 1;
	}
	
	/** 
	 * Implement the abstract method for finding all 
	 * out-neighbors of a vertex.
	 * If there are multiple edges between the vertex
	 * and one of its out-neighbors, this neighbor
	 * appears once in the list for each of these edges.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */	
	public List<Integer> getNeighbors(int v) {
		List<Integer> neighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[v][i]; j ++) {
				neighbors.add(i);
			}
		}
		return neighbors;
	}
	
	/** 
	 * Implement the abstract method for finding all 
	 * in-neighbors of a vertex.
	 * If there are multiple edges from another vertex
	 * to this one, the neighbor
	 * appears once in the list for each of these edges.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */
	public List<Integer> getInNeighbors(int v) {
		List<Integer> inNeighbors = new ArrayList<Integer>();
		for (int i = 0; i < getNumVertices(); i ++) {
			for (int j=0; j< adjMatrix[i][v]; j++) {
				inNeighbors.add(i);
			}
		}
		return inNeighbors;
	}
	
	/** 
	 * Implement the abstract method for finding all 
	 * vertices reachable by two hops from v.
	 * Use matrix multiplication to record length 2 paths.
	 * 
	 * @param v the index of vertex.
	 * @return List<Integer> a list of indices of vertices.  
	 */	
	public List<Integer> getDistance2(int v) {
		// XXX Implement this method in week 1
		
		 // Get neighbours of vertex 
		List<Integer> adj2list = new ArrayList<Integer>();
		
			//Square adjacent matrix for 2 hop results 
		int dim = adjMatrix.length;
		 int[][] adjMatrix2=adjMatrix;	 
		 int[][] matrix2hop=new int[dim][dim];
		 int aRows = adjMatrix.length;
	        int aColumns = adjMatrix[0].length;
	        int bRows = adjMatrix2.length;
	        int bColumns = adjMatrix2[0].length;
		 
	   	 //ref: stackoverflow http://stackoverflow.com/questions/17623876/matrix-multiplication-using-arrays
	 //       System.out.println("*********************************************************");
	 //       System.out.println("Original Matrix ...;");
	     //   System.out.println(this.adjacencyString());
	        		
	        for (int i = 0; i < aRows; i++) { // aRow
	            for (int j = 0; j < bColumns; j++) { // bColumn
	                for (int k = 0; k < aColumns; k++) { // aColumn
	                	matrix2hop[i][j] += adjMatrix[i][k] * adjMatrix2[k][j];
	                }
	            }
	        }
	//        System.out.println(" Hop2 Matrix ....");
	 //     System.out.println(this.adjacencyString2( matrix2hop));
	//      System.out.println("*********************************************************");
// verify 2 hop
	//      System.out.println(adjacencyString2(matrix2hop));
	        // convert matrix Row v to list to get its neighbours  
	    String s = new String();    
	        for (int i = 0; i < matrix2hop[v].length; i++) {
	       
	        	if(matrix2hop[v][i] >0) {
	        	//	 s=s+"Value:"+ matrix2hop[v][i] +" Column "+ i; 
	        	//	 System.out.println(s);
	        	// Chekc value of matrix in the cell if its >1 then add it to path multiple times
	        		
	        			 for(int k=1;k<=matrix2hop[v][i];k++)
	        				 adj2list.add( i);    // v can reach i        	 
	        	
	        }
	        }
	        
		return adj2list;
	}
	
	/**
	 * Generate string representation of adjacency matrix
	 * @return the String
	 */
	public String adjacencyString() {
		int dim = adjMatrix.length;
		String s = "Adjacency matrix";
		s += " (size " + dim + "x" + dim + " = " + dim* dim + " integers):";
		for (int i = 0; i < dim; i ++) {
			s += "\n\t"+i+": ";
			for (int j = 0; j < adjMatrix[i].length; j++) {
			s += adjMatrix[i][j] + ", ";
			}
		}
		return s;
	}
	public String adjacencyString2( int[][] m) {
		int dim = m.length;
		String s = "New matrix";
		s += " (size " + dim + "x" + dim + " = " + dim* dim + " integers):";
		for (int i = 0; i < dim; i ++) {
			s += "\n\t"+i+": ";
			for (int j = 0; j < m[i].length; j++) {
			s += m[i][j] + ", ";
			}
		}
		return s;
	};
	public static void main(String[] args) {
		int[][] multiplicand = new int[][] {
            {0, 1, 1,0},
            {0,0,0,1},
            {0,1,0,1},
            {0,0,0,0},
    };
    
    GraphAdjMatrix g = new GraphAdjMatrix(multiplicand);
    System.out.println(g.adjacencyString());
   
	List<Integer> hop2test= g.getDistance2(0);
	hop2test.iterator();
	hop2test.stream().forEach(System.out::println);
    
    }
	
}
