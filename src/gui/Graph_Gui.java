package gui;

import dataStructure.DGraph;
import dataStructure.edge;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node;
import dataStructure.node_data;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.Range;
import utils.StdDraw;
import java.awt.Color;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * this class represent a GUI for the graph, you can use it to draw random graph
 * or a graph of your own.
 * The gui is an extension of StdDraw library.
 * you can use the gui to draw a graph and use algorithms on it.
 * @authors Zohar Zrihen and Arthur Boltak.
 */
public class Graph_Gui {
    private graph gr;

    public static void main(String[] args) {
        Graph_Gui gg = new Graph_Gui();
        gg.DrawGraph(1000, 600, new Range(-10, 60), new Range(-10, 60),gg.getGr());
        DGraph g=new DGraph();
        g.addNode(new node(1,new Point3D(20,20,0)));
        g.addNode(new node(2,new Point3D(30,30,0)));
        g.connect(1,2,5);
        g.connect(2,1,3);
        Graph_Gui g1=new Graph_Gui(g);
        DGraph g11=new DGraph();
        Graph_Gui gg2=new Graph_Gui(g11);
       // gg2.DrawGraph(1000, 600, new Range(-10, 60), new Range(-10, 60),gg2.getGr());
    }

    /**
     * this is a default constructor which building a random graph using graph factory.
     */
    public Graph_Gui() {
        gr = GraphFactory();

    }

    /**
     * this constructor init graph g as the graph to draw.
     * @param g graph to init and draw.
     */
    public Graph_Gui(graph g) {
        if(g==null ||g.nodeSize()==0){
            g=new DGraph();
        }
        gr = new DGraph((DGraph) g);
    }

