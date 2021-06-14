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
public class EdgeTest {
	Graph g;
	Vertex a;
	Vertex b;
	Vertex c;

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
	public void testAddEdge() {
		g.addEdge(a, b, "likes");
		g.addEdge(a, c, "likes");

		// -----------------------------------------------------//
		assertTrue(g.getEdges().size() == 2);
		assertTrue(g.getEdge("A|likes|B").getId().equals("A|likes|B"));
		assertTrue(g.getEdge("A|likes|C").getId().equals("A|likes|C"));
		
		g.addEdge(a, b, "loves");
		g.addEdge(c, c, "likes");
		
		// -----------------------------------------------------//
		assertTrue(g.getEdges().size() == 4);
		assertTrue(g.getEdge("A|likes|B") != null);
		assertTrue(g.getEdge("A|likes|C") != null);
	}
	
	@Test
	public void testGetEdges() {
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|likes|C, A|loves|B, C|likes|C]"));
	}
	
	@Test
	public void testGetEdges_WithDirectionIn() {
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		assertTrue(b.getEdges(Direction.IN, "likes").stream().map(e -> e.getId()).sorted().collect(Collectors.toList())
				.toString().equals("[A|likes|B]"));
	}
	
	@Test
	public void testGetEdges_WithDirectionOut() {
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		assertTrue(a.getEdges(Direction.OUT, "likes").stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|likes|C]"));
		assertTrue(a.getEdges(Direction.OUT, "likes", "loves").stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|likes|C, A|loves|B]"));
		
		assertTrue(a.getVertices(Direction.OUT, "likes", "loves").stream().map(e -> e.getId()).sorted().collect(Collectors.toList())
				.toString().equals("[B, C]"));
	}
	
	
	@Test
	public void testGetLabel() {
		Edge ac = g.addEdge(a, c, "likes");

		assertTrue(ac.getLabel().equals("likes"));
	}
	
	
	@Test
	public void testRemove() {
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		Edge ac = g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		ac.remove();

		assertTrue(g.getVertices().stream().map(v -> v.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A, B, C]"));
		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|loves|B, C|likes|C]"));
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
	
	@Test
	public void testProperty_GetAndSet() {
		Edge ab = g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		ab.setProperty("name", "J. B.");
		ab.setProperty("title", "Prof.");

		assertTrue(
				ab.getPropertyKeys().stream().sorted().collect(Collectors.toList()).toString().equals("[name, title]"));

	}
}
