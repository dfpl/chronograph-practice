package org.dfpl.chronograph.crud.memory.practice;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice8 {

	public static void main(String[] args) {
		Graph g = new ChronoGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		Edge ac = g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		g.removeEdge(ac);

		for (Vertex v : g.getVertices()) {
			System.out.println(v);
		}
		for (Edge e : g.getEdges()) {
			System.out.println(e);
		}
	}
}
