package dataStructure;

import org.json.JSONArray;
import org.json.JSONObject;
import utils.Point3D;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * This class represents a directional weighted graph.
 * The class has a road-system or communication network in mind - and support a large number of nodes (over 1,000,000).
 *
 */
public class DGraph implements graph, Serializable {
	private  int numOfEdgesG=0;
	private HashMap<Integer, node_data> vertices;
	private HashMap<Integer,HashMap<Integer,edge_data>> edges;
    private int numOfVertices;
	private int ModeCount;
	/**
	 * default constructor
	 * sets the hash maps for edges and vertices.
	 *
	 */
	public DGraph(){
		vertices=new HashMap<Integer, node_data>();
		edges=new HashMap<Integer, HashMap<Integer, edge_data>>();
		numOfVertices=0;
		ModeCount=0;
		numOfEdgesG=0;
	}
	/**
	 * copy constructor
	 * implements shallow copy for the graph
	 */
	public DGraph(DGraph gr) {
		if (gr == null) {
			gr = new DGraph();
		} else {
			numOfVertices = gr.nodeSize();
			vertices = new HashMap<>(gr.getVertices());
			edges = new HashMap<>(gr.getEdges());
			ModeCount = 0;
		}
	}
	/**
	 * this function makes a deep copy for the graph.
	 * @return deep copy of this graph.
	 */
	public graph copy() {
		DGraph g = new DGraph();
		Collection<node_data> n = vertices.values();
		Iterator iter = n.iterator();
		while(iter.hasNext()) {
			node_data n1 = (node_data) iter.next();
			g.addNode(n1);
		}
		Collection<HashMap<Integer,edge_data>> e = edges.values();
		Iterator iterE = e.iterator();
		while(iterE.hasNext()) {
			HashMap<Integer, edge_data> e1 = (HashMap<Integer, edge_data>) iterE.next();
			Collection<edge_data> e2 = e1.values();
			Iterator iterE2 = e2.iterator();
			while(iterE2.hasNext()) {
				edge_data edge = (edge_data) iterE2.next();
				g.connect(edge.getSrc(), edge.getDest(), edge.getWeight());
			}
		}
		g.numOfEdgesG = this.edgeSize();
		g.ModeCount = this.getMC();
		return g;
	}
	/**
	 * return the node_data by the node_id,
	 * @param key - the node_id
	 * @return the node_data by the node_id, null if none.
	 */
	@Override
	public node_data getNode(int key) {
		if(vertices.containsKey(key)){
			return vertices.get(key);
		}
		else return null;
	}
	/**
	 * return the edge_data (src,dest), null if none.
	 * @param src
	 * @param dest
	 * @return the edge_data
	 */
	@Override
	public edge_data getEdge(int src, int dest) {
		if(vertices.containsKey(src)&& vertices.containsKey(dest)){
			edge_data e=edges.get(src).get(dest);
			return e;
		}
		else return null;
	}
	/**
	 * add a new node to the graph with the given node_data.
	 * if there is already node with same id, throw exception.
	 * @param n
	 */
	@Override
	public void addNode(node_data n) {
	    node n1= (node) n;
		if(!vertices.containsKey(n1.getKey())) {
			vertices.put(n1.getKey(),n1);
			edges.put(n1.getKey(),new HashMap<Integer, edge_data>());
			numOfVertices++;
			ModeCount++;
		}
		else{
			throw new RuntimeException("This vertex already exists");
		}
	}
	/**
	 * Connect an edge with weight w between node src to node dest.
	 * @param src - the source of the edge.
	 * @param dest - the destination of the edge.
	 * @param w - positive weight representing the cost (aka time, price, etc) between src-->dest.
	 *    if w<0 or if src\dest doesn't exist throw exception.
	 */
	@Override
	public void connect(int src, int dest, double w) { // if user tries to add same edge, throw exception.
		try {
			if(w<0){
				throw new RuntimeException("Weight of edge can't be negative.");
			}
			edge e=new edge(vertices.get(src),vertices.get(dest),w);
            edges.get(src).put(dest,e);
            numOfEdgesG++;
			ModeCount++;
		} catch (Exception e) {
			 e.printStackTrace();
		}
	}
	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the nodes in the graph.
	 * @return Collection<node_data>
	 */

