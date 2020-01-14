package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.node_data;
import gui.Graph_Gui;
import oop_dataStructure.OOP_DGraph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class MyGameGUI {
    public static void main(String[] a) {
        init();}

    public static void init(){
        int scenario = Integer.parseInt(JOptionPane.showInputDialog(null,"Enter your scenario number: "));
        game_service game = Game_Server.getServer(scenario);
        String g=game.getGraph();
        DGraph gg= new DGraph();
        gg.init(g);
        Graph_Gui gui=new Graph_Gui(gg);
        gui.DrawGraph(2000, 1000, new Range(gui.minXPos()-0.001,gui.maxXPos()+0.001), new Range(gui.minYPos()-0.001, gui.maxYPos()+0.001),gui.getGr());
        DrawFruits(game.getFruits());
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("robots");
            System.out.println(info);
            System.out.println(g);
            int fruits=ttt.getInt("fruits");
            System.out.println(fruits);
            Iterator<String> f_iter = game.getFruits().iterator();

            while(f_iter.hasNext()) {

            }
            int src_node = 0;  // arbitrary node, you should start at one of the fruits
            for(int a = 0;a<rs;a++) {
                game.addRobot(src_node+a);
            }
        }
        catch (JSONException e) {e.printStackTrace();}
        game.startGame();
        // should be a Thread!!!
        while(game.isRunning()) {
       //     moveRobots(game, gg);
        }
        String results = game.toString();
        System.out.println("Game Over: "+results);
    }
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
    public static void DrawRobots(List<Robot> robots){
        Iterator iter=robots.iterator();
        while(iter.hasNext()){
                Robot r= (Robot) iter.next();
                StdDraw.picture(r.getLocation().x(),r.getLocation().y(),"/gui/police.png",0.0007, 0.0005);
        }
    }

    }

