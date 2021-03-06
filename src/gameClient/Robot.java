package gameClient;

import dataStructure.node_data;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;

import java.util.Date;

/**
 * this class represents a robot in the game.
 * each robot has source (the current node on the graph where robot located)
 * each robot has score from eating fruits.
 * each robot in the game has different key.
 */
public class Robot {
    private int source;
    private int dest;
    private int key;
    private Point3D location;
    private double speed;
    private double score;

    /**
     * This constructor converts JSON string to robot.
     * @param str the JSON string with all the information for the robot.
     * @throws JSONException
     */
    public Robot(JSONObject str) throws JSONException {
        key=str.getInt("id");
        source=str.getInt("src");
        dest=str.getInt("dest");
        location=new Point3D(str.getString("pos"));
        speed=str.getDouble("speed");
        score=str.getDouble("value");
    }
    public Robot(int id,int src,int speed,Point3D p){
        key=id;
        source=src;
        this.speed=speed;
        location=new Point3D(p);
    }

    public int getSource() {
        return source;
    }

    public int getDest() {
        return dest;
    }

    public int getKey() {
        return key;
    }

    public Point3D getLocation() {
        return location;
    }

    public double getSpeed() {
        return speed;
    }

    public double getScore() {
        return score;
    }

    public void setSource(int source) {
        this.source = source;
    }

    public void setDest(int dest) {
        this.dest=dest;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setLocation(Point3D location) {
        this.location = location;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setScore(double score) {
        this.score = score;
    }

}
