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
public class Test2 {

	@SuppressWarnings("unused")
	@Test
	public void addVertex() {
		Graph g = new ChronoGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");

		// -----------------------------------------------------//
		assertTrue(g.getVertices().size() == 3);
		assertTrue(g.getVertex("A").getId().equals("A"));
		assertTrue(g.getVertex("B").getId().equals("B"));
		assertTrue(g.getVertex("C").getId().equals("C"));
	}
}
