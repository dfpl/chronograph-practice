package org.dfpl.chronograph.impl.jgraph.practice;

import org.dfpl.chronograph.impl.jgraph.JGraph;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice20 {

	public static void main(String[] args) {
		Graph g = new JGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		a.setProperty("name", "J. B.");
		a.setProperty("title", "Prof.");

		for (String k : a.getPropertyKeys()) {
			System.out.println(k);
		}
	}
}
