package gameClient;

import Server.fruits;
import Server.robot;
import Server.fruits;
import dataStructure.edge_data;
import oop_utils.OOP_Point3D;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

/**
 * This class represents a fruit in the game.
 * each fruit belongs to edge in the graph (by location).
 * there are 2 types of fruits -1(fruit can be eatn from high to low) or 1(from low to high).
 * each fruit as value (the higher the value is, more score for the player)
 */
public class Fruit {
    private edge_data edge;
    private Point3D location;
    private int type;
    private double value;

    /**
     * this constructor convert JSON string to fruit object.
     * @param str the JSON string with all the information about the fruit.
     * @throws JSONException
     */
    public Fruit(JSONObject str) throws JSONException {
        location = new Point3D(str.getString("pos"));
        type= str.getInt("type");
        value=str.getDouble("value");
        edge=null;
    }
    public Fruit(){
        location=new Point3D(0,0,0);
    }
    public Fruit(Fruit f){
        location=f.getLocation();
        type=f.getType();
        value=f.getValue();
        edge=f.getEdge();

    }
    public edge_data getEdge() {
        return edge;
    }

    public Point3D getLocation() {
        return location;
    }

    public int getType() {
        return type;
    }

    public double getValue() {
        return value;
    }

    public void setEdge(edge_data edge) {
        this.edge = edge;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setValue(double value) {
        this.value = value;
    }
    public double grap(Robot r, double dist) {
        double ans = 0.0D;
        if (this.edge != null && r != null) {
            int d = r.getDest();
            if (this.edge.getDest() == d) {
                Point3D rp = r.getLocation();
                if (dist > rp.distance2D(this.location)) {
                    ans = this.value;
                }
            }
        }
        return ans;
    }
}