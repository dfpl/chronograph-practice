package org.dfpl.chronograph.impl.jgraph.practice;

import org.dfpl.chronograph.impl.jgraph.JGraph;

import com.tinkerpop.blueprints.Graph;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class Practice1 {

	public static void main(String[] args) {
		Graph g = new JGraph();
		g.addVertex("jack");
	}
}
