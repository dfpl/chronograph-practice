package org.dfpl.chronograph.crud.memory.test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import org.dfpl.chronograph.crud.memory.jgraph.JGraph;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class main {
    public static void main(String[] args) throws IOException {
        Graph jGraph = new JGraph();
        Vertex vertex1 = jGraph.addVertex("1");
        Vertex vertex2 = jGraph.addVertex("2");
        Edge edge1 = jGraph.addEdge(jGraph.getVertex("1"), jGraph.getVertex("2"), "");
        Edge edge2 = jGraph.addEdge(jGraph.getVertex("2"), jGraph.getVertex("1"), "");
        HashSet<Edge> edges = (HashSet<Edge>) vertex1.getEdges(Direction.IN,"");

        System.out.println(edges);



    }
}

