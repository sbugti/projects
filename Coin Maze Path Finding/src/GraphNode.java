/**
 * Constructor class to create GraphNode object to represent nodes within a graph, to store two PIVs: integer name and boolean marked
 * @author Sameer Bugti
 */
public class GraphNode {
	private int name;	//intializing PIVs
	private boolean marked;
	
	/**
	 * Constructor method to initialize GraphNode object and set PIV values, with marked automatically set to false
	 * @param name variable to set PIV to, for use identifying index of node
	 */
	public GraphNode(int name) {
		this.name = name;
		marked = false;
	}
	
	/**
	 * Setter method for marked PIV
	 * @param mark new boolean to set marked PIV to 
	 */
	public void mark(boolean mark) {
		marked = mark;
	}
	
	/**
	 * Getter method for marked PIV
	 * @return marked PIV
	 */
	public boolean isMarked() {
		return marked;
	}
	
	/**
	 * Getter method for marked PIV
	 * @return name PIV
	 */
	int getName() {
		return name;
	}
}
