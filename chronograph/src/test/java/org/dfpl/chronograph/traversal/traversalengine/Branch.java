package org.dfpl.chronograph.traversal.traversalengine;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

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

public class Branch {
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

		a.setProperty("isOdd", true);
		a.setProperty("value", 5);
		a.setProperty("direction", "north");

		b.setProperty("isOdd", false);
		b.setProperty("value", 10);
		b.setProperty("direction", "south");

		c.setProperty("isOdd", true);
		c.setProperty("value", 6);
		c.setProperty("direction", "west");

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		Edge abLoves = graph.addEdge(a, b, "loves");
		Edge ccLoves = graph.addEdge(c, c, "loves");

		abLikes.setProperty("isOdd", true);
		abLoves.setProperty("isOdd", true);
		acLikes.setProperty("isOdd", false);
		ccLoves.setProperty("weight", 50);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testIfThenElse_WithoutUnboxing() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		List<Object> vertices = engine.ifThenElse(new Predicate<Vertex>() {

			@Override
			public boolean test(Vertex t) {
				return t.getProperty("isOdd");
			}

		}, new Function<Vertex, Object>() {

			@Override
			public Object apply(Vertex t) {
				return t.getProperty("direction");
			}

		}, new Function<Vertex, Object>() {

			@Override
			public Object apply(Vertex t) {
				return t.getProperty("value");
			}

		}, false, false).toList();

		assertThat(vertices, containsInAnyOrder("north", 10, "west"));
	}

	@SuppressWarnings("unused")
	@Test
	public void testIfThenElse_WithUnboxing() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		List<Object> vertices = engine.ifThenElse(new Predicate<Vertex>() {

			@Override
			public boolean test(Vertex t) {
				return t.getProperty("isOdd");
			}

		}, new Function<Vertex, Collection<Vertex>>() {

			@Override
			public Collection<Vertex> apply(Vertex t) {
				return t.getVertices(Direction.OUT, "likes");
			}

		}, new Function<Vertex, Collection<Vertex>>() {

			@Override
			public Collection<Vertex> apply(Vertex t) {
				return t.getVertices(Direction.OUT, "likes");
			}

		}, true, true).toList();

		fail("Unimplemented");
//		assertThat(vertices, containsInAnyOrder("north", 10, "west"));
	}

}