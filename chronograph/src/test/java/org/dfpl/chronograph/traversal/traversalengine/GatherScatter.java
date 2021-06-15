package org.dfpl.chronograph.traversal.traversalengine;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;
import static org.hamcrest.Matchers.*;

public class GatherScatter {
	Graph graph;
	Vertex a;
	Vertex b;
	Vertex c;
	Edge abLikes;
	Edge acLikes;
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
		ccLoves = graph.addEdge(c, c, "loves");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGather() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		List<List<Vertex>> vertices = engine.gather().toList();

		assertEquals(1, vertices.size());
		assertThat(vertices.get(0), contains(a, b, c));
	}

	@Test
	public void testScatter() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		assertThat(engine.scatter().toList(), containsInAnyOrder("A", "B", "C"));
	}

	@Test
	public void testTransform_WithUnboxing() {
		TraversalEngine unboxEngine = new TraversalEngine(graph, a, Vertex.class, false);

		List<Edge> edges = unboxEngine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, Edge.class, null, true).toList();

		assertThat(edges, containsInAnyOrder(abLikes, acLikes));
	}

	@Test
	public void testTransform_WithoutUnboxing() {
		TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

		List<Set<Edge>> edges = engine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, Collection.class, Edge.class, false).toList();

		assertEquals(1, edges.size());
		assertThat(edges.get(0), containsInAnyOrder(abLikes, acLikes));
	}

	@Test
	public void testTransform_WithoutUnboxingWithScatter() {
		TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

		List<Edge> edges = engine.transform(new Function<Vertex, Collection<Edge>>() {

			@Override
			public Collection<Edge> apply(Vertex t) {
				return t.getEdges(Direction.OUT, "likes");
			}

		}, Collection.class, Edge.class, false).scatter().toList();

		assertEquals(2, edges.size());
		assertThat(edges, containsInAnyOrder(abLikes, acLikes));
	}

}
