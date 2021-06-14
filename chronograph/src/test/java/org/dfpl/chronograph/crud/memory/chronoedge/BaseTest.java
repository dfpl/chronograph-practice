package org.dfpl.chronograph.crud.memory.chronoedge;

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

public class BaseTest {

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

		abLikes = g.addEdge(a, b, "likes");
		abLoves = g.addEdge(a, b, "loves");
		acLikes = g.addEdge(a, c, "likes");
		ccLikes = g.addEdge(c, c, "likes");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testRemove() {
		acLikes.remove();

		assertTrue(g.getVertices().stream().map(v -> v.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A, B, C]"));
		assertTrue(g.getEdges().stream().map(e -> e.getId()).sorted().collect(Collectors.toList()).toString()
				.equals("[A|likes|B, A|loves|B, C|likes|C]"));
	}

	@Test
	public void testGetLabel() {
		assertTrue(acLikes.getLabel().equals("likes"));
	}

	@Test
	public void testGetId() {
		assertTrue(acLikes.getId().equals("A|likes|C"));
	}

	@Test
	public void testAddAndRemoveProperty() {
		// Set properties
		abLikes.setProperty("name", "J. B.");
		abLikes.setProperty("title", "Prof.");

		// Check all property keys
		assertTrue(abLikes.getPropertyKeys().stream().sorted().collect(Collectors.toList()).toString()
				.equals("[name, title]"));

		// Check get property
		assertEquals(abLikes.getProperty("name"), "J. B.");

		// Check removal of property
		abLikes.removeProperty("name");
		assertNull(abLikes.getProperty("name"));
	}

	@Test
	public void testGetVertex() {
		assertTrue(acLikes.getVertex(Direction.OUT).toString().equals("A"));
		assertTrue(acLikes.getVertex(Direction.IN).toString().equals("C"));
	}
}
