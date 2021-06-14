package org.dfpl.chronograph.traversal.traversalengine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

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

public class EdgeToVertex {
	Graph graph;
	Vertex a;
	Vertex b;
	Vertex c;
	Edge abLikes;

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
		graph.addEdge(a, c, "likes");
		graph.addEdge(a, b, "loves");
		graph.addEdge(c, c, "loves");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testInV() {
		TraversalEngine engine = new TraversalEngine(graph, abLikes, Edge.class, false);

		assertThat(engine.inV().toList(), containsInAnyOrder("B"));
	}

	@Test
	public void testOutV() {
		TraversalEngine engine = new TraversalEngine(graph, abLikes, Edge.class, false);

		assertThat(engine.outV().toList(), containsInAnyOrder("A"));
	}
}
