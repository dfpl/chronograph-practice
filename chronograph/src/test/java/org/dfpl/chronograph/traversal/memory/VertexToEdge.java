package org.dfpl.chronograph.traversal.memory;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;
import org.junit.Test;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class VertexToEdge {

	@Test
	public void getOutEdges() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(a, b, "loves");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, a, Vertex.class, false);

		assertThat(engine.outE("likes").toList(), containsInAnyOrder("A|likes|B", "A|likes|C"));
	}

	@Test
	public void getInEdges() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(a, b, "loves");
		graph.addEdge(c, c, "loves");

		TraversalEngine engine = new TraversalEngine(graph, c, Vertex.class, false);

		assertThat(engine.inE("likes", "loves").toList(), containsInAnyOrder("A|likes|C", "C|loves|C"));
	}

}
