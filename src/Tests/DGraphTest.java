package Tests;

import dataStructure.*;

import java.util.Collection;

import static org.junit.Assert.*;

public class DGraphTest {
    DGraph d =new DGraph();
    @org.junit.Test
    public void getNode() {
        d.addNode(new node(1));
        node n= (node) d.getNode(1);
        assertEquals(n.getKey(),1);
    }

    @org.junit.Test
    public void getEdge() {
        d.addNode(new node(1));
        d.addNode(new node(2));
        d.connect(1,2,777);
        d.connect(2,1,0);
        edge e= (edge) d.getEdge(1,2);
        double w=777;
        assertEquals(e.getWeight(),w,w);
    }

    @org.junit.Test
    public void addNode() {
        for(int i=1;i<3;i++) {
            d.addNode(new node(i));
        }
        assertEquals(d.nodeSize(),2);
        d.addNode(new node(3));
        assertEquals(d.nodeSize(),3);
    }

    @org.junit.Test
    public void connect() {
        d.addNode(new node(1));
        d.addNode(new node(2));
        assertEquals(d.edgeSize(),0);
        d.connect(1,2,5);
        double w=5;
        assertEquals(d.edgeSize(),1);
        assertEquals(d.getEdge(1,2).getWeight(),w,w);

    }

    @org.junit.Test
    public void getV() {
        for (int i = 1; i < 4; i++) {
            d.addNode(new node(i));
        }
        Collection<node_data> nodes=d.getV();
        assertEquals(nodes.size(),3);
    }

    @org.junit.Test
    public void getE() {
        for (int i = 1; i < 4; i++) {
            d.addNode(new node(i));
        }
        d.connect(1,2,3);
        d.connect(1,3,4);
        Collection<edge_data> edges=d.getE(1);
        assertEquals(edges.size(),2);
    }


    @org.junit.Test
    public void nodeSize() {
        for (int i = 1; i < 4; i++) {
            d.addNode(new node(i));
        }
        assertEquals(3, d.nodeSize());
        d.removeNode(1);
        assertEquals(2, d.nodeSize());
        d.addNode(new node(4));
        d.addNode(new node(5));
        d.addNode(new node(2)); //already used key
        assertEquals(4, d.nodeSize());

    }

    @org.junit.Test
    public void edgeSize() {
        d.addNode(new node(1));
        d.addNode(new node(2));
        d.addNode(new node(3));
        d.connect(1,2,0);
        d.connect(2,3,0);
        assertEquals(2, d.edgeSize());
        d.removeNode(2);
        assertEquals(0, d.edgeSize());
        d.addNode(new node(2));
        d.connect(1,2,0);
        d.connect(2,3,0);
        d.removeEdge(2,3);
        assertEquals(1, d.edgeSize());
    }

    @org.junit.Test
    public void getMC() {
        for(int i=1;i<10;i++){
            d.addNode(new node(i));
        }
        assertEquals(d.getMC(),9);
        d.connect(1,2,2);
        assertEquals(d.getMC(),10);
        d.removeEdge(1,2);
        assertEquals(d.getMC(),11);
        d.connect(1,2,5);
        d.removeNode(1);
        assertEquals(d.getMC(),13);
    }
}