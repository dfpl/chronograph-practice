package org.dfpl.chronograph.traversal.memory;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

import org.dfpl.chronograph.common.Tokens.NC;
import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class FilterSortLimit {

	@Test
	public void HasWithPropKeyAndValue() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true); // included
		b.setProperty("isOdd", false);
		c.setProperty("weight", 7);

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		assertThat(engine.has("isOdd", true).toList(), containsInAnyOrder(a));
	}

	@Test
	public void HasWithPropKey() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true); // included
		b.setProperty("isOdd", false); // included
		c.setProperty("weight", 7);

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		assertThat(engine.has("isOdd").toList(), containsInAnyOrder(a, b));
	}

	@Test
	public void HasWithToken() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("isOdd", true); // included
		b.setProperty("isOdd", false);
		c.setProperty("weight", 7);

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		assertThat(engine.has("weight", NC.$eq, 7).toList(), containsInAnyOrder(c));
	}

	@Test
	public void dedup() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex aDup = graph.addVertex("A");

		Collection<Vertex> dupVertices = Arrays.asList(a, b, aDup);
		TraversalEngine engine = new TraversalEngine(graph, dupVertices, Vertex.class, false);

		assertThat(engine.dedup().toList(), containsInAnyOrder(a, b));
	}

	@Test
	public void filter() {

		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");

		TraversalEngine engine = new TraversalEngine(graph, graph.getVertices(), Vertex.class, false);

		List<Vertex> vertices = engine.filter(new Predicate<Vertex>() {

			@Override
			public boolean test(Vertex t) {
				return t.getId().equals("A");
			}

		}).toList();

		assertThat(vertices, containsInAnyOrder(a));
	}

}
