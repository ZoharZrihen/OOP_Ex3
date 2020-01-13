package dataStructure;

import java.io.Serializable;
/**
 * This class represents the set of operations applicable on a
 * directional edge(src,dest) in a (directional) weighted graph.
 *
 */
public class edge implements edge_data, Serializable {
    private node source;
    private node dest;
    private int tag;
    private String info;
    private double weight;

    /**
     * Edge constructor
     * @param s the source node
     * @param d the destination node
     * @param w weight of the edge
     */
    public edge(node_data s, node_data d, double w){
        source=(node)s;
        dest= (node)d;
        weight=w;
    }
    /**
     * Copy constructor
     * creates a deep copy for edge e.
     */
    public edge(edge e){
        source=new node(e.source);
        dest=new node(e.dest);
        tag=e.getTag();
        info=e.getInfo();
        weight=e.getWeight();
    }
    /**
     * The id of the source node of this edge.
     * @return node source key
     */
    @Override
    public int getSrc() {
        return source.getKey();
    }
    /**
     * The id of the destination node of this edge
     * @return node destination key
     */
    @Override
    public int getDest() {
        return dest.getKey();
    }
    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return weight;
    }
    /**
     * return the remark (meta data) associated with this edge.
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }
    /**
     * Allows changing the remark (meta data) associated with this edge.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info=s;
    }
    /**
     * Temporal int data
     * which can be used be algorithms
     * @return int tag
     */
    @Override
    public int getTag() {
        return tag;
    }
    /**
     * Allow setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }
    /**
     * @return node source of the edge.
     */
    public node getSource(){
        return source;
    }
    /**
     * @return node destination of the edge.
     */
    public node getDestination(){
        return dest;
    }
}
