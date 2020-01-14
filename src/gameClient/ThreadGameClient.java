package gameClient;


import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import gui.Graph_Gui;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Range;

import javax.swing.*;

import static gameClient.MyGameGUI.DrawFruits;
import static gameClient.MyGameGUI.DrawRobots;

public class ThreadGameClient  {
    public static void main(String[] a) {
        run();
    }

    public static void init() {

    }


    public static void run() {
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
    }
}
