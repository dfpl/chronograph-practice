package org.dfpl.chronograph.crud.memory.JGraph;

import java.io.IOException;

import org.dfpl.chronograph.crud.memory.jgraph.Email;
import org.dfpl.chronograph.crud.memory.jgraph.JGraph;
import org.junit.Before;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;

public class RealWorldTest {
    JGraph graph;
    
    @Before
    public void init() throws IOException {
    	Email email = new Email();

    	
    }
   
    public void test1() {
    	graph.getVertices().forEach(System.out::println);
    	graph.getEdges().forEach(System.out::println);
    }
   
    @Test
    public void test2() {
    	Vertex a = graph.getVertex("0");
    	System.out.println(a.getVertices(Direction.OUT,"").parallelStream().count());
    }
    public void test3() {
    	Vertex a = graph.getVertex("0");
        
    }
    //TODO: outEdges 와 inEdges 를 유지해서 기존의 속도와 비교해보자
}

