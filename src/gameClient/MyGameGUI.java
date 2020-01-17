package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.*;
import gui.Graph_Gui;
import oop_dataStructure.OOP_DGraph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * This class has functions for the GUI of the game.
 * The functions of the class draws all the game items.
 */
public class MyGameGUI {
    public static void main(String[] a) {

    }

    /**
     * Convert the list of the fruits from the server to the GUI and draw the icon (criminal)
     * in the location of the fruit.
     * @param fruits lists of fruits from the server to draw.
     */
    public static void DrawFruits(List<String> fruits){
        Iterator iter_f = fruits.iterator();
        while (iter_f.hasNext()) {
            try {
                JSONObject f = new JSONObject(iter_f.next().toString());
                JSONObject f2=f.getJSONObject("Fruit");
                Fruit fru=new Fruit(f2);
                if(fru.getType()==1) {
                    StdDraw.picture(fru.getLocation().x(), fru.getLocation().y(), "/gui/criminal.png", 0.0005, 0.0005);
                }
                if(fru.getType()==-1){
                    StdDraw.picture(fru.getLocation().x(), fru.getLocation().y(), "/gui/prisoner.png", 0.0007, 0.0005);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * This function gets list of robots and draw each robot by its location on the map
     * with the police car icon.
     * @param robots list of robots to draw
     */
    public static void DrawRobots(List<Robot> robots){
        Iterator iter=robots.iterator();
        while(iter.hasNext()){
            Robot r= (Robot) iter.next();
            StdDraw.picture(r.getLocation().x(),r.getLocation().y(),"/gui/police.png",0.0007, 0.0005);
            StdDraw.setPenColor(Color.red);
            StdDraw.setPenRadius(0.009);
            StdDraw.text(r.getLocation().x(),r.getLocation().y()+0.0005,Integer.toString(r.getKey()));
        }
    }

    /**
     * This function gets a graph and draws it by draw each node and edge of the graph in its location.
     * @param g graph to draw
     */
    public static void Drawgraph(DGraph g){
        Collection<node_data> nodes = g.getV();
        Iterator iter = nodes.iterator();
        while (iter.hasNext()) {
            node n = (node) iter.next();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.02);
            StdDraw.point(n.getLocation().x(), n.getLocation().y());
            int key = n.getKey();
            StdDraw.text(n.getLocation().x() - 0.0001, n.getLocation().y() + 0.0002, Integer.toString(key));
            Collection<edge_data> edges = g.getE(n.getKey());
            Iterator iterE = edges.iterator();
            while (iterE.hasNext()) {
                edge e = (edge) iterE.next();
                StdDraw.setPenColor(Color.RED);
                StdDraw.setPenRadius(0.007);
                Point3D p1 = g.getNode(e.getSrc()).getLocation();
                Point3D p2 = g.getNode(e.getDest()).getLocation();
                StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.text(p1.x() + 0.7 * (p2.x() - p1.x()) - 0.0003, p1.y() + 0.7 * (p2.y() - p1.y()) + 0.0002, String.format("%.1f", e.getWeight()));
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.setPenRadius(0.02);
                StdDraw.point(p1.x() + 0.85 * (p2.x() - p1.x()), p1.y() + 0.85 * (p2.y() - p1.y()));

            }
        }
    }

}

