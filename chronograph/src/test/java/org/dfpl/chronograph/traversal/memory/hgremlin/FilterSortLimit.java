package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Predicate;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class FilterSortLimit {

	@Test
	public void getElementsWithPropKeyAndValue() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("test", true); // included
		b.setProperty("test", false);
		c.setProperty("not", true);

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		Edge abLoves = graph.addEdge(a, b, "loves");
		Edge ccLoves = graph.addEdge(c, c, "loves");

		abLikes.setProperty("test", true);
		abLoves.setProperty("test", true);
		acLikes.setProperty("test", false);
		ccLoves.setProperty("notest", "a");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Vertex.class);

		assert (engine.has("test", true).toList().size() == 1);
	}

	@Test
	public void dedup() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex aDup = graph.addVertex("A");

		Collection<Vertex> dupVertices = Arrays.asList(a, b, aDup);
		HTraversalEngine engine = new HTraversalEngine(graph, dupVertices, Vertex.class);

		assertThat(engine.dedup().toList(), containsInAnyOrder(a, b));
	}

	@Test
	public void filter() {

		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Vertex.class);

		List<Vertex> vertices = engine.filter(new Predicate<Vertex>() {

			@Override
			public boolean test(Vertex t) {
				return t.getId().equals("A");
			}

		}).toList();

		assertThat(vertices, containsInAnyOrder(a));
	}

}
