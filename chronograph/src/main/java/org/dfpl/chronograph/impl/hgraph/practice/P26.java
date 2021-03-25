package org.dfpl.chronograph.impl.hgraph.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;

import org.dfpl.chronograph.impl.hgraph.HGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class P26 {

	public static void main(String[] args) throws IOException {
		Graph g = new HGraph();

		BufferedReader br = new BufferedReader(
				new FileReader("C:\\Users\\haifa\\OneDrive\\Desktop\\advanced_database\\week_2\\email.txt"));

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
			String receiver = source.toString();

			HashSet<Vertex> senders = new HashSet<Vertex>();

			for (Vertex in : source.getVertices(Direction.IN, "sendEmail")) {
				senders.add(in);
			}
			if (senders.size() > 0)
				System.out.println(receiver + " received email from " + senders);
		}
	}
}
