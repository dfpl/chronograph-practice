package org.dfpl.chronograph.crud.memory;

import static org.junit.Assert.assertTrue;

import java.util.stream.Collectors;

import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Test15 {

	@Test
	public void incidentEdges() {
		Graph g = new ChronoGraph();
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		assertTrue(c.getEdges(Direction.OUT, "likes").stream().map(e -> e.getId()).sorted().collect(Collectors.toList())
				.toString().equals("[C|likes|C]"));

		assertTrue(c.getEdges(Direction.IN, "likes").stream().map(e -> e.getId()).sorted().collect(Collectors.toList())
				.toString().equals("[A|likes|C, C|likes|C]"));
	}
}
