package org.dfpl.chronograph.impl.hgraph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class P25 {

	public static void main(String[] args) throws IOException {
		Graph g = new HGraph();

		BufferedReader br = new BufferedReader(
				new FileReader("d:\\email.txt"));

		while (true) {
			String line = br.readLine();
			if (line == null)
				break;
			if (line.startsWith("#"))
				continue;

			String[] arr = line.split("\\s");

			Vertex from = null;
			try {
				from = g.addVertex(arr[0]);
			} catch (Exception e) {
				from = g.getVertex(arr[0]);
			}
			Vertex to = null;
			try {
				to = g.addVertex(arr[1]);
			} catch (Exception e) {
				to = g.getVertex(arr[1]);
			}
			g.addEdge(from, to, "sendEmail");
		}
		br.close();

		for (Vertex source : g.getVertices()) {
			String sent = source.toString();

			HashSet<Vertex> receivers = new HashSet<Vertex>();

			for (Vertex out : source.getVertices(Direction.OUT, "sendEmail")) {
				receivers.add(out);
			}
			if (receivers.size() > 0)
				System.out.println(sent + " sent email to " + receivers);
		}
	}
}
