package gameClient;


import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import gui.Graph_Gui;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;
import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import static gameClient.MyGameGUI.*;

/**
 * This class is a thread of the game.
 * The user runs this class and his game begins.
 * user need to choose level to play [0-23] and
 * way to play (automatic or manual) and then the game begins.
 * Goal of the game is to eat as many fruits as possible in the given time.
 */
public class ThreadGameClient implements Runnable {
    private static int level;
    public static KML_Logger kml=null;
    public static Thread thread = new Thread(new ThreadGameClient());
    public static void main(String[] a) {
        thread.start();
    }
    public void run() {
        level = Integer.parseInt(JOptionPane.showInputDialog(null, "Enter your scenario number: "));
        kml=new KML_Logger(level);
        game_service game = Game_Server.getServer(level);
        Zone play = new Zone(game);
        Graph_Gui gui = new Graph_Gui(play.getGraph());
        Range rangeX = new Range(gui.minXPos() - 0.001, gui.maxXPos() + 0.001);
        Range rangeY = new Range(gui.minYPos() - 0.001, gui.maxYPos() + 0.001);
        gui.DrawGraph(2000, 1000, rangeX, rangeY, gui.getGr());
        StdDraw.save("MyGraph.jpg");
        DrawFruits(play.getGame().getFruits());
        for (Fruit fruit : play.getFruits()) {
            setEdgeForFruit(fruit, gui.getGr());
        }
        String info = play.getGame().toString();
        try {
            JSONObject line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int numrobots = ttt.getInt("robots");
            int fruts = 2 % play.getFruits().size();
            for (int i = 0; i < numrobots; i++) {
                game.addRobot(play.getFruits().get(fruts).getEdge().getDest());
                fruts = (fruts + 1) % play.getFruits().size();
            }
            play.setRobots(game.getRobots());
            DrawRobots(play.getRobots());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Point3D p = play.getGraph().getNode(3).getLocation();
        StdDraw.enableDoubleBuffering();
     //   StdDraw.clear();
        StdDraw.setPlay(play);
        StdDraw.setGui(gui);
        StdDraw.setRangeX(rangeX);
        StdDraw.setRangeY(rangeY);
        StdDraw.picture((gui.minXPos()+gui.maxXPos())/2,(gui.minYPos()+gui.maxYPos())/2,"/gui/Ariel.png",rangeX.get_length(),rangeY.get_length());

        //Drawgraph(gui.getGr());
        StdDraw.save("GamePhoto.png");
        while (!StdDraw.isAutomatic() && !StdDraw.isManual()) {
            System.out.print("");
        }

        if (StdDraw.isAutomatic()) {
            Automatic(play, gui, rangeX, rangeY);
        }
        if(StdDraw.isManual()){
            Manual(play, gui, rangeX, rangeY);
        }
    }

    /**
     * This function makes the robot to move in automatic way and makes the robots to eat
     * as much fruits as possible.
     * function gets information about the robots in the game from the server,
     * then sending it to next node function to choose best place to move the robots.
     * @param game the game from the server that runs
     * @param gg the graph of the game.
     * @param play the Zone with all the information about the game.
     */
    private void moveRobots(game_service game, DGraph gg, Zone play) {
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
                    int speed = ttt.getInt("speed");

                    if(dest==-1) {
                        if (rid%3 == 0)
                             dest = nextNod(gg, src,play);
                        else if (rid%3 == 1)
                            dest= nextNode(gg,src,play);
                        else
                            dest = nextNoda(gg,src,play);

                        game.chooseNextEdge(rid, dest);
                        game.move();
                        try {
                            thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                        System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
                        System.out.println(ttt);
                    }
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }
    }

    /**
     * This function makes the robots to move in manual way by getting information from the user.
     * User choose the robot he wants to move (by the robot id),
     * then choose destination to move the robot to.
     * the destination must be to a node that is neighbour of the current robot's node location.
     * @param game game that the user plays
     * @param gg the graph of the game
     * @param play the Zone with all the game information
     */
    private void MoveRobotManual(game_service game, DGraph gg, Zone play){
        List<String> log = game.move();
        if(log!=null) {
            long t = game.timeToEnd();
            for(int i=0;i<log.size();i++) {
                String robot_json = log.get(i);
                try {
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject ttt = line.getJSONObject("Robot");
                    int dest = ttt.getInt("dest");

                    if(dest==-1) {
                        int rid = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose robot id to move"));
                        dest = Integer.parseInt(JOptionPane.showInputDialog(null, "Please choose destination to move the robot"));
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

    /**
     * This function uses our graph algorithm function to get the best way to the closest fruit in the game.
     * By using our algorithm we get the best path to the fruit and then
     * sending to robot to the next node in the path.
     * @param g the graph of the game
     * @param src the place the robot is
     * @param play
     * @return
     */
    private int nextNoda(DGraph g, int src, Zone play){
        int ans = -1;
        ArrayList<Fruit> fruits=play.getFruits();
        Fruit close = closeFruta(g,src,fruits);
        if (close.getEdge().getDest() == src)
            return close.getEdge().getSrc();
        Graph_Algo ga=new Graph_Algo(g);
        List<node_data> nodes=ga.shortestPath(src,close.getEdge().getDest());
        return nodes.get(1).getKey();
    }
    private int nextNod(DGraph g, int src, Zone play){
        int ans = -1;
        ArrayList<Fruit> fruits=play.getFruits();
        Fruit close = closeFrt(g,src,fruits);
        if (close.getEdge().getDest() == src)
            return close.getEdge().getSrc();
        Graph_Algo ga=new Graph_Algo(g);
        List<node_data> nodes=ga.shortestPath(src,close.getEdge().getDest());
        return nodes.get(1).getKey();
    }
    private int nextNode(DGraph g, int src, Zone play){
        int ans = -1;
        ArrayList<Fruit> fruits=play.getFruits();
        Fruit close = closeFruit(g,src,fruits);
        if (close.getEdge().getDest() == src)
            return close.getEdge().getSrc();
        Graph_Algo ga=new Graph_Algo(g);
        List<node_data> nodes=ga.shortestPath(src,close.getEdge().getDest());
        return nodes.get(1).getKey();
    }
    /**
     * This function finding the closest fruit to the src location.
     * @param g the graph of the game
     * @param src the location where the robot is.
     * @param fruits the list of the current fruits of the game.
     * @return
     */
    private Fruit closeFrt(DGraph g, int src, ArrayList<Fruit> fruits) {
        double min = Double.MAX_VALUE;
        Fruit res = null;
        // int ans = -1;
        Point3D s = g.getNode(src).getLocation();
        Iterator<Fruit> iter = fruits.iterator();
        while (iter.hasNext()) {
            Fruit f = iter.next();
            if (f.getEdge() == null) {
                setEdgeForFruit(f, g);
            }
            Graph_Algo ga = new Graph_Algo(g);
            double dist;
            if (src!=f.getEdge().getSrc() && src!=f.getEdge().getDest())
                dist =( ga.shortestPathDist(src, f.getEdge().getSrc()) + f.getEdge().getWeight());
            else if (src==f.getEdge().getSrc())
                dist = f.getEdge().getWeight();

            else
                dist = 1.7*f.getEdge().getWeight();
            if (dist < min) {
                min = dist;
                res = f;
            }

        }
        return res;
    }
    private Fruit closeFruit(DGraph g, int src, ArrayList<Fruit> fruits) {
        double min = Double.MAX_VALUE;
        Fruit res = null;
        // int ans = -1;
        Point3D s = g.getNode(src).getLocation();
        Iterator<Fruit> iter = fruits.iterator();
        while (iter.hasNext()) {
            Fruit f = iter.next();
            if (f.getEdge() == null) {
                setEdgeForFruit(f, g);
            }
            Graph_Algo ga = new Graph_Algo(g);
            double dist;
            if (src!=f.getEdge().getSrc() && src!=f.getEdge().getDest())
                dist =( ga.shortestPathDist(src, f.getEdge().getSrc()) + f.getEdge().getWeight());
            else if (src==f.getEdge().getSrc())
                dist = f.getEdge().getWeight();

            else
                dist = 1.7*f.getEdge().getWeight();
            if (dist*Math.pow(f.getValue(),3) < min) {
                min = dist;
                res = f;
            }

        }
        return res;
    }
    private Fruit closeFruta(DGraph g, int src, ArrayList<Fruit> fruits) {
        double min = Double.MAX_VALUE;
        Fruit res = null;
        // int ans = -1;
        Point3D s = g.getNode(src).getLocation();
        Iterator<Fruit> iter = fruits.iterator();
        while (iter.hasNext()) {
            Fruit f = iter.next();
            if (f.getEdge() == null) {
                setEdgeForFruit(f, g);
            }
            Graph_Algo ga = new Graph_Algo(g);
            double dist;
            if (src!=f.getEdge().getSrc() && src!=f.getEdge().getDest())
                dist =( ga.shortestPathDist(src, f.getEdge().getSrc()) + f.getEdge().getWeight());
            else if (src==f.getEdge().getSrc())
                dist = f.getEdge().getWeight();

            else
                dist = 1.7*f.getEdge().getWeight();
            if (dist*(f.getEdge().getWeight()) < min) {
                min = dist;
                res = f;
            }

        }
        return res;
    }
    /**
     * This function sets for fruit f an edge by its location.
     * The function is searching in the graph for the edge the fruit is located on.
     * We know that we found the right edge if the distance of the fruit from the src of the edge
     * plus the distance of the fruit to the dest of the edge is equal(or almost equal)
     * to the destination from src of the edge to dest of the edge.
     * @param f the fruit to sets edge for it
     * @param g the graph of the game
     */
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

    /**
     * simple function to calculate distance between 2 points.
     * @param a
     * @param b
     * @return
     */
    public double Distance(Point3D a, Point3D b){
        return Math.sqrt(Math.pow(a.x()-b.x(),2)+Math.pow(a.y()-b.y(),2));
    }

    /**
     * This function manages the game in automatic way, by using automatic moves of the robots.
     * @param play the Zone with all the information about the game
     * @param gui our function to open GUI window and draw the graph
     * @param rangeX
     * @param rangeY
     */
    public void Automatic(Zone play, Graph_Gui gui, Range rangeX, Range rangeY){
        play.getGame().startGame();
        while (play.getGame().isRunning()) {
            play.setRobots(play.getGame().getRobots());
            play.setFruits(play.getGame().getFruits());
            try {
                thread.sleep(40);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            StdDraw.clear();
            moveRobots(play.getGame(), play.getGraph(), play);
            try {
                thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
           // StdDraw.picture((gui.minXPos()+gui.maxXPos())/2,(gui.minYPos()+gui.maxYPos())/2,"/gui/Ariel.png",rangeX.get_length(),rangeY.get_length());
            StdDraw.picture((gui.minXPos()+gui.maxXPos())/2,(gui.minYPos()+gui.maxYPos())/2,"MyGraph.jpg",rangeX.get_length(),rangeY.get_length());
            DrawFruits(play.getGame().getFruits());
            DrawRobots(play.getRobots());
            StdDraw.show();
            try {
                thread.sleep(30);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
        String results = play.getGame().toString();
        System.out.println("Game Over: "+results);
        kml.kmlEnd();
    }

    /**
     * This function manages the game in manual way by using Manual moves of the robots, choosen by the user.
     *
     * @param play
     * @param gui
     * @param rangeX
     * @param rangeY
     */
    public void Manual(Zone play,Graph_Gui gui,Range rangeX,Range rangeY) {
        play.getGame().startGame();
        while (play.getGame().isRunning()) {
            play.setRobots(play.getGame().getRobots());
            play.setFruits(play.getGame().getFruits());
            MoveRobotManual(play.getGame(), play.getGraph(), play);
            StdDraw.picture((gui.minXPos() + gui.maxXPos()) / 2, (gui.minYPos() + gui.maxYPos()) / 2, "/gui/Ariel.png", rangeX.get_length(), rangeY.get_length());
            Drawgraph(gui.getGr());
            DrawFruits(play.getGame().getFruits());
            DrawRobots(play.getRobots());
            StdDraw.show();
           StdDraw.pause(10);
            StdDraw.clear();
        }
        String results = play.getGame().toString();
        kml.kmlEnd();
        System.out.println("Game Over: " + results);
    }
    }