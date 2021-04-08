package org.dfpl.chronograph.crud.memory.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice27 {

	public static void main(String[] args) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader("d:\\data.txt"));

		Graph g = new ChronoGraph();

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

		long pre = System.currentTimeMillis();
		for (Vertex v : g.getVertices()) {
			HashSet<Vertex> outVoutVSet = new HashSet<Vertex>();
			Iterable<Vertex> outVs = v.getVertices(Direction.OUT, "sendEmailTo");
			for (Vertex ov : outVs) {
				for (Vertex oov : ov.getVertices(Direction.OUT, "sendEmailTo")) {
					outVoutVSet.add(oov);
				}
			}
			System.out.println(v + " -> " + outVoutVSet.size());
		}
		// I9-10900K - Elapsed Time: 13951
		System.out.println("Elapsed Time: " + (System.currentTimeMillis()-pre));
		
		r.close();
	}
}
