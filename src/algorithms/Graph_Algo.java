package algorithms;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.*;

import dataStructure.*;
/**
 * This class represents the "regular" Graph Theory algorithms including:
 * 0. clone();
 * 1. init(String file_name);
 * 2. save(String file_name);
 * 3. isConnected();
 * 5. double shortestPathDist(int src, int dest);
 * 6. List<Node> shortestPath(int src, int dest);
 *
 */
public class Graph_Algo implements graph_algorithms, Serializable {
	private DGraph gr = new DGraph();

	public Graph_Algo(graph graph) {
		if (graph == null) {
			gr = new DGraph();
		} else {
			gr = new DGraph((DGraph) graph);
		}
	}
	public Graph_Algo() {
		gr=new DGraph();
	}

	/**
	 * Init this set of algorithms on the parameter - graph.
	 * @param g the new graph of this object.
	 */
	@Override
	public void init(graph g) {
		gr = new DGraph((DGraph) g);
	}
	/**
	 * Init a graph from serializable file
	 * @param file_name
	 */
	@Override
	public void init(String file_name) {
		deserialize(file_name);
	}
	/**
	 * saves a graph to serializable file
	 * @param file_name
	 */
	@Override
	public void save(String file_name) {
		serialize(file_name);
	}
	/**
	 * Returns true if and only if (iff) there is a valid path from EVREY node to each
	 * other node.
	 * implementation:
	 * 1. take any node.
	 * 2. check if from this node i can get to all other nodes.
	 * 3. transpose the graph (remove all edges and connect it opposite.
	 * 4. take the same node again.
	 * 5. check number 2 again.
	 * 6. if still true, graph is connected.
	 * @return true\false if graph is connected.
	 */
	@Override
	public boolean isConnected() {
		if(gr==null||gr.nodeSize()==0){
			return true;
		}
		DGraph copy= (DGraph) gr.copy();
		Object[] arr = copy.getVertices().keySet().toArray();
		DFS(((node) copy.getVertices().get(arr[0])),copy);
		if (CheckForFlag(copy) == false) {
			return false;

		}
		setALLzero(copy);
		DGraph g1 = Transpose(copy);
		DFS(((node) g1.getVertices().get(arr[0])),copy);
		if (CheckForFlag(g1) == false) {
			return false;
		}
		return true;
	}

	/**
	 * searching in DFS method. took the idea from here https://www.geeksforgeeks.org/depth-first-search-or-dfs-for-a-graph/
	 * @param n the node to start to search with.
	 */
	private void DFS(node n, DGraph copy) {    //change to iterative
		setALLzero(copy);
		DFS2(n,copy);

	}

	/**
	 * recursive function to implement the dfs search.
	 * @param n the node to start to search with.
	 */
	private void DFS2(node n,DGraph copy) {
		if (n.getTag() != 1) {
			n.setTag(1);
			if (copy.getEdges().get(n.getKey()).size() > 0) {
				Object[] arr = copy.getEdges().get(n.getKey()).keySet().toArray();
				for (int i = 0; i < arr.length; i++) {
					DFS2((node) copy.getVertices().get(arr[i]),copy);
				}
			}
			return;
		}


	}

	/**
	 * simple function to check all tags for the graph.
	 * @param g the graph to check
	 * @return true if all tags marked as visited (1), false if there is at least one node with tag=0.
	 */
	private boolean CheckForFlag(DGraph g) {
		Object[] arr = g.getVertices().keySet().toArray();
		for (int i = 0; i < arr.length; i++) {
			if (g.getVertices().get(arr[i]).getTag() == 0) {
				return false;
			}
		}
		return true;
	}

	/**
	 * simple function to go over all the nodes of the graph and set tag to 0.
	 * @param g the graph to set tags zero.
	 */
	private void setALLzero(DGraph g) {
		Object[] arr = g.getVertices().keySet().toArray();
		for (int i = 0; i < arr.length; i++) {
			g.getVertices().get(arr[i]).setTag(0);
		}
	}

