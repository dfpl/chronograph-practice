package org.dfpl.chronograph.traversal.memory.hgremlin.practice;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.memory.hgremlin.HTraversalEngine;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class P1 {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");

		a.setProperty("test", true);
		b.setProperty("test", false);

		Edge abLikes = graph.addEdge(a, b, "likes");
		Edge acLikes = graph.addEdge(a, c, "likes");
		Edge abLoves = graph.addEdge(a, b, "loves");
		Edge ccLoves = graph.addEdge(c, c, "loves");

		// Test V()
		HTraversalEngine engine1 = new HTraversalEngine(graph, graph, Graph.class);
		System.out.println(engine1.V().toList());

		// Test E()
		HTraversalEngine engine2 = new HTraversalEngine(graph, graph, Graph.class);
		System.out.println(engine2.E().toList());

		// Test V(key, object)
		HTraversalEngine engine3 = new HTraversalEngine(graph, graph, Graph.class);
		System.out.println(engine3.V("test", true).toList());
	}

}
