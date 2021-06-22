package org.dfpl.chronograph.algorithm.general.search.bfs;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.LoopBundle;

/**
 * Hello world!
 *
 */
public class BreadthFirstSearchGremlin {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public ArrayList<List> BFS(Graph g, Vertex v, Direction d, String label) {
		ArrayList<List> wcc = new ArrayList<List>();
		ArrayList<Vertex> queue0 = new ArrayList<Vertex>();
		queue0.add(v);
		wcc.add(queue0);
		v.setProperty("tag", "VISITED");

		GremlinFluentPipeline traversal = new TraversalEngine(g, v, Vertex.class, false);

		traversal.as("s").outE(label).filter(new Predicate<Edge>() {

			@Override
			public boolean test(Edge edge) {
				if (edge.getProperty("tag").toString().equals("UNEXPLORED")) {
					Vertex w = edge.getVertex(d.opposite());
					if (w.getProperty("tag").toString().equals("UNEXPLORED")) {
						edge.setProperty("tag", "DISCOVERY");
						w.setProperty("tag", "VISITED");
						return true;
					} else {
						edge.setProperty("tag", "CROSS");
					}
				}
				return false;
			}
		}).inV().gather().sideEffect(new Function() {

			@Override
			public Object apply(Object t) {
				List list = (List) t;
				wcc.add(list);
				return t;
			}
		}).scatter().loop("s", new Predicate<LoopBundle<Vertex>>() {

			@Override
			public boolean test(LoopBundle<Vertex> t) {
				if (t.getTraverser() == null) {
					return false;
				}
				return true;
			}

		}).toList();
		return wcc;
	}

	@SuppressWarnings("rawtypes")
	public ArrayList<ArrayList<List>> BFS(Graph g, Direction d, String label) {
		ArrayList<ArrayList<List>> result = new ArrayList<ArrayList<List>>();
		for (Vertex v : g.getVertices()) {
			v.setProperty("tag", "UNEXPLORED");
		}
		for (Edge e : g.getEdges()) {
			e.setProperty("tag", "UNEXPLORED");
		}

		for (Vertex v : g.getVertices()) {
			if (v.getProperty("tag").toString().equals("UNEXPLORED")) {
				result.add(BFS(g, v, d, label));
			}
		}
		return result;
	}

	public Graph createExample() {
		Graph g = new ChronoGraph();
		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");
		Vertex v4 = g.addVertex("4");
		Vertex v5 = g.addVertex("5");
		Vertex v6 = g.addVertex("6");
		Vertex v7 = g.addVertex("7");
		Vertex v8 = g.addVertex("8");

		g.addEdge(v1, v2, "c");
		g.addEdge(v1, v3, "c");
		g.addEdge(v1, v4, "c");
		g.addEdge(v2, v3, "c");
		g.addEdge(v2, v5, "c");
		g.addEdge(v3, v4, "c");
		g.addEdge(v3, v5, "c");
		g.addEdge(v3, v6, "c");
		g.addEdge(v4, v6, "c");
		g.addEdge(v7, v8, "c");

		return g;
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) {

		BreadthFirstSearchGremlin bfs = new BreadthFirstSearchGremlin();
		Graph g = bfs.createExample();

		ArrayList<ArrayList<List>> result = bfs.BFS(g, Direction.OUT, "c");
		System.out.println(result);
	}
}
