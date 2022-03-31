package org.dfpl.chronograph.crud.memory.JGraph;


import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import org.dfpl.chronograph.crud.memory.jgraph.Email;
import org.dfpl.chronograph.crud.memory.jgraph.JGraph;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class JGraphTest {
    JGraph jGraph;
    Vertex a, b, c;
    Edge edge1, edge2, edge3, edge4;

    @Before
    public void init() {
        jGraph = new JGraph();
        a = jGraph.addVertex("A");
        b = jGraph.addVertex("B");
        c = jGraph.addVertex("C");
        edge1 = jGraph.addEdge(a, b, "likes");
        edge2 = jGraph.addEdge(a, b, "loves");
        edge3 = jGraph.addEdge(a, c, "likes");
        edge4 = jGraph.addEdge(c, c, "likes");

    }

    @Test
    public void practice4() {
        Vertex a = jGraph.addVertex("A");
        Vertex b = jGraph.addVertex("B");
        Vertex c = jGraph.addVertex("C");
        Edge edge1 = jGraph.addEdge(a, b, "likes");
        Edge edge2 = jGraph.addEdge(a, b, "loves");
        Edge edge3 = jGraph.addEdge(a, c, "likes");
        Edge edge4 = jGraph.addEdge(c, c, "likes");
        System.out.println(jGraph.getEdges());
        System.out.println("jGraph = " + jGraph.getVertices());
    }

    @Test
    public void practice5() {
        Vertex a = jGraph.addVertex("A");
        Vertex b = jGraph.addVertex("B");
        Vertex c = jGraph.addVertex("C");
        Edge edge1 = jGraph.addEdge(a, b, "likes");
        Edge edge2 = jGraph.addEdge(a, b, "loves");
        Edge edge3 = jGraph.addEdge(a, c, "likes");
        Edge edge4 = jGraph.addEdge(c, c, "likes");
        System.out.println(jGraph.getVertices());

    }

    @Test
    public void practice6() {
        Vertex a = jGraph.addVertex("A");
        Vertex b = jGraph.addVertex("B");
        Vertex c = jGraph.addVertex("C");
        Edge edge1 = jGraph.addEdge(a, b, "likes");
        Edge edge2 = jGraph.addEdge(a, b, "loves");
        Edge edge3 = jGraph.addEdge(a, c, "likes");
        Edge edge4 = jGraph.addEdge(c, c, "likes");
        System.out.println(jGraph.getEdges());

    }

    @Test
    public void practice7() {
        Vertex a = jGraph.addVertex("A");
        Vertex b = jGraph.addVertex("B");
        Vertex c = jGraph.addVertex("C");
        Edge edge1 = jGraph.addEdge(a, b, "likes");
        Edge edge2 = jGraph.addEdge(a, b, "loves");
        Edge edge3 = jGraph.addEdge(a, c, "likes");
        Edge edge4 = jGraph.addEdge(c, c, "likes");
        jGraph.removeVertex(a);
        System.out.println("jGraph.getVertices() = " + jGraph.getVertices());
        System.out.println("jGraph.getEdges() = " + jGraph.getEdges());
    }

    @Test
    public void practice8() {
        Vertex a = jGraph.addVertex("A");
        Vertex b = jGraph.addVertex("B");
        Vertex c = jGraph.addVertex("C");
        Edge edge1 = jGraph.addEdge(a, b, "likes");
        Edge edge2 = jGraph.addEdge(a, b, "loves");
        Edge edge3 = jGraph.addEdge(a, c, "likes");
        Edge edge4 = jGraph.addEdge(c, c, "likes");
        jGraph.removeEdge(edge3);
        System.out.println("jGraph.getVertices() = " + jGraph.getVertices());
        System.out.println("jGraph.getEdges() = " + jGraph.getEdges());
    }

    @Test
    public void practice9() {

        System.out.println(edge3.getVertex(Direction.OUT));
        System.out.println(edge3.getVertex(Direction.IN));
    }

    @Test
    public void practice10() {
        System.out.println("edge4.getLabel() = " + edge4.getLabel());
    }
    @Test
    public void practice11(){
        edge3.remove();
        System.out.println("jGraph = " + jGraph.getVertices());
        System.out.println("jGraph = " + jGraph.getEdges());
    }
    @Test
    public void practice12(){
        System.out.println("jGraph = " + jGraph.getVertices());
        System.out.println("jGraph = " + jGraph.getEdges());

        Collection<Edge> likes = a.getEdges(Direction.OUT, "likes");


    }
}
