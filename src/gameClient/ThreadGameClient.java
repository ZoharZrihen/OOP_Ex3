package gameClient;


import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import gui.Graph_Gui;
import oop_dataStructure.oop_edge_data;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import javax.swing.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;

import static gameClient.MyGameGUI.*;

public class ThreadGameClient implements Runnable {
    public static void main(String[] a) {
        Thread thread = new Thread(new ThreadGameClient());
        thread.start();
    }

    public static void init() {

    }


    public void run() {
        int scenario = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter your scenario number: "));
        game_service game = Game_Server.getServer(scenario);
        Zone play = new Zone(game);
        Graph_Gui gui = new Graph_Gui(play.getGraph());
        gui.DrawGraph(2000, 1000, new Range(gui.minXPos() - 0.001, gui.maxXPos() + 0.001), new Range(gui.minYPos() - 0.001, gui.maxYPos() + 0.001), gui.getGr());
        DrawFruits(game.getFruits());
        String info = game.toString();
        try {
            JSONObject line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int numrobots = ttt.getInt("robots");
            for (int i = 0; i < numrobots; i++) {
                game.addRobot(i);
            }
            play.setRobots(game.getRobots());
            DrawRobots(play.getRobots());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        //Point3D p = play.getGraph().getNode(3).getLocation();
        //StdDraw.setPenColor(Color.BLACK);
        //StdDraw.setPenRadius(0.005);
        // StdDraw.text(p.x(), p.y() + 0.001, "Please click on Robot to start, then select destination to move");
        StdDraw.enableDoubleBuffering();
        play.getGame().startGame();
          /*  while (game.isRunning()) {
                play = new Zone(game);
                play.setRobots(game.getRobots());
                moveRobots(game, play.getGraph(), play);
                Drawgraph(gui.getGr());
                DrawFruits(game.getFruits());
                DrawRobots(play.getRobots());
                StdDraw.show();
                StdDraw.pause(10);
                StdDraw.clear();
            }*/
        while (play.getGame().isRunning()) {
            play.setRobots(game.getRobots());
            int robotnum = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose robot id to move"));
            int dest = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose destination to move the robot"));
            List<String> log = play.getGame().move();
            if (log != null) {
                long t = play.getGame().timeToEnd();
                for (int i = 0; i < log.size(); i++) {
                    String robot_json = log.get(i);
                    try {
                        JSONObject line = new JSONObject(robot_json);
                        JSONObject ttt = line.getJSONObject("Robot");
                        int rid = ttt.getInt("id");
                        int src = ttt.getInt("src");
                        int d = ttt.getInt("dest");
                        if(d==-1) {
                            play.getGame().chooseNextEdge(robotnum, dest);
                            play.getGame().move();
                        }
                        Drawgraph(gui.getGr());
                        DrawFruits(play.getGame().getFruits());
                        DrawRobots(play.getRobots());
                        StdDraw.show();
                        StdDraw.pause(10);
                        StdDraw.clear();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private void moveRobots(game_service game, DGraph gg,Zone play) {
        List<String> log = game.move();
        if(log!=null) {
            long t = game.timeToEnd();
            for(int i=0;i<log.size();i++) {
                String robot_json = log.get(i);
                try {
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject ttt = line.getJSONObject("Robot");
                    int rid = ttt.getInt("id");
                    int src = ttt.getInt("src");
                    int dest = ttt.getInt("dest");

                    if(dest==-1) {
                        dest = nextNode(gg, src,play);
                        game.chooseNextEdge(rid, dest);
                        game.move();
                        System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
                        System.out.println(ttt);
                    }
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }
    }
    private int nextNode(DGraph g, int src, Zone play) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
     /*   int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
        ans = itr.next().getDest();*/
        ArrayList<Fruit> fruits=play.getFruits();
        int dest=ClosestFruit(g,src,fruits);
        Graph_Algo ga=new Graph_Algo(g);
        //System.out.println(ga.shortestPathDist(0,4)+"zoharrrrrr");
        List<node_data> nodes=ga.shortestPath(src,dest);
        Iterator<node_data> iter=nodes.iterator();
        while(iter.hasNext()){
            int key=iter.next().getKey();
            if(key!=src){
                ans=key;
                break;
            }
        }
        return ans;
    }
    private int ClosestFruit(DGraph g, int src,ArrayList<Fruit> fruits){
        double min=Double.MAX_VALUE;
        int ans=-1;
        Point3D s=g.getNode(src).getLocation();
        Iterator<Fruit> iter=fruits.iterator();
        while(iter.hasNext()){
            Fruit f=iter.next();
            if(f.getEdge()==null) {
                setEdgeForFruit(f, g);
            }
            Point3D d=f.getLocation();
            double dist=Math.sqrt(Math.pow(s.x()-d.x(),2)+Math.pow(s.y()-d.y(),2));
            if(dist<min){
                min=dist;
                if(f.getType()==-1){
                    ans=f.getEdge().getDest();
                }
                if(f.getType()==1){
                    ans=f.getEdge().getSrc();
                }
            }
        }
        return ans;
    }
    public void setEdgeForFruit(Fruit f, DGraph g) {
        Point3D sf = f.getLocation();
        Iterator<node_data> iterNode = g.getV().iterator();
        while (iterNode.hasNext()) {
            node_data n=iterNode.next();
            Point3D source=n.getLocation();
            Iterator<edge_data> iterEdge = g.getE(n.getKey()).iterator();
            while(iterEdge.hasNext()){
                edge_data e=iterEdge.next();
                int dest=e.getDest();
                Point3D destination=g.getNode(dest).getLocation();
                if(Math.abs(Distance(sf,source)+Distance(sf,destination)-Distance(source,destination))<0.00000001) {
                    f.setEdge(e);
                    return;
                }
            }
        }
    }
    public double Distance(Point3D a,Point3D b){
        return Math.sqrt(Math.pow(a.x()-b.x(),2)+Math.pow(a.y()-b.y(),2));
    }
    private Robot ComparetoRobot(List<Robot>robots,Point3D p ){
        Iterator<Robot> iter=robots.iterator();
        while(iter.hasNext()){
            Robot r=iter.next();
            if(r.getLocation()==p){
                return r;
            }
        }
        return null;
    }
    private void MoveRobot(Robot r,game_service game,DGraph g ){
        if(StdDraw.isMousePressed()){
            int dest=-1;
            Point3D p=new Point3D(StdDraw.mouseX(),StdDraw.mouseY(),0);
            Iterator<node_data>iter=g.getV().iterator();
            while(iter.hasNext()){
                node_data n=iter.next();
                if(p==n.getLocation())
                    dest=n.getKey();
            }
            game.chooseNextEdge(r.getKey(),dest);
            game.move();
        }
    }
}
