package org.dfpl.chronograph.traversal.memory.hgremlin;

import static org.junit.Assert.*;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class GatherScatter {

	@SuppressWarnings("unused")
	@Test
	public void gather() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Graph.class);

		GremlinFluentPipeline vertices = engine.gather();

		assertThat(vertices.toList(), containsInAnyOrder("A", "B", "C"));
	}

	@SuppressWarnings("unused")
	@Test
	public void scatter() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Graph.class);

		assertThat(engine.scatter().toList(), containsInAnyOrder("A", "B", "C"));
	}

}
