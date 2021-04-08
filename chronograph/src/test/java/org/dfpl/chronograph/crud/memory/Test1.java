package org.dfpl.chronograph.crud.memory;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.tinkerpop.blueprints.Graph;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Test1 {

	@Test
	public void addVertex() {
		Graph g = new ChronoGraph();
		g.addVertex("jack");

		// -----------------------------------------------------//
		assertTrue(g.getVertices().size() == 1);
		assertTrue(g.getVertex("jack").getId().equals("jack"));
	}
}
