package Tests;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import gameClient.Zone;
import gui.Graph_Gui;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;

import static org.junit.Assert.*;

public class ZoneTest {
    static game_service game = Game_Server.getServer(0);
    static Zone play=new Zone(game);
    static DGraph g=new DGraph();
    @BeforeAll
    static void init() {
        Graph_Gui gui = new Graph_Gui(play.getGraph());
        String info = play.getGame().toString();
        try {
            JSONObject line = new JSONObject(info);
            int fruit=0;
            JSONObject ttt = line.getJSONObject("GameServer");
            int numrobots = ttt.getInt("robots");
            for (int i = 0; i < numrobots; i++) {
                play.getGame().addRobot(i);
            }
            play.setRobots(game.getRobots());

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void getRobots() {
        assertEquals(play.getRobots().size(),0);
    }

    @Test
    public void getGraph() {
        play.setGraph(g);
        assertEquals(g,play.getGraph());
    }
    @Test
    public void setGame() {
        play.setGame(game);
        assertEquals(play.getGame(),play.getGame());
    }

}