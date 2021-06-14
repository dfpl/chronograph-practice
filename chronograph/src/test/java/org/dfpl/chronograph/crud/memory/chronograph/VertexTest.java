package org.dfpl.chronograph.crud.memory.chronograph;

import static org.junit.Assert.*;

import java.util.stream.Collectors;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class VertexTest {

	Graph g;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		g = new ChronoGraph();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testAddVertex() {
		g.addVertex("A");

		// -----------------------------------------------------//
		assertEquals(g.getVertices().size(), 1);
		assertEquals(g.getVertex("A").getId(), "A");
		
		g.addVertex("B");
		g.addVertex("C");
		
		assertTrue(g.getVertices().size() == 3);
		assertTrue(g.getVertex("A").getId().equals("A"));
		assertTrue(g.getVertex("B").getId().equals("B"));
		assertTrue(g.getVertex("C").getId().equals("C"));
	}
	
	@Test
	public void testGetVertices() {
		g.addVertex("A");
		g.addVertex("B");
		g.addVertex("C");

		// -----------------------------------------------------//
		assertTrue(g.getVertices().stream().map(v -> v.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A, B, C]"));
	}
	
	@Test
	public void testRemoveVertex() {
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		g.removeVertex(a);

		assertTrue(g.getVertices().stream().map(v -> v.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[B, C]"));

		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[C|likes|C]"));
	}
	
	@Test
	public void testGetVertex() {
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		Edge ac = g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		assertTrue(ac.getVertex(Direction.OUT).toString().equals("A"));
		assertTrue(ac.getVertex(Direction.IN).toString().equals("C"));
	}
	
	@Test
	public void testGetId() {
		Vertex a = g.addVertex("A");

		assertTrue(a.getId().equals("A"));
	}
	
	@Test
	public void testRemove() {
		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		a.remove();

		assertTrue(g.getVertices().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[B, C]"));

		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[C|likes|C]"));
	}
	
	@Test
	public void testProperty_GetAndSet() {
		Vertex a = g.addVertex("A");

		a.setProperty("name", "J. B.");
		a.setProperty("title", "Prof.");
		
		assertTrue(a.getProperty("name").toString().equals("J. B."));
		assertTrue(a.getProperty("title").toString().equals("Prof."));
	}

}
