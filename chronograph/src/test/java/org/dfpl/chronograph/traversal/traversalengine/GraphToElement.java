package org.dfpl.chronograph.traversal.traversalengine;

import static org.junit.Assert.assertEquals;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class GraphToElement {
	
	Graph graph;
	Vertex a;
	Vertex b;
	Vertex c;
	Edge abLikes;
	Edge acLikes;
	Edge abLoves;
	Edge ccLoves;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		graph = new ChronoGraph();

		a = graph.addVertex("A");
		b = graph.addVertex("B");
		c = graph.addVertex("C");

		abLikes = graph.addEdge(a, b, "likes");
		acLikes = graph.addEdge(a, c, "likes");
		abLoves = graph.addEdge(a, b, "loves");
		ccLoves = graph.addEdge(c, c, "loves");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	@Test
	public void testV() {
		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(3, engine.V().toList().size());
	}

	@Test
	public void testV_NoVertex() {
		Graph emptyGraph = new ChronoGraph();
		TraversalEngine engine = new TraversalEngine(emptyGraph, emptyGraph, Graph.class, false);

		assertEquals(0, engine.V().toList().size());
	}

	@Test
	public void testV_WithProperties() {
		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(1, engine.V("isOdd", false).toList().size());
	}

	@Test
	public void testV_WithoutProperties() {
		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(0, engine.V("isOdd", 1).toList().size());
	}

	@Test
	public void testE() {
		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(engine.E().toList().size(), 4);
	}
	
	@Test
	public void testE_WithoutEdges() {
		graph = new ChronoGraph();
		
		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(0, engine.E().toList().size());
	}

	@Test
	public void testE_WithProperties() {
		abLikes.setProperty("isOdd", true);
		abLoves.setProperty("isOdd", true);
		acLikes.setProperty("isOdd", false);
		ccLoves.setProperty("weight", 2);

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(2, engine.E("isOdd", true).toList().size());
	}
	
	@Test
	public void testE_WithoutProperties() {
		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, false);

		assertEquals(0, engine.E("isOdd", true).toList().size());
	}
}
