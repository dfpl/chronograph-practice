package org.dfpl.chronograph.common.algorithm.communitydetection.lpa;

import java.util.HashMap;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class LabelPropagationAlgorithmBlueprints {

	public static void main(String[] args) {
		LabelPropagationAlgorithmBlueprints lpa = new LabelPropagationAlgorithmBlueprints();
		Graph g = lpa.createStaticGraph();
		lpa.LPA(g);
		lpa.printLabels(g);
	}

	public void LPA(Graph g) {
		int t = 0;
		while (true) {
			System.out.println("Iteration: " + t);
			for (Vertex v : g.getVertices()) {
				v.setProperty("oldValue", v.getProperty("value"));
			}

			HashMap<String, Integer> valueCounter = new HashMap<>();
			for (Vertex v : g.getVertices()) {
				for (Vertex n : v.getVertices(Direction.OUT, "link")) {
					String key = n.getProperty("oldValue").toString();
					int count = valueCounter.containsKey(key) ? valueCounter.get(key) : 0;
					valueCounter.put(key, count + 1);
				}

				String maxValue = valueCounter.entrySet().stream()
						.max((entry1, entry2) -> entry1.getValue() - entry2.getValue()).get().getKey();
				v.setProperty("value", Integer.valueOf(maxValue));
			}

			boolean end = true;
			for (Vertex v : g.getVertices()) {

				if (v.getProperty("oldValue") != v.getProperty("value")) {
					end = false;
					break;
				}
			}
			if (end)
				break;
			t++;
		}
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
