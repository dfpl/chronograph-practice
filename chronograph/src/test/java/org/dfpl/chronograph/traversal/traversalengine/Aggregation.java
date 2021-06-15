package org.dfpl.chronograph.traversal.traversalengine;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import org.hamcrest.collection.IsMapContaining;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Aggregation {

	Graph graph;
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
		graph = new ChronoGraph();

		a = graph.addVertex("A");
		b = graph.addVertex("B");
		c = graph.addVertex("C");

		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testGroupBy() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		Map<Boolean, List<Vertex>> map = engine.groupBy(new Function<Vertex, Boolean>() {

			@Override
			public Boolean apply(Vertex t) {
				return t.getProperty("isOdd");
			}
		});

		assertEquals(2, map.size());
		assertThat(map, IsMapContaining.hasEntry(true, Arrays.asList(a, c)));
		assertThat(map, IsMapContaining.hasEntry(false, Arrays.asList(b)));
	}

	@Test
	public void testGroupCount() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		Map<Boolean, Long> map = engine.groupCount(new Function<Vertex, Boolean>() {

			@Override
			public Boolean apply(Vertex t) {
				return t.getProperty("isOdd");
			}
		});

		@SuppressWarnings("serial")
		Map<Boolean, Long> expectedMap = new HashMap<Boolean, Long>() {
			{
				put(true, Long.valueOf(2));
				put(false, Long.valueOf(1));
			}
		};

		assertEquals(2, map.size());
		assertEquals(map, expectedMap);
	}

	@Test
	public void testReduce_LastIDVertex() {
		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		Vertex v = (Vertex) engine.reduce(new BinaryOperator<Vertex>() {

			@Override
			public Vertex apply(Vertex t, Vertex u) {
				if (t.getId().compareTo(u.getId()) > 0)
					return t;
				else
					return u;
			}
		}).get();

		assertEquals("C", v.getId());
	}

}
