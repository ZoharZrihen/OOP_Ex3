package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.node_data;
import gui.Graph_Gui;
import oop_dataStructure.OOP_DGraph;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Range;

import javax.swing.*;
import java.util.Collection;
import java.util.Iterator;

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
        gui.DrawGraph(1000, 600, new Range(gui.minXPos()-0.001,gui.maxXPos()+0.001), new Range(gui.minYPos()-0.001, gui.maxYPos()+0.001),gui.getGr(),game.getFruits());
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

    }

