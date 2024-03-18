/**
 * Constructor class to create GraphEdge object to represent an edge between nodes on a graph, storing 4 PIVs
 * @author Sameer Bugti
 */
public class GraphEdge {
	private GraphNode u;	//initializing PIVs
	private GraphNode v;
	private int type;
	private String label;
	
	/**
	 * Constructor method to create GraphEdge object and set PIVs to specified parameters
	 * @param u first node connected to the edge
	 * @param v second node connected to the edge
	 * @param type describes amount of coins needed to go through a door type edge, 0 if corridor type
	 * @param label describes the type of edge through a string variable
	 */
	public GraphEdge(GraphNode u, GraphNode v, int type, String label) {
		this.u = u;
		this.v = v;
		this.type = type;
		this.label = label;
	}
	
	/**
	 * Getter method for first node connected to edge object
	 * @return u PIV
	 */
	public GraphNode firstEndpoint() {
		return u;
	}
	
	/**
	 * Getter method for second node connected to edge object
	 * @return v PIV
	 */
	public GraphNode secondEndpoint() {
		return v;
	}
	
	/**
	 * Getter method for int representation of coin cost of edge
	 * @return type PIV
	 */
	public int getType() {
		return type;
	}
	
	/**
	 * Setter method for int representation of coin cost of edge
	 * @param newType new value to replace type PIV
	 */
	public void setType(int newType) {
		type = newType;
	}
	
	/**
	 * Getter method for string representation of type of edge
	 * @return label PIV
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Setter method for string representation of type of edge
	 * @param newLabel new value to replace label PIV
	 */
	public void setLabel(String newLabel) {
		label = newLabel;
	}
}
