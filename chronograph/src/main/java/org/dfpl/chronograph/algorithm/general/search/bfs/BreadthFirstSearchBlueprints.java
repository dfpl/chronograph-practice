package org.dfpl.chronograph.algorithm.general.search.bfs;

import java.util.ArrayList;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class BreadthFirstSearchBlueprints {

    public ArrayList<ArrayList<Vertex>> BFS(Graph g, Vertex v, Direction d, String label) {
        ArrayList<ArrayList<Vertex>> wcc = new ArrayList<ArrayList<Vertex>>();
        ArrayList<Vertex> queue0 = new ArrayList<Vertex>();
        queue0.add(v);
        wcc.add(queue0);
        v.setProperty("tag", "VISITED");
        int depth = 0;
        while (wcc.get(depth) != null && wcc.get(depth).isEmpty() == false) {
            ArrayList<Vertex> queueCurrent = wcc.get(depth);
            ArrayList<Vertex> queueNext = new ArrayList<Vertex>();
            wcc.add(queueNext);
            for (Vertex queueElement : queueCurrent) {
                for (Edge incidentEdge : queueElement.getEdges(d, label)) {
                    if (incidentEdge.getProperty("tag").toString().equals("UNEXPLORED")) {
                        Vertex w = incidentEdge.getVertex(d.opposite());
                        if (w.getProperty("tag").toString().equals("UNEXPLORED")) {
                            incidentEdge.setProperty("tag", "DISCOVERY");
                            w.setProperty("tag", "VISITED");
                            queueNext.add(w);
                        } else {
                            incidentEdge.setProperty("tag", "CROSS");
                        }
                    }
                }
            }
            depth = depth + 1;
        }
        return wcc;
    }

    public ArrayList<ArrayList<ArrayList<Vertex>>> BFS(Graph g, Direction d, String label) {
        ArrayList<ArrayList<ArrayList<Vertex>>> result = new ArrayList<ArrayList<ArrayList<Vertex>>>();
        for (Vertex v : g.getVertices()) {
            v.setProperty("tag", "UNEXPLORED");
        }
        for (Edge e : g.getEdges()) {
            e.setProperty("tag", "UNEXPLORED");
        }

        for (Vertex v : g.getVertices()) {
            if (v.getProperty("tag").toString().equals("UNEXPLORED")) {
                result.add(BFS(g, v, d, label));
            }
        }
        return result;
    }
}