    /**
     * this function draws the graph you send to, using StdDraw library.
     * @param w width of the canvas
     * @param h height of the canvas
     * @param rx range of x axis
     * @param ry range of y axis
     * @param g the graph you want to draw.
     */
    public void DrawGraph(int w, int h, Range rx, Range ry, DGraph g) {
        if(g==null||g.nodeSize()==0){
            g=new DGraph();
        }
        StdDraw.setgraph(g);
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(rx.get_min(), rx.get_max());
        StdDraw.setYscale(ry.get_min(), ry.get_max());
        Collection<node_data> nodes = gr.getV();
        Iterator iter = nodes.iterator();
        while (iter.hasNext()) {
            node n = (node) iter.next();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.02);
            StdDraw.point(n.getLocation().x(), n.getLocation().y());
            int key = n.getKey();
            StdDraw.text(n.getLocation().x() - 0.0001, n.getLocation().y() + 0.0002, Integer.toString(key));
            Collection<edge_data> edges = gr.getE(n.getKey());
            Iterator iterE = edges.iterator();
            while (iterE.hasNext()) {
                edge e = (edge) iterE.next();
                StdDraw.setPenColor(Color.RED);
                StdDraw.setPenRadius(0.007);
                Point3D p1 = gr.getNode(e.getSrc()).getLocation();
                Point3D p2 = gr.getNode(e.getDest()).getLocation();
                StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.text(p1.x() + 0.7 * (p2.x() - p1.x())-0.0003, p1.y() + 0.7 * (p2.y() - p1.y()) + 0.0002, String.format("%.1f",e.getWeight()));
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.setPenRadius(0.02);
                StdDraw.point(p1.x() + 0.85 * (p2.x() - p1.x()), p1.y() + 0.85 * (p2.y() - p1.y()));

            }
        }
        StdDraw.save("MyGraph.jpg");
    }
    public void DrawGraph(int w, int h, Range rx, Range ry, DGraph g, List<String> fruits) {
        if (g == null || g.nodeSize() == 0) {
            g = new DGraph();
        }
        StdDraw.setgraph(g);
        StdDraw.setCanvasSize(w, h);
        StdDraw.setXscale(rx.get_min(), rx.get_max());
        StdDraw.setYscale(ry.get_min(), ry.get_max());
        Collection<node_data> nodes = gr.getV();
        Iterator iter = nodes.iterator();
        while (iter.hasNext()) {
            node n = (node) iter.next();
            StdDraw.setPenColor(Color.BLACK);
            StdDraw.setPenRadius(0.02);
            StdDraw.point(n.getLocation().x(), n.getLocation().y());
            int key = n.getKey();
            StdDraw.text(n.getLocation().x() - 0.0001, n.getLocation().y() + 0.0002, Integer.toString(key));
            Collection<edge_data> edges = gr.getE(n.getKey());
            Iterator iterE = edges.iterator();
            while (iterE.hasNext()) {
                edge e = (edge) iterE.next();
                StdDraw.setPenColor(Color.RED);
                StdDraw.setPenRadius(0.007);
                Point3D p1 = gr.getNode(e.getSrc()).getLocation();
                Point3D p2 = gr.getNode(e.getDest()).getLocation();
                StdDraw.line(p1.x(), p1.y(), p2.x(), p2.y());
                StdDraw.setPenColor(Color.BLUE);
                StdDraw.text(p1.x() + 0.7 * (p2.x() - p1.x()) - 0.0003, p1.y() + 0.7 * (p2.y() - p1.y()) + 0.0002, String.format("%.1f", e.getWeight()));
                StdDraw.setPenColor(Color.YELLOW);
                StdDraw.setPenRadius(0.02);
                StdDraw.point(p1.x() + 0.85 * (p2.x() - p1.x()), p1.y() + 0.85 * (p2.y() - p1.y()));

            }
        }
            Iterator iter_f = fruits.iterator();
            while (iter_f.hasNext()) {
                System.out.println(iter_f.next());
                JSONObject f = new JSONObject(iter_f.next());
                try {
                    JSONObject f2=f.getJSONObject("Fruit");
                    Point3D p = new Point3D(f2.getString("pos"));
                    int type= f2.getInt("type");
                    double value=f2.getDouble("value");
                    StdDraw.picture(p.x(),p.y(),"criminal.png");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    /**
     * this is a graph factory to draw a random graph.
     * the random graph has 10 vertices in random location.
     * @return
     */
    public DGraph GraphFactory() {
        DGraph gr = new DGraph();
        for (int i = 1; i < 11; i++) {
            gr.addNode(new node(i, new Point3D((int) (Math.random() * 50) + 1, (int) (Math.random() * 50) + 1, 0)));
        }
        for (int i = 1; i < 9; i++) {
            gr.connect(i, i + 1, (int) (Math.random() * 20) + 1);
            gr.connect(i, i + 2, (int) (Math.random() * 20) + 1);
        }
        return gr;
    }

    public DGraph getGr(){
        return (DGraph) gr;
    }
    public double maxXPos(){
        double max=0;
        Iterator iter=gr.getV().iterator();
        while(iter.hasNext()){
            node_data n=(node_data) iter.next();
            Point3D p=n.getLocation();
            if(p.x()>max) {
                max=p.x();
            }
        }
        return max;
    }
    public double minXPos(){
        double min=Double.MAX_VALUE;
        Iterator iter=gr.getV().iterator();
        while(iter.hasNext()){
            node_data n=(node_data) iter.next();
            Point3D p=n.getLocation();
            if(p.x()<min) {
                min=p.x();
            }
        }
        return min;
    }
    public double maxYPos(){
        double max=0;
        Iterator iter=gr.getV().iterator();
        while(iter.hasNext()){
            node_data n=(node_data) iter.next();
            Point3D p=n.getLocation();
            if(p.y()>max) {
                max=p.y();
            }
        }
        return max;
    }
    public double minYPos(){
        double min=Double.MAX_VALUE;
        Iterator iter=gr.getV().iterator();
        while(iter.hasNext()){
            node_data n=(node_data) iter.next();
            Point3D p=n.getLocation();
            if(p.y()<min) {
                min=p.y();
            }
        }
        return min;
    }
}
