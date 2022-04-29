package kr.ac.adv.exam;

import com.fasterxml.jackson.databind.deser.std.UntypedObjectDeserializer;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ClosenessCentrality {

    Graph g;
    Set<Vertex> settledVertex;
    Set<Vertex> unsettledVertex;
    Map<Vertex, Vertex> predecessors;
    Map<Vertex, Integer> distance;
    Vertex vertex;
    ClosenessCentrality(Graph g) {

        this.g = g;

    }


    public void execute(Vertex source) {
        vertex = source;
        settledVertex = new HashSet<>();
        unsettledVertex = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unsettledVertex.add(source);
        while (unsettledVertex.size() > 0) {
            Vertex v = getMinimum(unsettledVertex);
            settledVertex.add(v);
            unsettledVertex.remove(v);
            findMinimalDistances(v);
        }
    }

    public Vertex getMinimum(Set<Vertex> vertices) {
        Vertex minimum = null;
        for (Vertex vertex : vertices) {
            if (minimum == null) {
                minimum = vertex;
            }
            else {
                if(getShortestDistance(vertex) < getShortestDistance(minimum))
                    minimum = vertex;
            }
        }
        return minimum;
    }

    public int getShortestDistance(Vertex dest) {
        Integer d = distance.get(dest);
        if (d == null)
            return Integer.MAX_VALUE;
        else    return d;
    }

    public void findMinimalDistances(Vertex vertex) {
        List<Vertex> adjVertices = getAdjVertices(vertex);
        for (Vertex target : adjVertices) {
            if(getShortestDistance(target) > getShortestDistance(vertex) + getDistance(vertex , target)){
                distance.put(target, getShortestDistance(vertex) + getDistance(vertex, target));
                predecessors.put(target, vertex);
                unsettledVertex.add(target);
            }
        }
    }

    public List<Vertex> getAdjVertices(Vertex vertex) {
        List<Vertex> adjVertices = new ArrayList<>();
        for (Edge edge : g.getEdges()) {
            if (edge.getVertex(Direction.OUT).equals(vertex) && !isSettled(
                edge.getVertex(Direction.IN))) {
                adjVertices.add(edge.getVertex(Direction.IN));
            }
        }
        return adjVertices;
    }

    public Integer getDistance(Vertex source, Vertex target) {
        for (Edge edge : g.getEdges()) {
            if(edge.getVertex(Direction.OUT).equals(source) && edge.getVertex(Direction.IN).equals(target))
                return edge.getProperty("weight");
        }
        throw new RuntimeException();
    }

    public boolean isSettled(Vertex vertex) {
        return settledVertex.contains(vertex);
    }
    public Double calculateCC(){
        /**
         * Export comma-separated values of normalized degree centrality
         * in an ascending order of identifier of vertices.
         * CC = N-1 / sigma(all Reachable Vertices distance)
         */
        long numberOfVertices = g.getVertices().stream().count();
        double sum = 0;
        for (Vertex v : distance.keySet()) {
            sum += distance.get(v);
        }
        if(sum == 0)
            return -1d;
        return (numberOfVertices-1)/sum;

    }
}
