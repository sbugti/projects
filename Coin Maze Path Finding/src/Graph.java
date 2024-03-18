import java.util.*;

/**
 * Constructor class to create Graph object, implements as an abstract data type, to represent a set of nodes and edges connecting those nodes. 3 PIVs
 * @author Sameer Bugti
 */
public class Graph implements GraphADT {
	private int size;	//initializing PIVs
	private GraphNode[] nodes;
	private GraphEdge[][] adjacentMatrix;
	
	/**
	 * Constructor method to create Graph object with size n, a set of nodes with size n, and an adjacent matrix implementation of size n by n
	 * @param n size specification of graph
	 */
	public Graph(int n) {
		size = n;
		nodes = new GraphNode[n];
		adjacentMatrix = new GraphEdge[n][n];
		
		//creating every node within the nodes PIV
		for(int i = 0; i < n; i++) {
			nodes[i] = new GraphNode(i);
		}
		
		//setting every edge to null within the adjaceny matrix to show its empty
		for(int i = 0; i < n; i++) {
			for(int j = 0; j < n; j++) {
				adjacentMatrix[i][j] = null;
			}
		}
	}
	
	/**
	 * Setter method for individual edges within the adjaceny matrix PIV
	 * @param nodeu first node connected to edge to be inserted
	 * @param nodev second node connected to edge to be inserted
	 * @param type type of edge to be inserted
	 * @param label label of edge to be inserted
	 * @throws GraphException if out of bounds or if spot is taken within the matrix
	 */
	@Override
	public void insertEdge(GraphNode nodeu, GraphNode nodev, int type, String label) throws GraphException {
		int uName = nodeu.getName();	//grabs indexes for each node
		int vName = nodev.getName();
		
		if(uName < 0 || vName < 0 || uName >= size || vName >= size) {	//checks if nodes are out of bounds of node list PIV
			throw new GraphException("Given nodes do not exist");
		} else if(adjacentMatrix[uName][vName] != null) {	//checks if spot is taken
			throw new GraphException("Given nodes already have an edge");
		} else {
			adjacentMatrix[uName][vName] = new GraphEdge(nodeu, nodev, type, label);	//inserts edge in both directions
			adjacentMatrix[vName][uName] = new GraphEdge(nodev, nodeu, type, label);
		}
	}

	/**
	 * Getter method for nodes within the node list PIV
	 * @param u index to grab node from within the list
	 * @throws GraphException if u is out of bounds from the list PIV
	 * @return node at index u within list PIV
	 */
	@Override
	public GraphNode getNode(int u) throws GraphException {
		if(u < 0 || u >= size) {
			throw new GraphException("Node does not exist");
		}
		return nodes[u];
	}

	/**
	 * Method to grab all edges connected to specified node
	 * @param u node to find edges of
	 * @throws GraphException if u is out of bounds from the list PIV
	 * @return iterator variable containing each edge connected to the node, null if no edges are connected to node u
	 */
	@Override
	public Iterator incidentEdges(GraphNode u) throws GraphException {
		int uName = u.getName();	//grabs index of given node
		ArrayList<GraphEdge> result = new ArrayList<>();	//temp variable to store each edge
		
		if(uName < 0 || uName >= size) {	//checks if node is out of bounds
			throw new GraphException("Node does not exist");
		} 
		
		for(int i = 0; i < size; i++) {		//checks every node in relation to given node, and sees if its connected through the adjacency matrix
			if(adjacentMatrix[uName][i] != null) {
				result.add(adjacentMatrix[uName][i]);	//if so, add to the iterator
			}
		}
			
		if(result.isEmpty()) {	//if no nodes connect, return null
			return null;
		} else {
			return result.iterator();	//else, return iterator containing every connecting edge
		}
	}

	/**
	 * Getter method to find specified edge within adjacency matrix PIV
	 * @param u	first node connected to edge
	 * @param v second node connected to edge
	 * @throws GraphException if nodes specified are out of bounds, or if no edge exists between given nodes
	 * @return edge connecting two specified nodes
	 */
	@Override
	public GraphEdge getEdge(GraphNode u, GraphNode v) throws GraphException {
		int uName = u.getName();	//grabs indexes of given nodes
		int vName = v.getName();
		
		if(uName < 0 || uName >= size || vName < 0 || vName >= size) {	//checks if they are in bounds of list PIV
			throw new GraphException("Given nodes do not exist");
		} else if (adjacentMatrix[uName][vName] == null) {	//checks if adjacency matrix contains an edge between the nodes
			throw new GraphException("No edge between given nodes");
		}
		return adjacentMatrix[uName][vName];	//returns edge if no exceptions
	}

	/**
	 * method to check if edge exists between two nodes
	 * @param u first node connected to edge
	 * @param v second node connected to edge
	 * @throws GraphException if nodes specified are out of bounds
	 * @return true if adjacency matrix contains edge between these two nodes, false if edge is null
	 */
	@Override
	public boolean areAdjacent(GraphNode u, GraphNode v) throws GraphException {
		int uName = u.getName();	//grabs indexes of given nodes
		int vName = v.getName();
		
		if(uName < 0 || uName >= size || vName < 0 || vName >= size) {	//checks if they are in bounds of list PIV
			throw new GraphException("Given nodes do not exist");
		} else if (adjacentMatrix[uName][vName] == null) {	//checks if adjacency matrix does not contain edge between nodes
			return false; 	//returns false
		}
		return true;	//else, returns true
	}
	
}
