package org.dfpl.chronograph.impl.hgraph.practice;

import org.dfpl.chronograph.impl.hgraph.HGraph;
import org.dfpl.chronograph.impl.hgraph.HVertex;

import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class TestIndex {

	public static void main(String[] args) {
		Graph g = new HGraph();

		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");

		Edge abLike = g.addEdge(a, b, "likes");
		Edge acLike = g.addEdge(a, c, "likes");
		Edge abLove = g.addEdge(a, b, "loves");
		Edge ccLikes = g.addEdge(c, c, "likes");

		System.out.println("+++Removed ACLike in G+++");
		g.removeEdge(acLike);
		System.out.println("Edges with C should be removed from A's out-going edges");
		for (Edge inEdge : ((HVertex) a).getOutEdges()) {
			System.out.println(inEdge);
		}

		System.out.println("Edges with A should be removed from C's in-going edges");
		for (Edge inEdge : ((HVertex) c).getInEdges()) {
			System.out.println(inEdge);
		}

		System.out.println("Edge AC should be removed from G's edges");
		for (Edge edge : g.getEdges()) {
			System.out.println(edge);
		}

		System.out.println("\n+++Removed A in G+++");
		g.removeVertex(a);
		System.out.println("Edges with A should be removed from B's in-going edges");
		for (Edge inEdge : ((HVertex) b).getInEdges()) {
			System.out.println(inEdge);
		}

		System.out.println("Edges with A should be removed in G's edges");
		for (Edge edge : g.getEdges()) {
			System.out.println(edge);
		}
	}

}
