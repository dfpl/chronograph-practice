package org.dfpl.chronograph.algorithm.general.centrality.betweenness;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class BetweennessCentralityBlueprints {

    @SuppressWarnings("unchecked")
    public void compute(Graph g, String... labels) {
        for (Vertex v : g.getVertices()) {
            v.setProperty("centrality", 0);
        }

        for (Vertex s : g.getVertices()) {

            // Initialization
            Stack<Vertex> S = new Stack<>();

            for (Vertex t : g.getVertices()) {
                t.setProperty("predecessors", new ArrayList<Vertex>());
                t.setProperty("sigma", s.equals(t) ? 1 : 0);
                t.setProperty("d", s.equals(t) ? 0 : -1);
            }
            Queue<Vertex> Q = new LinkedList<>();
            Q.add(s);

            while (Q.size() > 0) {
                Vertex v = Q.remove();
                S.push(v);
                for (Vertex w : v.getVertices(Direction.OUT, labels)) {
                    if ((int) w.getProperty("d") < 0) {
                        Q.add(w);
                        w.setProperty("d", (int) v.getProperty("d") + 1);
                    }
                    if ((int) w.getProperty("d") == (int) v.getProperty("d") + 1) {
                        w.setProperty("sigma", (int) w.getProperty("sigma") + (int) v.getProperty("sigma"));
                        ((List<Vertex>) w.getProperty("predecessors")).add(v);
                    }
                }
            }

            for (Vertex v : g.getVertices()) {
                v.setProperty("delta", 0);
            }

            while (S.size() > 0) {
                Vertex w = S.pop();
                for (Vertex v : (List<Vertex>) w.getProperty("predecessors")) {
                    int updatedDelta = (int) v.getProperty("delta")
                        + ((int) v.getProperty("sigma") / (int) w.getProperty("sigma"))
                        * (1 + (int) w.getProperty("delta"));
                    v.setProperty("delta", updatedDelta);
                }
                if (!w.equals(s)) {
                    int updatedCentrality = (int) w.getProperty("centrality") + (int) w.getProperty("delta");
                    w.setProperty("centrality", updatedCentrality);
                }
            }
        }

    }

    public void printCentrality(Graph g) {
        System.out.println("Centrality");
        for (Vertex c : g.getVertices()) {
            System.out.println(c + " " + c.getProperty("centrality").toString());
        }
    }
}
