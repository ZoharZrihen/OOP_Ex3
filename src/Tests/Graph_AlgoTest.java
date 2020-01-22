package Tests;

import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.node;
import dataStructure.node_data;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class Graph_AlgoTest {
    Graph_Algo ga=new Graph_Algo();

    @Test
    public void init() {
        DGraph g=new DGraph();
        g.addNode(new node(1));
        g.addNode(new node(2));
        g.connect(1,2,4);
        ga.init(g);
        assertEquals(ga.getGr().toString(),g.toString());
    }

    @Test
    public void save() {
        DGraph g1=new DGraph();
        g1.addNode(new node(1));
        g1.addNode(new node(2));
        g1.addNode(new node(3));
        ga.init(g1);
        System.out.println("before: " + ga.getGr().toString());
        ga.save("myGraph.txt");
        ga.getGr().getVertices().clear();
        ga.init("myGraph.txt");
        System.out.println(" after (should be same)" + ga.getGr().toString());

    }

    @Test
    public void isConnected() {
        DGraph gra=new DGraph();
        for(int i=1;i<5;i++){
            gra.addNode(new node(i));
        }
        // gra.addNode(new node(6));

        gra.connect(1,2,0);
        gra.connect(2,3,0);
        gra.connect(3,4,0);
        // gra.connect(4,5,0);
        // gra.connect(5,4,0);
        gra.connect(4,3,0);
        gra.connect(3,2,0);
        gra.connect(2,1,0);
        // gra.connect(5,6,0);
        ga.init(gra);
        assertTrue(ga.isConnected());
        // gra.removeEdge(3,4);
        //assertFalse(ga.isConnected());
        //gra.connect(3,4,0);
        gra.removeNode(3);
        assertFalse(ga.isConnected());
    }


    @Test
    public void shortestPathDist() {
        DGraph gra=new DGraph();
        for(int i=1;i<5;i++){
            gra.addNode(new node(i));
        }
        gra.connect(1,2,3);
        gra.connect(1,3,2);
        gra.connect(1,4,7);
        gra.connect(2,4,3);
        gra.connect(3,4,5);
        ga.init(gra);
        System.out.println(ga.shortestPathDist(1,4));
        int k=0;
    }

    @Test
    public void shortestPath() {
        DGraph gra=new DGraph();
        for(int i=1;i<6;i++){
            gra.addNode(new node(i));
        }
        gra.connect(1,2,3);
        gra.connect(1,3,2);
        gra.connect(1,4,7);
        gra.connect(2,4,3);
        gra.connect(3,4,5);
        ga.init(gra);
        //System.out.println(ga.shortestPathDist(1,4));
        List <node_data> ans;
        ans = ga.shortestPath(1,4);
        Iterator iter=ans.iterator();
        while (iter.hasNext()){
            node n=(node)iter.next();
            System.out.print(n.getKey() + " ---> ");
        }
    }

    @Test
    public void TSP() {
        DGraph gra=new DGraph();
        for(int i=1;i<8;i++){
            gra.addNode(new node(i));
        }
        gra.connect(1,2,3);
        gra.connect(1,3,2);
        gra.connect(1,4,7);
        gra.connect(2,4,3);
        gra.connect(3,4,5);
        gra.connect(4,5,3);
        gra.connect(5,6,3);
        gra.connect(6,7,3);
        gra.connect(2,6,3);
        gra.connect(7,1,3);
        ga.init(gra);
        List<Integer> targets=new ArrayList<>();
        targets.add(1);
       // targets.add(3);
       // targets.add(4);
      //  targets.add(6);
        targets.add(7);
        ArrayList<node_data> ans= (ArrayList<node_data>) ga.TSP(targets);
        Iterator iter=ans.iterator();
        while (iter.hasNext()){
            node n=(node)iter.next();
            System.out.print(n.getKey() + " ---> ");
        }

    }

    @Test
    public void copy() {
        DGraph gra=new DGraph();
        for(int i=1;i<6;i++){
            gra.addNode(new node(i));
        }
        gra.connect(1,2,3);
        gra.connect(1,3,2);
        gra.connect(1,4,7);
        gra.connect(2,4,3);
        gra.connect(3,4,5);
        ga.init(gra);
        DGraph copy= (DGraph) ga.copy();
        assertEquals(copy.toString(),ga.getGr().toString());
        ga.getGr().removeNode(1);
        assertNotEquals(copy.toString(),ga.getGr().toString());
    }

}