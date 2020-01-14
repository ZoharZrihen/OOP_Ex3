package gameClient;


import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
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
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static gameClient.MyGameGUI.DrawFruits;
import static gameClient.MyGameGUI.DrawRobots;

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
        StdDraw.enableDoubleBuffering();
        BufferedImage buf = new BufferedImage(2000,1000,BufferedImage.TYPE_INT_ARGB);
      //  BufferedImage buf2 = new BufferedImage(2000,1000,BufferedImage.TYPE_INT_ARGB);
        gui.DrawGraph(2000, 1000, new Range(gui.minXPos() - 0.001, gui.maxXPos() + 0.001), new Range(gui.minYPos() - 0.001, gui.maxYPos() + 0.001), gui.getGr());
      //  Graphics2D gr = buf.createGraphics();
       // StdDraw.setOffscreenImage(buf);
       // StdDraw.setOffscreen(gr);
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

        game.startGame();
        while (game.isRunning()) {
        //         StdDraw.clear();
                play=new Zone(game);
                play.setRobots(game.getRobots());
                moveRobots(game,play.getGraph());
              //  StdDraw.picture(35,35,"MyGraph.jpg",1,1);
                DrawFruits(game.getFruits());
                DrawRobots(play.getRobots());
         //   StdDraw.show();

          //  Graphics2D gr2 = buf2.createGraphics();
           // StdDraw.setOffscreenImage(buf2);
           // StdDraw.setOffscreen(gr2);

          //  Graph_Gui gui2 = new Graph_Gui(play.getGraph());
           // gui2.DrawGraph(2000, 1000, new Range(gui.minXPos() - 0.001, gui.maxXPos() + 0.001), new Range(gui.minYPos() - 0.001, gui.maxYPos() + 0.001), gui.getGr());

        }
    }
    private static void moveRobots(game_service game, DGraph gg) {
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
                        dest = nextNode(gg, src);
                        game.chooseNextEdge(rid, dest);
                        System.out.println("Turn to node: "+dest+"  time to end:"+(t/1000));
                        System.out.println(ttt);
                    }
                }
                catch (JSONException e) {e.printStackTrace();}
            }
        }
    }
    private static int nextNode(DGraph g, int src) {
        int ans = -1;
        Collection<edge_data> ee = g.getE(src);
        Iterator<edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random()*s);
        int i=0;
        while(i<r) {itr.next();i++;}
        ans = itr.next().getDest();
        return ans;
    }
}
