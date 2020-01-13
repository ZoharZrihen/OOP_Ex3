package dataStructure;

import utils.Point3D;

import java.io.Serializable;

/**
 * This class represents the set of operations applicable on a
 * node (vertex) in a (directional) weighted graph.
 *
 */
public class node implements node_data, Serializable {
    private int key;
    private Point3D location;
    private double weight;
    private String info;
    private int tag;

    /**
     * Default constructor for the node.
     */
    public node(){
        key=0;
        location=new Point3D(0,0,0);
        weight=0;
        info="";
        tag=0;
    }
    /**
     *  Constructor for the node.
     * @param k- the id of the node
     */
    public node(int k){
        key=k;
        location=new Point3D(0,0,0);
        weight=0;
        info="";
        tag=0;
    }
    /**
     * constructor for the node.
     * @param k- id for the node
     * @param p- the location of the node
     *
     */
    public node(int k,Point3D p){
        key=k;
        location=new Point3D(p);
        weight=0;
        info="";
        tag=0;
    }
    /**
     * Copy constructor for the node.
     * implements deep copy and creates new node.
     */
    public node(node n){
        key=n.getKey();
        location=n.getLocation();
        weight=n.getWeight();
        info=n.getInfo();
        tag=n.getTag();
    }
    /**
     * Return the key (id) associated with this node.
     * @return key.
     */
    @Override
    public int getKey() {
        return key;
    }
    /** Return the location (of applicable) of this node.
     * @return location.
     */

    @Override
    public Point3D getLocation() {
        return location;
    }
    /** Allows changing this node's location.
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(Point3D p) {
        location=new Point3D(p);
    }
    /**
     * Return the weight associated with this node.
     * @return weight
     */
    @Override
    public double getWeight() {
        return weight;
    }
    /**
     * Allows changing this node's weight.
     * @param w - the new weight
     */

    @Override
    public void setWeight(double w) {
        weight=w;
    }
    /**
     * return the remark (meta data) associated with this node.
     * @return
     */
    @Override
    public String getInfo() {
        return info;
    }
    /**
     * Allows changing the remark (meta data) associated with this node.
     * @param s
     */
    @Override
    public void setInfo(String s) {
        info=s;
    }
    /**
     * Temporal int data,
     * which can be used be algorithms
     * @return
     */
    @Override
    public int getTag() {
        return tag;
    }
    /**
     * Allow setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        tag=t;
    }

}
