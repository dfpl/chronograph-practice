package org.dfpl.chronograph.traversal.practice;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;

import com.tinkerpop.blueprints.Graph;

public class Practice1 {

	public static void main(String[] args) {
		Graph graph = new ChronoGraph();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");

		TraversalEngine engine = new TraversalEngine(graph, graph, Graph.class, true);
		System.out.println(engine.V().out().outE().inV().out().toList());
	}

}
