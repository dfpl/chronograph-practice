package org.dfpl.chronograph.traversal.jgremlin.practice;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.jgremlin.JTraversalEngine;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class Practice1 {

	public static void main(String[] args) {
		Graph graph = new ChronoGraph();
		graph.addVertex("A");
		graph.addVertex("B");
		graph.addVertex("C");

		JTraversalEngine<Graph, Vertex> engine = new JTraversalEngine<Graph, Vertex>(graph, graph, Graph.class, true);
		System.out.println(engine.V().toList());
	}

}
