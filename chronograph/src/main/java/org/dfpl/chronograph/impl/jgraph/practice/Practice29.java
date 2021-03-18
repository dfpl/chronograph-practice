package org.dfpl.chronograph.impl.jgraph.practice;

import java.io.IOException;

import org.dfpl.chronograph.impl.jgraph.JGraph;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice29 {

	public static void main(String[] args) throws IOException {
		Graph g = new JGraph();

		Vertex v1 = g.addVertex("1");
		Vertex v2 = g.addVertex("2");
		Vertex v3 = g.addVertex("3");
		Vertex v4 = g.addVertex("4");
		Vertex v5 = g.addVertex("5");
		Vertex v6 = g.addVertex("6");
		Vertex v7 = g.addVertex("7");
		Vertex v8 = g.addVertex("8");
		Vertex v9 = g.addVertex("9");
		Vertex v10 = g.addVertex("10");
		Vertex v11 = g.addVertex("11");
		Vertex v12 = g.addVertex("12");
		Vertex v13 = g.addVertex("13");

		g.addEdge(v1, v2, "a");
		g.addEdge(v1, v3, "a");
		g.addEdge(v2, v4, "a");
		g.addEdge(v2, v5, "a");
		g.addEdge(v3, v6, "a");
		g.addEdge(v3, v7, "a");
		g.addEdge(v8, v1, "a");
		g.addEdge(v9, v1, "a");
		g.addEdge(v10, v8, "a");
		g.addEdge(v11, v8, "a");
		g.addEdge(v12, v9, "a");
		g.addEdge(v13, v9, "a");

		v1.setProperty("isOdd", true);
		v2.setProperty("isOdd", false);
		v3.setProperty("isOdd", true);
		v4.setProperty("isOdd", false);
		v5.setProperty("isOdd", true);
		v6.setProperty("isOdd", false);
		v7.setProperty("isOdd", true);
		v8.setProperty("isOdd", false);
		v9.setProperty("isOdd", true);
		v10.setProperty("isOdd", false);
		v11.setProperty("isOdd", true);
		v12.setProperty("isOdd", false);
		v13.setProperty("isOdd", true);
	}
}