	/**
	 * function to remove all edges and connect it back opposite.
	 * @param g graph to change
	 * @return same graph, opposite direction for the edges.
	 */
	private DGraph Transpose(DGraph g) {
		DGraph graph = new DGraph(g);
		Object[] arr = graph.getVertices().keySet().toArray();
		for (int i = 0; i < arr.length; i++) {
			if (graph.getEdges().get(arr[i]).size() > 0) {
				Object[] a = graph.getEdges().get(arr[i]).keySet().toArray();
				for (int j = 0; j < a.length; j++) {
					if (graph.getEdges().get(arr[i]).get(a[j]).getTag() != 1) {
						graph.removeEdge((int) arr[i], (int) a[j]);
						if (graph.getEdges().get(a[j]).get(arr[i]) != null) {
							graph.getEdges().get(a[j]).get(arr[i]).setTag(1);
							graph.connect((int) arr[i], (int) a[j], 0);
							graph.getEdges().get(arr[i]).get(a[j]).setTag(1);
						} else {
							graph.connect((int) a[j], (int) arr[i], 0);
							graph.getEdges().get(a[j]).get(arr[i]).setTag(1);
						}
					}
				}
			}
		}
		return graph;
	} // function to remove all edges and connect it back opposite.
	/**
	 * returns the length of the shortest path between src to dest
	 * @param src - start node
	 * @param dest - end (target) node
	 *  if src\dest are wrong, throws exception.
	 * @return the distance between src node to dest node.
	 */
	@Override
	public double shortestPathDist(int src, int dest) {
		try {
			setALLzero(gr);
			setALLInfinity(gr);
			PriorityQueue<node> pq = new PriorityQueue<node>(gr.getVertices().size(), new nodeComperator());
			node s = (node) gr.getVertices().get(src);
			s.setTag(src);    //set tag of source as it's own id
			s.setWeight(0);
			Object[] arr = gr.getEdges().get(s.getKey()).keySet().toArray();
			addPq(pq, arr, s);
			boolean visited=true;
			while (!pq.isEmpty()&&visited) {
				node x = pq.poll();
				if(x.getInfo().equals("visited")){
					break;
				}
				x.setInfo("visited");
				Object[] ar = gr.getEdges().get(x.getKey()).keySet().toArray();
				addPq(pq, ar, x);
			}
			return gr.getVertices().get(dest).getWeight();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error: Invalid src/dest or there is no path between src and dest");
			return 0;
		}
	}

