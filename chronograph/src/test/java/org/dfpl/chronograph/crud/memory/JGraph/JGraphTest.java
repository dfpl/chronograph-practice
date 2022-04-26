package org.dfpl.chronograph.crud.memory.JGraph;


import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import org.dfpl.chronograph.crud.memory.jgraph.Email;
import org.dfpl.chronograph.crud.memory.jgraph.JGraph;
import org.junit.Assert;
import org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

public class JGraphTest {
    JGraph jGraph;
    Graph graph;
    Vertex a, b, c;
    Edge edge1, edge2, edge3, edge4;
    Email email , email1;
    long beforeTime, afterTime;
    @Before
    public void init() throws IOException {
        email = new Email();
        email1 = new Email();
        jGraph = (JGraph)email.getG();
        graph = email1.getG();
//        a = jGraph.addVertex("A");
//        b = jGraph.addVertex("B");
//        c = jGraph.addVertex("C");
//        edge1 = jGraph.addEdge(a, b, "likes");
//        edge2 = jGraph.addEdge(a, b, "loves");
//        edge3 = jGraph.addEdge(a, c, "likes");
//        edge4 = jGraph.addEdge(c, c, "likes");

    }
    @Test
    public void practiceinit(){
        System.out.println(jGraph.equals(graph));
    }
    public void practice4() {
   
        System.out.println(jGraph.getEdges());
        System.out.println("jGraph = " + jGraph.getVertices());
    }

    public void practice5() {
       
        System.out.println(jGraph.getVertices());

    }

    public void practice6() {
        
        System.out.println(jGraph.getEdges());

    }

    public void practice7() {
        
        jGraph.removeVertex(a);
        System.out.println("jGraph.getVertices() = " + jGraph.getVertices());
        System.out.println("jGraph.getEdges() = " + jGraph.getEdges());
    }

    public void practice8() {
        jGraph.removeEdge(edge3);
        System.out.println("jGraph.getVertices() = " + jGraph.getVertices());
        System.out.println("jGraph.getEdges() = " + jGraph.getEdges());
    }

    public void practice9() {
        System.out.println(edge3.getVertex(Direction.OUT));
        System.out.println(edge3.getVertex(Direction.IN));
    }

    public void practice10() {
        System.out.println("edge4.getLabel() = " + edge4.getLabel());
    }
    public void practice11(){
        edge3.remove();
        System.out.println("jGraph = " + jGraph.getVertices());
        System.out.println("jGraph = " + jGraph.getEdges());
    }
    
    
    public void practice12(){
        System.out.println("jGraph = " + jGraph.getVertices());
        System.out.println("jGraph = " + jGraph.getEdges());

        Collection<Edge> likes = b.getEdges(Direction.IN, "likes");
        likes.forEach(System.out::println);

    }
    @Test
    public void practice13(){
        System.out.println("jGraph = " + jGraph.getVertices());
        System.out.println("jGraph = " + jGraph.getEdges());
        a.getVertices(Direction.OUT, "loves").forEach(System.out::println);
    }
    @Test
    public void practiceTime(){
        long beforeTime = System.currentTimeMillis();
        graph.getVertices().forEach(sender->{
//            System.out.print(sender + " -> ");
            sender.getVertices(Direction.OUT).forEach(middle ->{
//                System.out.print(middle + " -> {");
                middle.getVertices(Direction.OUT).forEach(reciever->{
//                    System.out.println(reciever + " ");
                });
//                System.out.print(" }");
            });
        });
        long afterTime = System.currentTimeMillis();
        System.out.println("Graph test time : " + Long.toString(afterTime - beforeTime));
        beforeTime = System.currentTimeMillis();
        jGraph.getVertices().forEach(sender->{
//            System.out.print(sender + " -> ");
            sender.getVertices(Direction.OUT).forEach(middle ->{
//                System.out.print(middle + " -> {");
                middle.getVertices(Direction.OUT).forEach(reciever->{
//                    System.out.println(reciever + " ");
                });
//                System.out.print(" }");
            });
        });
        afterTime = System.currentTimeMillis();
        System.out.println("JGraph test time : " + Long.toString(afterTime - beforeTime));
        //Graph test time : 13402
        //JGraph test time : 12432
    }
    @Test
    public void sameTest(){
        HashSet<Vertex> outVertices = new HashSet<>() ;
        HashSet<Vertex> outVertices1 = new HashSet<>();
        beforeTime = System.currentTimeMillis();
        graph.getVertices().stream().forEach(v->{
            v.getVertices(Direction.OUT).forEach(middle->{
                outVertices.add(middle);
            });}
        );
        afterTime = System.currentTimeMillis();
        System.out.println(afterTime - beforeTime);
        beforeTime = System.currentTimeMillis();
        jGraph.getVertices().stream().forEach(v->{
            v.getVertices(Direction.OUT).forEach(middle->{
                outVertices1.add(middle);
            });}
        );
        afterTime = System.currentTimeMillis();
        System.out.println(afterTime - beforeTime);
        System.out.println(outVertices.equals(outVertices1) );
    }

}
