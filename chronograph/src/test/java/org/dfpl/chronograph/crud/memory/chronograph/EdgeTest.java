package org.dfpl.chronograph.crud.memory.chronograph;

import static org.junit.Assert.*;

import java.util.stream.Collectors;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class EdgeTest {
	Graph g;
	Vertex a;
	Vertex b;
	Vertex c;
	Edge abLikes;
	Edge abLoves;
	Edge acLikes;
	Edge ccLikes;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		g = new ChronoGraph();
		a = g.addVertex("A");
		b = g.addVertex("B");
		c = g.addVertex("C");

	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateEdges() {
		assertEquals(g.getEdges().size(), 0);

		abLikes = g.addEdge(a, b, "likes");
		abLoves = g.addEdge(a, b, "loves");
		acLikes = g.addEdge(a, c, "likes");
		ccLikes = g.addEdge(c, c, "likes");

		assertTrue(g.getEdges().size() == 4);

		assertTrue(g.getEdge("A|likes|B") != null);
		assertTrue(g.getEdge("A|loves|B") != null);
		assertTrue(g.getEdge("A|likes|C") != null);
		assertTrue(g.getEdge("C|likes|C") != null);

		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|likes|C, A|loves|B, C|likes|C]"));
	}

	@Test
	public void testRemoveEdge() {
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		Edge ac = g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		g.removeEdge(ac);

		assertTrue(g.getVertices().stream().map(v -> v.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A, B, C]"));
		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|loves|B, C|likes|C]"));
	}
}
