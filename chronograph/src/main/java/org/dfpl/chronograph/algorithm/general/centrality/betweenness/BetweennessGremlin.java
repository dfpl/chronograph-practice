package org.dfpl.chronograph.algorithm.general.centrality.betweenness;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;
import com.tinkerpop.gremlin.LoopBundle;

public class BetweennessGremlin {

	public static void main(String[] args) {
		BetweennessGremlin bg = new BetweennessGremlin();
		Graph g = bg.createStaticGraph();
		bg.BC(g);

		bg.printCentrality(g);
	}

	public Graph BC(Graph g) {
		GremlinFluentPipeline pipeline = new TraversalEngine(g, g, Graph.class, false);

		HashMap<String, Path> shortestPaths = new HashMap<>();

		for (Vertex v : g.getVertices())
			v.setProperty("centrality", 0);

		// Traverse the graph and save the shortest paths
		pipeline.V().sideEffect(new Function<Vertex, Vertex>() {

			@Override
			public Vertex apply(Vertex v) {
				for (Vertex t : g.getVertices())
					t.setProperty("d", v.equals(t) ? 0 : -1);

				return v;
			}

		}).as("v")
				// TODO: Implement
				// .both("link")
				// .simplePath()
				.loop("v", new Predicate<LoopBundle<Vertex>>() {

					@Override
					public boolean test(LoopBundle<Vertex> v) {
						boolean continueLoop = (int) v.getTraverser().getProperty("d") < 0;
						v.getTraverser().setProperty("d", 1);

						return continueLoop;
					}
				})
				// TODO
				// .path(s -> s)
				.sideEffect(new Function<List<Vertex>, List<Vertex>>() {

					@Override
					public List<Vertex> apply(List<Vertex> s) {
						Vertex start = (Vertex) s.get(0);
						Vertex end = (Vertex) s.get(s.size() - 1);
						if (start.equals(end))
							return s;

						String key = start.getId() + "|" + end.getId();
						if (shortestPaths.containsKey(key)) {
							Path existingPath = shortestPaths.get(key);
							if (existingPath.getSize() > s.size()) {
								existingPath.setPath(s);
							}
						} else {
							Path newPath = new Path(s);
							shortestPaths.put(key, newPath);
						}
						return s;
					}

				}).toList();

		// Compute Centrality
		for (Vertex v : g.getVertices()) {
			shortestPaths.forEach((key, path) -> {
				if (!path.getStart().equals(v) && !path.getEnd().equals(v) && path.getPath().contains(v)) {
					int oldCentrality = v.getProperty("centrality");
					v.setProperty("centrality", oldCentrality + 1);
				}
			});
		}

		return g;
	}

	public void printCentrality(Graph g) {
		System.out.println("Centrality");
		for (Vertex c : g.getVertices()) {
			System.out.println(c + " " + c.getProperty("centrality").toString());
		}
	}

	public Graph createStaticGraph() {
		Graph g = new ChronoGraph();

		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		Vertex d = g.addVertex("D");
		Vertex e = g.addVertex("E");

		g.addEdge(a, c, "link");
		g.addEdge(c, a, "link");

		g.addEdge(b, c, "link");
		g.addEdge(c, b, "link");

		g.addEdge(c, d, "link");
		g.addEdge(d, c, "link");

		g.addEdge(d, e, "link");
		g.addEdge(e, d, "link");

		return g;
	}

}
