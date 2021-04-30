package org.dfpl.chronograph.traversal.memory.hgremlin;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.junit.Test;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsIterableContainingInAnyOrder.containsInAnyOrder;

public class ElementToString {

	@Test
	public void getIdsOfVertices() {
		Graph graph = new ChronoGraph();

		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getVertices(), Vertex.class);

		assertThat(engine.id().toList(), containsInAnyOrder("A", "B", "C"));
	}

	@Test
	public void getIdsOfEdges() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(c, c, "loves");

		HTraversalEngine engine = new HTraversalEngine(graph, graph.getEdges(), Edge.class);

		assertThat(engine.id().toList(), containsInAnyOrder("A|likes|B", "A|likes|C", "C|loves|C"));
	}

	@Test
	public void getElements() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		HTraversalEngine vEngine = new HTraversalEngine(graph, graph, Graph.class);

		assertThat(vEngine.element(Vertex.class).toList(), containsInAnyOrder("A", "B", "C"));

		graph.addEdge(a, b, "likes");
		graph.addEdge(a, c, "likes");
		graph.addEdge(c, c, "loves");

		HTraversalEngine eEngine = new HTraversalEngine(graph, graph, Graph.class);

		assertThat(eEngine.element(Edge.class).toList(), containsInAnyOrder("A|likes|B", "A|likes|C", "C|loves|C"));
	}

}