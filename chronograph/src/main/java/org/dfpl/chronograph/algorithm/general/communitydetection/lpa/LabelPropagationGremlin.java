package org.dfpl.chronograph.algorithm.general.communitydetection.lpa;

import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.dfpl.chronograph.crud.memory.ChronoGraph;
import org.dfpl.chronograph.traversal.TraversalEngine;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.gremlin.GremlinFluentPipeline;

public class LabelPropagationGremlin {

	public static void main(String[] args) {
		LabelPropagationGremlin lpg = new LabelPropagationGremlin();
		Graph g = lpg.createStaticGraph();
		lpg.LPA(g);
		lpg.printLabels(g);
	}

	public Graph LPA(Graph g) {
		GremlinFluentPipeline pipeline = new TraversalEngine(g, g, Graph.class, false);
		HashMap<String, Integer> valueCounter = new HashMap<>();

		while (true) {
			pipeline = new TraversalEngine(g, g, Graph.class, false);

			for (Vertex v : g.getVertices())
				v.setProperty("oldValue", v.getProperty("value"));

			pipeline.V().as("s").transform(new Function<Vertex, Collection<Vertex>>() {

				@Override
				public Collection<Vertex> apply(Vertex v) {
					return v.getVertices(Direction.OUT, "link");
				}
			}, Collection.class, Vertex.class, false)
					.sideEffect(new Function<Collection<Vertex>, Collection<Vertex>>() {

						@Override
						public Collection<Vertex> apply(Collection<Vertex> s) {
							valueCounter.clear();
							for (Vertex n : s) {
								String key = n.getProperty("oldValue").toString();
								int count = valueCounter.getOrDefault(key, 0);
								valueCounter.put(key, count + 1);
							}
							return s;
						}
					})
					// TODO: Implement back
					// .back("s")
					.sideEffect(new Function<Vertex, Vertex>() {

						@Override
						public Vertex apply(Vertex s) {
							String maxValue = valueCounter.entrySet().stream()
									.max(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
							((Vertex) s).setProperty("value", Integer.valueOf(maxValue));
							return s;
						}

					}).toList();

			boolean end = true;
			for (Vertex v : g.getVertices()) {
				if (v.getProperty("oldValue") != v.getProperty("value")) {
					end = false;
					break;
				}
			}
			if (end)
				break;
		}

		return g;
	}

	public void printLabels(Graph g) {
		for (Vertex v : g.getVertices()) {
			System.out.println(v + " " + v.getProperty("value").toString());
		}
	}

	public Graph createStaticGraph() {
		Graph g = new ChronoGraph();

		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		Vertex d = g.addVertex("D");
		Vertex e = g.addVertex("E");
		Vertex f = g.addVertex("F");

		g.addEdge(a, b, "link");
		g.addEdge(b, a, "link");

		g.addEdge(a, c, "link");
		g.addEdge(c, a, "link");

		g.addEdge(c, b, "link");
		g.addEdge(b, c, "link");

		g.addEdge(c, d, "link");
		g.addEdge(d, c, "link");

		g.addEdge(b, d, "link");
		g.addEdge(d, b, "link");

		g.addEdge(d, e, "link");
		g.addEdge(e, d, "link");

		g.addEdge(e, f, "link");
		g.addEdge(f, e, "link");

		g.addEdge(d, f, "link");
		g.addEdge(f, d, "link");

		int i = 0;
		for (Vertex v : g.getVertices()) {
			v.setProperty("value", i);
			i++;
		}

		return g;
	}
}
