package kr.ac.adv.exam;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.tg.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class App {

    static HashMap<String, HashSet<Vertex>> reachableMap;
    static int V , E;

    public static void main(String[] args) throws IOException {
        TinkerGraph graph = Creation.getGraph();
        V = getVertexNumber(graph);
        E = getEdgeNumber(graph);
        System.out.println(V);
        TinkerGraph graph1 = Creation.getTempGraph();
        reachableMap = new HashMap<>();
        Vertex v1 = graph1.getVertex("1");
        Edge e1 = graph1.getEdge("1->2");

        System.out.println(v1);
        //BFS(graph1);

    }


    public static int getVertexNumber(TinkerGraph graph) {
        int count = 0;
        Iterator iterator = graph.getVertices().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static int getEdgeNumber(TinkerGraph graph) {
        int count = 0;
        Iterator iterator = graph.getEdges().iterator();
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        return count;
    }

    public static void getReachableVertices(Graph graph) {

        Iterable<Vertex> vertices = graph.getVertices();

        for (Vertex vertex : vertices) {
            HashSet<Vertex> vertexHashSet = new HashSet<>();

        }
    }

    public static void tmp(Vertex vertex) {

        Iterable<Vertex> vertices = vertex.getVertices(Direction.OUT);
        if (count(vertices) == 1) {
            return;
        }
        vertices.forEach(vertex1 -> {
            System.out.println(vertex1);
            tmp(vertex1);
        });


    }

    public static int count(Iterable<Vertex> vertices) {
        int count = 0;
        for (Vertex v : vertices) {
            count++;
        }
        return count;
    }

    public static void BFS(TinkerGraph graph) {
        Iterable<Vertex> vertices = graph.getVertices();
        int cnt = 0;
        for (Vertex v : vertices) {
            String vId = v.getId().toString();
            Queue<Vertex> queue = new LinkedList<>();
            HashSet<Vertex> visited = new HashSet<>();
            queue.add(v);
            while (!queue.isEmpty()) {
                Vertex vertex = queue.poll();
                Iterator<Vertex> iterator = vertex.getVertices(Direction.OUT).iterator();
                while (iterator.hasNext()) {
                    Vertex adjVertex = iterator.next();
                    if (adjVertex.equals(vertex)) {
                        continue;
                    }
                    if (!visited.contains(adjVertex)) {
                        visited.add(adjVertex);
                        queue.add(adjVertex);
                    }
                }
            }
            reachableMap.put(vId, visited);
            System.out.println(visited);
            System.out.println("vId: " + vId + "\t" + reachableMap.get(vId).size());
        }
    }

    public static void calculateCC(Graph graph) {
        /**
         * Export comma-separated values of normalized degree centrality
         * in an ascending order of identifier of vertices.
         * CC = N-1 / sigma(all Reachable Vertices distance)
         */

        HashMap<String, Float> closenessCentralityMap = new HashMap<>();

        Iterable<Vertex> vertices = graph.getVertices();
        for (Vertex vertex : vertices) {
            if (getSize(vertex.getEdges(Direction.OUT)) == 0
                && getSize(vertex.getEdges(Direction.IN)) == 0) {
                closenessCentralityMap.put(vertex.getId().toString(), 0f);
                continue;
            }

            int distance = 0;

        }
    }

    public static int getDistance(Vertex source, Vertex destination) {

        return 0;
    }

    public static int getSize(Iterable<? extends Element> e) {
        int count = 0;
        for (Element element : e) {
            count++;
        }
        return count;
    }


}


