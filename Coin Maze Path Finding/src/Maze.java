import java.io.*;
import java.util.*;

/**
 * Constructor class to create Maze object, implemented with graph representation, to be pathfound, contains 6 PIVs
 * @author Sameer Bugti
 */
public class Maze {
	private Graph graph; //initializing PIVs
	private int width;
	private int length;
	private int coins;
	private GraphNode startNode;
	private GraphNode exitNode;
	
	/**
	 * Constructor method for a Maze object to read file input to set private instance variables and base graph off of.
	 * @param inputFile name of input file to be read
	 * @throws MazeException if file cannot be read, or if format of the maze input is wrong
	 */
	public Maze(String inputFile) throws MazeException {
		char[][] characters = new char[0][0];	//creates 2d char array to store each char in the maze
		boolean startFound = false;	//intializing neccessary variables
		boolean exitFound = false;
	
		BufferedReader reader;	//reader variable to read each line of input file
		String line = "";
		int linecount = 0;	//linecount to keep track of which line has been read
		
		//try reading file name, throws error if cannot be read properly
		try {	
			reader = new BufferedReader(new FileReader(inputFile));
		} catch(Exception e) {
			throw new MazeException("File not found");
		}
		
		//tries reading first 4 lines of input
		try {
			reader.readLine();
			width = Integer.parseInt(reader.readLine().trim());	//width PIV defined by second line, width of nodes only
			length = Integer.parseInt(reader.readLine().trim());	//length PIV defined by third line, length of nodes only
			coins = Integer.parseInt(reader.readLine().trim());	//coins PIV defined by fourth line
		} catch (IOException e1) {	//throws error if cannot be read properly
			e1.printStackTrace();
		}
		
		int totalWidth = width + width - 1;	//finds totalWidth, representing both nodes and edges within the maze in each row, for use within traversing 2d array
		int totalLength = length + length - 1;	//finds totalLength, representing both nodes and edges within the maze in each column
		
		characters = new char[totalLength][totalWidth]; //defines size of 2d array
		while(linecount < totalLength) {	//traverses every line after first 4 lines, representing each row within the maze
			try {
				line = reader.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			char curr;	//temp char var to check each char within the line read
			for(int i = 0; i < totalWidth; i++) {
				curr = line.charAt(i);
				characters[linecount][i] = curr;	//stores each character within the 2d array
			}
			linecount++;
		}
		try {	//closes reader to prevent memory leak
			reader.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
		//graph representation needs size of nodes, not edges, so use basic width and length as defined by input
		graph = new Graph(width*length);
		
		for(int i = 0; i < characters.length; i++) {	//loops through the 2d array to check every possible character in the maze
			for(int j = 0; j < characters[i].length; j++) {
				if(characters[i][j] == 's') {	//if found starting node...
					if(!startFound) {
						try {
							startNode = graph.getNode(i/2*width + j/2);	//set the PIV to it (2d indexes mathematical relation to 1d array of nodes is (i/2*width + j/2)) 
						} catch (GraphException e) {
							e.printStackTrace();
						}
						startFound = true;	//and set boolean PIV to true so it cannot redefine itself
					} else {	//throws exception if found second starting node, as that is not within the rules of a maze
						throw new MazeException("Second start found");
					}
					
				} else if(characters[i][j] == 'x') {	//if found exit node...
					if(!exitFound) {
						try {
							exitNode = graph.getNode(i/2*width + j/2);	//set the PIV to it
						} catch (GraphException e) {
							e.printStackTrace();
							}
						exitFound = true;	//and set boolean PIV to true so it cannot redefine itself
					} else {
						throw new MazeException("Second exit found");	//throws exception if found second exit node, as that is not within the rules of a maze
					}
		
				} else if (characters[i][j] == 'o' || characters[i][j] == 'w'){ //if found wall edge...
					continue;	//do nothing
				} else if(characters[i][j] == 'c') {	//if found corridor edge..
					if(j % 2 == 0) {	//if the column its stored in is even, it must be a vertically connecting edge
						try {
							graph.insertEdge(graph.getNode(i/2*width + j/2), graph.getNode(i/2*width + j/2 + width), 0, "corridor");	//connects currNode to the node below it
						} catch (GraphException e) {
							e.printStackTrace();
						}
					} else {	//if the column its stored in is odd, it must be a horizontally connecting edge
						try {
							graph.insertEdge(graph.getNode(i/2*width + j/2), graph.getNode(i/2*width + j/2 + 1), 0, "corridor");	//connects currNode to the node directly to its right
						} catch (GraphException e) {
							e.printStackTrace();
						}
					}
				} else if(Integer.parseInt(String.valueOf(characters[i][j])) != Double.NaN) {	//if you can find a number representation of the current character...
					int coinage = Integer.parseInt(String.valueOf(characters[i][j]));	//door is found, so set coinage (type) of edge to it
							
					if(j % 2 == 0) {	//if the column its stored in is even, it must be a vertically connecting edge
						try {
							graph.insertEdge(graph.getNode(i/2*width + j/2), graph.getNode(i/2*width + j/2 + width), coinage, "door");	//connects currNode to the node below it
						} catch (GraphException e) {
							e.printStackTrace();
						}
					} else {	//if the column its stored in is odd, it must be a horizontally connecting edge
						try {
							graph.insertEdge(graph.getNode(i/2*width + j/2), graph.getNode(i/2*width + j/2 + 1), coinage, "door");	//connects currNode to the node directly to its right
						} catch (GraphException e) {
							e.printStackTrace();
						}
					}
				} else {	//if not one of the previous options, the character is not correct so MazeException must be thrown
					throw new MazeException("Maze letter not found");
				}
			}
		}
	}
	
	/**
	 * Getter method for graph PIV
	 * @return graph PIV
	 * @throws MazeException if graph has not been initialized properly
	 */
	public Graph getGraph() throws MazeException {
		if(graph == null) throw new MazeException("Graph is null");
		return graph;
	}
	
	/**
	 * solve method to pathfind from start to exit of graph
	 * @return iterator containing the nodes you must travel in order to find the exit, returns null if solution cannot be found
	 */
	@SuppressWarnings("rawtypes")
	public Iterator solve() {
		ArrayList<GraphNode> path = new ArrayList<>();	//temp variable to store the nodes
		
		try {
			solveHelper(startNode, path);	//call recursive helper function to edit the path list
		} catch (GraphException e) {
			e.printStackTrace();
		}
		
		if(!path.isEmpty()) return path.iterator();	//if path has been found, return it
		else return null;	//if not, return null
	}
	
	/**
	 * recursive private helper function to traverse nodes of the graph and add and remove them from the path list
	 * @param currNode node currently being checked
	 * @param path list of each node traversed in current path algorithm
	 * @throws GraphException if incidentEdges is not used correctly
	 */
	@SuppressWarnings("unchecked")
	private void solveHelper(GraphNode currNode, ArrayList<GraphNode> path) throws GraphException {
		int initialCoins = coins;	//contains current coins before traversing through a door
		currNode.mark(true);	//marks the node as traversed
		path.add(currNode);	//adds traversed node to the path
		
		if(currNode == exitNode) {	//if the exit has been found, return, as path contains correct path
			return;
		} else {	//recursive case where you have not found the exit
			Iterator<GraphEdge> connections = graph.incidentEdges(currNode);	//grabs every edge connected to the current node
			
			while(connections.hasNext()) {	//loops through each edge in the iterator
				GraphEdge currEdge = connections.next();
				GraphNode nextNode = currEdge.secondEndpoint();	//grabs the node the current node is connected to 
				
				if(!nextNode.isMarked()) {	//checks if next node has been traveled to before
					if(currEdge.getLabel().equals("corridor")) {	//corridor is prioritized in travel to use as little coins as possible
						nextNode.mark(true);	//marks the next node for traversal
						solveHelper(nextNode, path);	//and traverses down the next node, keeping the same path so far
					} else if(currEdge.getLabel().equals("door")) {
						if(currEdge.getType() <= coins) {	//if you have enough coins to go through an available door...
							nextNode.mark(true);	//marks the next node for traversal
							coins -= currEdge.getType();	//and removes the amount of coins the door requires from PIV
							solveHelper(nextNode, path);	//and traverses down the next node, keeping the same path so far
							
							//if the solveHelper does not edit the path, reset coins as if you had not gone through the door
							if(path.get(path.size() - 1) != nextNode && path.get(path.size() - 1) != exitNode) {
								coins = initialCoins;
							}
						}
					}
				}
			}
			
			//if traversal of the current node does not result in being able to go to the exit, a corridor, or a door...
			if(path.get(path.size() - 1) != exitNode) {
				currNode.mark(false);	//unmark the currentNode
				path.remove(path.size() - 1);	//remove the node from the correct path list
			}
			
		}
	}
}
