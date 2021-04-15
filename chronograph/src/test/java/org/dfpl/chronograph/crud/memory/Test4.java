package org.dfpl.chronograph.crud.memory;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Test4 {

	@Test
	public void addEdge() {
		Graph g = new ChronoGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		// -----------------------------------------------------//
		assertTrue(g.getEdges().size() == 4);
		assertTrue(g.getEdge("A|likes|B") != null);
		assertTrue(g.getEdge("A|likes|C") != null);
	}
}
