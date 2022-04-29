package kr.ac.adv.exam;

import static kr.ac.adv.exam.getNumber.getEdgeNumber;
import static kr.ac.adv.exam.getNumber.getVertexNumber;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Element;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import org.dfpl.chronograph.crud.memory.ChronoGraph;

public class App {

    static HashMap<String, HashSet<Vertex>> reachableMap;

    public static void main(String[] args) throws IOException {
        ChronoGraph graph = Creation.getGraph();
        ChronoGraph graph1 = Creation.getTempGraph();
//        //prob 1-1,1-2
//        System.out.println(getNumber.getVertexNumber(graph));
//        System.out.println(getNumber.getEdgeNumber(graph));
//        //prob 1-3
//        ClosenessCentrality closenessCentrality = new ClosenessCentrality(graph);
//        for(Vertex vertex : graph.getVertices()){
//            closenessCentrality.execute(vertex);
//            System.out.println(closenessCentrality.distance);
//        }
//        //prob 1-4
//        for (Vertex vertex : graph.getVertices()) {
//            closenessCentrality.execute(vertex);
//            System.out.println(vertex.getId() + "," + closenessCentrality.calculateCC());
//        }
        //prob1-5
        LocalClusteringCoefficient localClusteringCoefficient = new LocalClusteringCoefficient(
            graph1);
        System.out.println(localClusteringCoefficient.getNumberOfTriangles());

//        System.out.println(graph1.getEdges());
//        ClosenessCentrality closenessCentrality = new ClosenessCentrality(graph1);
//        closenessCentrality.execute(graph1.getVertex("1"));
//        System.out.println(closenessCentrality.distance);
//        System.out.println(closenessCentrality.calculateCC());
    }


    public static int count(Iterable<Vertex> vertices) {
        int count = 0;
        for (Vertex v : vertices) {
            count++;
        }
        return count;
    }

    public static void BFS(Graph graph) {
////        Iterable<Vertex> vertices = graph.getVertices();
////        for (Vertex v : vertices) {
////            String vId = v.getId();
////            Queue<Vertex> queue = new LinkedList<>();
////            HashSet<Vertex> visited = new HashSet<>();
////            queue.add(v);
////            while (!queue.isEmpty()) {
////                Vertex vertex = queue.poll();
////                Iterator<Vertex> iterator = vertex.getVertices(Direction.OUT,"").iterator();
////                while (iterator.hasNext()) {
////                    Vertex adjVertex = iterator.next();
////                    if (adjVertex.equals(vertex)) {
////                        continue;
////                    }
////                    if (!visited.contains(adjVertex)) {
////                        visited.add(adjVertex);
////                        queue.add(adjVertex);
////                    }
////                }
////            }
////            reachableMap.put(vId, visited);
        ArrayList<ArrayList<Vertex>> adj = new ArrayList<>();
        //init

        Collection<Vertex> vertices = graph.getVertices();

        for (Vertex vertex : vertices) {
            Queue<Vertex> queue = new LinkedList<>();
            queue.add(vertex);
            vertex.setProperty("isVisited", "true");
            while (!queue.isEmpty()) {
                Vertex v = queue.poll();
                Collection<Vertex> adjVertices = v.getVertices(Direction.OUT, "");

            }
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


