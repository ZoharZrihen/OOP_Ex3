package gameClient;

import Server.game_service;
import dataStructure.DGraph;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class represent the Zone (Arena) of the game
 * in the Zone we store all the information about the game from the game server.
 * each list (robots,fruits) is dynamic and get updated all the time.
 */
public class Zone {
    private ArrayList<Robot> robots=new ArrayList<Robot>();
    private ArrayList<Fruit> fruits=new ArrayList<Fruit>();
    private DGraph graph;
    private game_service game;

    /**
     * This constructor get a game and init all the params with the information from the game.
     * @param game the game the user plays.
     */
    public Zone(game_service game){
        graph=new DGraph();
        graph.init(game.getGraph());
        Json2Fruits(game.getFruits());
        this.game=game;
    }

    /**
     * Function to convert a Json list of fruits to list of fruits.
     * @param fr Json list to convert
     */
    private void Json2Fruits(List<String> fr) {
        Iterator iter_f = fr.iterator();
        while (iter_f.hasNext()) {
            try {
                JSONObject f = new JSONObject(iter_f.next().toString());
                JSONObject f2 = f.getJSONObject("Fruit");
                Fruit fru = new Fruit(f2);
                fruits.add(fru);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public ArrayList<Robot> getRobots() {
        return robots;
    }

    public void setFruits(List<String> fr){
        fruits.clear();
        Iterator iter_f = fr.iterator();
        while (iter_f.hasNext()) {
            try {
                JSONObject f = new JSONObject(iter_f.next().toString());
                JSONObject f2 = f.getJSONObject("Fruit");
                Fruit fru = new Fruit(f2);
                fruits.add(fru);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public void setRobots(List<String> robots) {
            this.robots.clear();
            Iterator iter_f = robots.iterator();
            while (iter_f.hasNext()) {
                try {
                    JSONObject f = new JSONObject(iter_f.next().toString());
                    JSONObject f2 = f.getJSONObject("Robot");
                    Robot r = new Robot(f2);
                    this.robots.add(r);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<Fruit> fruits) {
        this.fruits = fruits;
    }

    public DGraph getGraph() {
        return graph;
    }

    public void setGraph(DGraph graph) {
        this.graph = graph;
    }
    public void setGame(game_service game){
        this.game=game;
    }
    public game_service getGame(){
        return game;
    }
}
