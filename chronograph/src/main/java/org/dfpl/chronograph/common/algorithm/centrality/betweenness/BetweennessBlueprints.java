package org.dfpl.chronograph.common.algorithm.centrality.betweenness;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class BetweennessBlueprints {

	public static void main(String[] args) {
		BetweennessBlueprints bb = new BetweennessBlueprints();
		Graph g = bb.createStaticGraph();
		bb.betweennessCentrality(g);
		bb.printCentrality(g);

	}

	@SuppressWarnings("unchecked")
	public void betweennessCentrality(Graph g) {
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
				for (Vertex w : v.getVertices(Direction.OUT, "link")) {
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

	public Graph createStaticGraph() {
		Graph g = new ChronoGraph();

		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		Vertex d = g.addVertex("D");
		Vertex e = g.addVertex("E");

		g.addEdge(a, c, "link");
		g.addEdge(c, a, "link");

		g.addEdge(b, c, "link");
		g.addEdge(c, b, "link");

		g.addEdge(c, d, "link");
		g.addEdge(d, c, "link");

		g.addEdge(d, e, "link");
		g.addEdge(e, d, "link");

		return g;
	}

	public void printCentrality(Graph g) {
		System.out.println("Centrality");
		for (Vertex c : g.getVertices()) {
			System.out.println(c + " " + c.getProperty("centrality").toString());
		}
	}

}
