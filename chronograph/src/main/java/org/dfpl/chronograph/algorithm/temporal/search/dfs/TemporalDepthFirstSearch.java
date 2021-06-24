package org.dfpl.chronograph.algorithm.temporal.search.dfs;

import com.tinkerpop.blueprints.*;
import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoEdgeEvent;

import java.util.HashMap;
import java.util.NavigableSet;
import java.util.TreeSet;

public class DepthFirstSearchTemporal {

    HashMap<Vertex, Time> gamma;
    HashMap<Vertex, Vertex> predecessors;

    public void compute(Graph g, Vertex source, Time time, String... labels) {
        for (Edge edge : g.getEdges()) {
            edge.setOrderByStart(false);
        }

        gamma = new HashMap<>();
        predecessors = new HashMap<>();

        gamma.put(source, time);

        Vertex u = source;
        while (true) {
            NavigableSet<ChronoEdgeEvent> validEvents = new TreeSet<>((ChronoEdgeEvent e1, ChronoEdgeEvent e2) -> e2.compareTo(e1));

            for (Edge edge : u.getEdges(Direction.OUT, labels)) {
                if (edge.getProperty("traversed") != null)
                    continue;

                Event event = edge.getEvent(gamma.get(u), TemporalRelation.isAfter);
                if (event != null)
                    validEvents.add((ChronoEdgeEvent) event);
                event = edge.getEvent(gamma.get(u), TemporalRelation.cotemporal);
                if (event != null)
                    validEvents.add((ChronoEdgeEvent) event);
            }

            if (!validEvents.isEmpty()) {
                ChronoEdgeEvent traverseEvent = validEvents.first();

                Edge e = ((Edge) traverseEvent.getElement());
                e.setProperty("traversed", true);

                Vertex v = ((Edge) traverseEvent.getElement()).getVertex(Direction.IN);
                if (gamma.get(v) == null || gamma.get(v).compareTo(traverseEvent.getTime()) > 0) {
                    predecessors.put(v, u);
                    gamma.put(v, traverseEvent.getTime());
                    u = v;
                }
            } else {
                u = predecessors.get(u);
                if (u == null)
                    break;
            }
        }
    }

    public void printInfo(Graph g) {
        for (Vertex v : g.getVertices()) {
            System.out.println(v + " Time: " + gamma.get(v) + " Pred: " + predecessors.get(v));
        }
    }
}
