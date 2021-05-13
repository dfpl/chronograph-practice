package org.dfpl.chronograph.traversal.memory;

import static org.hamcrest.CoreMatchers.is;
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
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Aggregation {

	@Test
	public void groupBy() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		Map<Boolean, List<Vertex>> map = engine.groupBy(new Function<Vertex, Boolean>() {

			@Override
			public Boolean apply(Vertex t) {
				return t.getProperty("isOdd");
			}
		});

		assertThat(map.size(), is(2));
		assertThat(map, IsMapContaining.hasEntry(true, Arrays.asList(a, c)));
		assertThat(map, IsMapContaining.hasEntry(false, Arrays.asList(b)));
	}

	@Test
	public void groupCount() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);

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

		assertThat(map.size(), is(2));
		assertEquals(map, expectedMap);
	}

	@Test
	public void reduceLastIDVertex() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true);
		b.setProperty("isOdd", false);
		c.setProperty("isOdd", true);

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

		assertThat(v.getId(), is("C"));
	}

}
