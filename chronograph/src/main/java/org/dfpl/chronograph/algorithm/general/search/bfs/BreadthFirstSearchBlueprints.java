package org.dfpl.chronograph.algorithm.general.search.bfs;

import java.util.ArrayList;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * Hello world!
 *
 */
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

	public Graph createExample() {
		Graph g = new ChronoGraph();
		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");
		Vertex v4 = g.addVertex("4");
		Vertex v5 = g.addVertex("5");
		Vertex v6 = g.addVertex("6");
		Vertex v7 = g.addVertex("7");
		Vertex v8 = g.addVertex("8");

		g.addEdge(v1, v2, "c");
		g.addEdge(v1, v3, "c");
		g.addEdge(v1, v4, "c");
		g.addEdge(v2, v3, "c");
		g.addEdge(v2, v5, "c");
		g.addEdge(v3, v4, "c");
		g.addEdge(v3, v5, "c");
		g.addEdge(v3, v6, "c");
		g.addEdge(v4, v6, "c");
		g.addEdge(v7, v8, "c");

		return g;
	}

	public static void main(String[] args) {
		BreadthFirstSearchBlueprints bfs = new BreadthFirstSearchBlueprints();
		Graph g = bfs.createExample();
		ArrayList<ArrayList<ArrayList<Vertex>>> result = bfs.BFS(g, Direction.OUT, "c");
		System.out.println(result);
	}
}
