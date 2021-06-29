package org.dfpl.chronograph.algorithm.temporal.search.bfs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.TreeSet;

import com.tinkerpop.blueprints.*;
import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoEdgeEvent;

public class TemporalBreadthFirstSearch {

    private final Map<Vertex, Vertex> predecessors;
    private final Map<Vertex, Integer> distances;
    private final Map<Vertex, Time> gamma;

    public TemporalBreadthFirstSearch() {
        predecessors = new HashMap<>();
        distances = new HashMap<>();
        gamma = new HashMap<>();
    }

    public void compute(Vertex source, Time time, String... labels) {
        predecessors.clear();
        distances.clear();
        gamma.clear();

        Queue<Vertex> Q = new LinkedList<>();

        distances.put(source, 0);
        gamma.put(source, time);
        Q.add(source);

        while (!Q.isEmpty()) {
            Vertex u = Q.poll();
            time = gamma.get(u);

            // Get all edges with the least time in ascending order
            NavigableSet<ChronoEdgeEvent> events = new TreeSet<>(Event::compareTo);
            for (Edge e : u.getEdges(Direction.OUT, labels)) {
                NavigableSet<ChronoEdgeEvent> minVisitEvents = e.getEvents(time, TemporalRelation.isAfter, TemporalRelation.cotemporal);
                if (!minVisitEvents.isEmpty())
                    events.addAll(minVisitEvents);
            }

            for (ChronoEdgeEvent event : events) {
                Vertex v = ((Edge) event.getElement()).getVertex(Direction.IN);

                if (gamma.get(v) == null || event.getTime().compareTo(gamma.get(v)) < 0) {
                    distances.put(v, distances.get(u) + 1);
                    gamma.put(v, event.getTime());
                    predecessors.put(v, u);

                    if (!Q.contains(v)) {
                        Q.add(v);
                    }
                }

            }
        }
    }

    public void printInfo(Graph g) {
        for (Vertex v : g.getVertices()) {
            System.out.println(
                v + " dist: " + distances.get(v) + " pred: " + predecessors.get(v) + " time: " + gamma.get(v));
        }
    }
}
