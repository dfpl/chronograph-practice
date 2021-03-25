package org.dfpl.chronograph.impl.kgraph.practice;

import org.dfpl.chronograph.impl.kgraph.KGraph;
import org.dfpl.chronograph.impl.kgraph.KVertex;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class P1 {

	public static void main(String[] args) {
		Graph g = new KGraph();
		Vertex a = new KVertex("A");
		Vertex b = new KVertex("B");
		Vertex c = new KVertex("C");
		g.addEdge(a, b, "likes");
		g.addEdge(a, b, "loves");
		g.addEdge(a, c, "likes");
		g.addEdge(c, c, "likes");

		a.setProperty("name", "kyudam");
		a.setProperty("tile", "student");

	}

}