	@Override
	public Collection<node_data> getV() {
		return vertices.values();
	}
	/**
	 * This method return a pointer (shallow copy) for the
	 * collection representing all the edges getting out of
	 * the given node (all the edges starting (source) at the given node).
	 * if node_id not exist, throw exception.
	 * @return Collection<edge_data>
	 */
	@Override
	public Collection<edge_data> getE(int node_id) {
		if(!vertices.containsKey(node_id)){
			throw new RuntimeException("This node id is wrong.");
		}
		return edges.get(node_id).values();
	}
	/**
	 * Delete the node (with the given ID) from the graph -
	 * and removes all edges which starts or ends at this node.
	 * This method also removes all the edges connected to this node (by src or dest).
	 * @return the data of the removed node (null if none).
	 * @param key
	 */
	@Override
	public node_data removeNode(int key) { // if node doesn't exist, return null.
        try {
            node_data n = vertices.remove(key);
            numOfVertices--;
            ModeCount++;
            Object[] arr = vertices.keySet().toArray();
            for (int i = 0; i < arr.length; i++) { // remove all edges of this node
				if(edges.get(arr[i]).get(key)!=null){
					edges.get(arr[i]).remove(key);
					numOfEdgesG--;
				}
            }
            int sub=0;
            if(edges.get(key)!=null){
            	sub=edges.get(key).size();
			}
            edges.remove(key);
            numOfEdgesG-=sub;
            return n;
        } catch(Exception e){
            return null;
        }
    }
	/**
	 * Delete the edge from the graph,
	 * @param src
	 * @param dest
	 * @return the data of the removed edge (null if none).
	 */
	@Override
	public edge_data removeEdge(int src, int dest) { // if edge doesnt exist return null
		try {
			numOfEdgesG--;
			ModeCount++;
			return edges.get(src).remove(dest);
		}
		catch (Exception e) {
			return null;
		}
	}
	/** return the number of vertices (nodes) in the graph.
	 * This method runs in O(1), because we always counting the nodes when adding\removing them.
	 * @return
	 */
	@Override
	public int nodeSize() {
		return numOfVertices;
	}
	/**
	 * return the number of edges (assume directional graph).
	 * This method runs in O(1), because we always counting the edges when adding\removing them.
	 * @return
	 */
	@Override
	public int edgeSize() {
		return numOfEdgesG;
	}
	/**
	 * return the Mode Count - for testing changes in the graph.
	 * this method counting when adding\ removing any node\edge
	 * @return ModeCount
	 */
	@Override
	public int getMC() {
		return ModeCount;
	}


	public HashMap<Integer,node_data> getVertices(){
		return vertices;
	}
	public HashMap<Integer,HashMap<Integer,edge_data>> getEdges(){
		return edges;
	}

    public String toString(){ // simple toString, just for testing. not useable for anything.
        String t="";
	    Object[] arr=vertices.keySet().toArray();
        for(int i=0;i<arr.length;i++){
            t+=vertices.get(arr[i]).getKey() + " -> " ;
        }
        return t;
    }
	public void init(String jsonSTR) {
		try {
			this.init();
			JSONObject graph = new JSONObject(jsonSTR);
			JSONArray nodes = graph.getJSONArray("Nodes");
			JSONArray edges = graph.getJSONArray("Edges");

			int i;
			int s;
			for(i = 0; i < nodes.length(); ++i) {
				s = nodes.getJSONObject(i).getInt("id");
				String pos = nodes.getJSONObject(i).getString("pos");
				Point3D p = new Point3D(pos);
				this.addNode(new node(s, p));
			}

			for(i = 0; i < edges.length(); ++i) {
				s = edges.getJSONObject(i).getInt("src");
				int d = edges.getJSONObject(i).getInt("dest");
				double w = edges.getJSONObject(i).getDouble("w");
				this.connect(s, d, w);
			}
		} catch (Exception var10) {
			var10.printStackTrace();
		}

	}
	public void init(){
		vertices=new HashMap<Integer, node_data>();
		edges=new HashMap<Integer, HashMap<Integer, edge_data>>();
		numOfVertices=0;
		ModeCount=0;
		numOfEdgesG=0;
	}
}
