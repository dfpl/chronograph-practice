package org.dfpl.chronograph.impl.jgraph.practice;

import org.dfpl.chronograph.impl.jgraph.JGraph;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice10 {

	public static void main(String[] args) {
		Graph g = new JGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		Edge ac = g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		System.out.println(ac.getLabel());

	}
}
