package org.dfpl.chronograph.impl.jgraph.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.dfpl.chronograph.impl.jgraph.JGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice28 {

	public static void main(String[] args) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader("d:\\data.txt"));

		Graph g = new JGraph();

		while (true) {
			String line = r.readLine();
			if (line == null)
				break;
			if (line.startsWith("#"))
				continue;

			String[] arr = line.split("\\s");
			Vertex vl = g.addVertex(arr[0]);
			Vertex vr = g.addVertex(arr[1]);
			g.addEdge(vl, vr, "sendEmailTo");
		}

		for (Vertex v : g.getVertices()) {

			HashSet<Vertex> inVinVSet = new HashSet<Vertex>();
			Iterable<Vertex> inVs = v.getVertices(Direction.IN, "sendEmailTo");
			for (Vertex ov : inVs) {
				for (Vertex iiv : ov.getVertices(Direction.IN, "sendEmailTo")) {
					inVinVSet.add(iiv);
				}
			}

			System.out.println(v + " <- " + inVinVSet);
		}

		r.close();
	}
}
