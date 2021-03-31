package org.dfpl.chronograph.impl.juhyeokGraph.practice;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.dfpl.chronograph.impl.jgraph.JGraph;
import org.dfpl.chronograph.impl.juhyeokGraph.JuhyeokGraph;

import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * Copyright (C) 2021- DFPL
 *
 * @author Jaewook Byun, Ph.D, Assistant Professor, Department of Software,
 *         Sejong University, Associate Director of Auto-ID Labs Korea,
 *         jwbyun@sejong.ac.kr, bjw0829@gmail.com
 * 
 * @author Juhyeok Lee, Bachelor Student, Department of Software, Sejong
 *         University, zero5.two4@gmail.com
 */
public class Practice22 {

	public static void main(String[] args) throws IOException {
		BufferedReader r = new BufferedReader(new FileReader("C:\\Users\\NUC\\Documents\\data1.txt"));

		Graph g = new JuhyeokGraph();

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
			System.out.println(v);
		}

		r.close();
	}
}