	/**
	 * This function implements the Dkjistra algorithm as it is.
	 * @param pq the priority queue to add all the nodes to.
	 * @param arr array of values to to insert to the queue.
	 * @param n
	 * @return the priority queue.
	 */
	private PriorityQueue<node> addPq(PriorityQueue<node> pq, Object[] arr, node n) {
		for (int i = 0; i < arr.length; i++) {
			double w = gr.getEdges().get(n.getKey()).get(arr[i]).getWeight();
			edge e = (edge) gr.getEdges().get(n.getKey()).get(arr[i]);
			if ((w + n.getWeight()) < e.getDestination().getWeight()) {
				e.getDestination().setWeight(w + n.getWeight());
				e.getDestination().setTag(n.getKey());        //set the tag as the previous node to save the path
				//e.getDestination().setWeight(Math.min(w + n.getWeight(), e.getDestination().getWeight()));
			}
			pq.add(e.getDestination());
		}
		return pq;
	}
	/**
 	* returns the the shortest path between src to dest - as an ordered List of nodes:
 	* src--> n1-->n2-->...dest
 	* * @param src - start node
 	* @param dest - end (target) node
 	* @return List of the path,empty if no path.
			 */
	@Override
	public List<node_data> shortestPath(int src, int dest) {
		List<node_data> ans = new ArrayList<node_data>();
		Stack<node_data> stk = new Stack<>();                //stack to reverse list elements
		//if (!isConnected()) return ans;					//try-catch?
		if (src == dest) {                            //path of node to itself returns a single node in list
			ans.add(gr.getNode(src));
			return ans;
		}
		if (shortestPathDist(src, dest) == Double.MAX_VALUE) return ans;        //no path between nodes

		stk.push(gr.getNode(dest));        //put destination in stack, it will be the last node in list
		dest = gr.getNode(dest).getTag();    //set next node as previous node (tag from shortest path)
		while (gr.getNode(dest).getKey() != src) {
			stk.push(gr.getNode(dest));
			dest = gr.getNode(dest).getTag();
		}
		stk.push(gr.getNode(src));
		while (!stk.isEmpty())
			ans.add(stk.pop());
		return ans;
	}
	/**
	 * computes a relatively short path which visit each node in the targets List.
	 * if there is full connectivity between the targets, computes a path of the targets
	 * @param targets - list of the nodes for the path.
	 * @return the nodes ordered by the path between them, null if none or there isnt full connectivity.
	 */
	@Override
	public List<node_data> TSP(List<Integer> targets) {
		ArrayList<node_data> temp=new ArrayList<>();
		ArrayList<node_data> ans= new ArrayList<>();
		Iterator<Integer> iter=targets.iterator();
		while(iter.hasNext()){
			int key=iter.next();
			if(gr.getNode(key)==null) return null;
			temp.add(gr.getNode(key));
		}
		for(int i=0;i<temp.size()-1;i++){
			ArrayList<node_data> temp2= (ArrayList<node_data>) shortestPath(temp.get(i).getKey(),temp.get(i+1).getKey());
			if(temp2.isEmpty())return null;
			for(int j=0;j<temp2.size();j++){
				if(!ans.contains(temp2.get(j))){
					ans.add(temp2.get(j));
				}
			}
		}
		ArrayList<node_data> temp2= (ArrayList<node_data>) shortestPath(temp.get(temp.size()-1).getKey(),temp.get(0).getKey());
		if(temp2.isEmpty())return null;
		for(int j=0;j<temp2.size();j++){
			if(!ans.contains(temp2.get(j))){
				ans.add(temp2.get(j));
			}
		}
		return ans;
	}

	/**
	 * this funtion makes a deep copy to a graph by using the copy constructor of DGraph.
	 * @return new copied graph g.
	 */
	@Override
	public graph copy() {
		DGraph g= (DGraph) gr.copy();
		return g;


	}

	/**
	 * this function saves a graph in a file, using serializable, writing it as an object.
	 * @param file_name to name of the file of the saved graph.
	 */
	private void serialize(String file_name){
		try {
			FileOutputStream file = new FileOutputStream(file_name);
			ObjectOutputStream out = new ObjectOutputStream(file);
			out.writeObject(getGr());
			out.close();
			file.close();
		} catch(IOException ex) {
			System.out.println("Error: can't save this graph to file, check again.");
		}
	}
	/**
	 * this function inits a graph from a file, using serializable, init it from an object.
	 * @param file_name to name of the file of the saved graph.
	 */
	private  void deserialize(String file_name) {
		gr=new DGraph();
		try {
			FileInputStream file = new FileInputStream(file_name);
			ObjectInputStream in = new ObjectInputStream(file);
			gr = (DGraph) in.readObject();
			in.close();
			file.close();
		}
		catch(IOException ex) {
			System.out.println("Error: can't init this graph from file, check again.");
		}
		catch(ClassNotFoundException ex) {
		}
	}
	public DGraph getGr(){
	    return gr;
    }

	/**
	 * this function sets weight of all the nodes to infinty.
	 * the use of this function is for Dkjistra algorithm.
	 * @param g
	 */
	private void setALLInfinity(DGraph g){
		Object [] arr =g.getVertices().keySet().toArray();
		for(int i=0;i<arr.length;i++){
			g.getVertices().get(arr[i]).setWeight(Double.MAX_VALUE);
		}
	}
}
