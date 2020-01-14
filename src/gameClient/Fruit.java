package gameClient;

import Server.fruits;
import Server.robot;
import Server.fruits;
import dataStructure.edge_data;
import oop_utils.OOP_Point3D;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

public class Fruit {
    private edge_data edge;
    private Point3D location;
    private int type;
    private double value;

    public Fruit(JSONObject str) throws JSONException {
        location = new Point3D(str.getString("pos"));
        type= str.getInt("type");
        value=str.getDouble("value");
        edge=null;
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