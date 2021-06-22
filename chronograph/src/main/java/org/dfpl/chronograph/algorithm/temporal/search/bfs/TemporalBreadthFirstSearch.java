package org.dfpl.chronograph.algorithm.temporal.search.bfs;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.TreeSet;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoEdgeEvent;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

public class TemporalBreadthFirstSearch {

	private Map<Vertex, Vertex> predecessors;
	private Map<Vertex, Integer> distances;
	private Map<Vertex, Time> gamma;

	public TemporalBreadthFirstSearch() {
		predecessors = new HashMap<Vertex, Vertex>();
		distances = new HashMap<Vertex, Integer>();
		gamma = new HashMap<Vertex, Time>();
	}

	public void compute(Graph g, Vertex source, Time time, String... labels) {
		predecessors.clear();
		distances.clear();
		gamma.clear();

		Queue<Vertex> Q = new LinkedList<>();

		distances.put(source, 0);
		gamma.put(source, time);
		Q.add(source);

		while (!Q.isEmpty()) {
			Vertex u = Q.poll();
			time = gamma.get(u);

			// Get all edges with the least time in ascending order
			NavigableSet<ChronoEdgeEvent> events = new TreeSet<>((ChronoEdgeEvent e1, ChronoEdgeEvent e2) -> {
				return e1.compareTo(e2);
			});
			for (Edge e : u.getEdges(Direction.OUT, labels)) {
				ChronoEdgeEvent minVisitEvent = e.getEvent(time, TemporalRelation.isAfter);
				if (minVisitEvent != null)
					events.add(minVisitEvent);
				minVisitEvent = e.getEvent(time, TemporalRelation.cotemporal);
				if (minVisitEvent != null)
					events.add(minVisitEvent);
			}

			for (ChronoEdgeEvent event : events) {
				Vertex v = ((Edge) event.getElement()).getVertex(Direction.IN);

				if (gamma.get(v) == null || event.getTime().compareTo(gamma.get(v)) == -1) {
					distances.put(v, distances.get(u) + 1);
					gamma.put(v, event.getTime());
					predecessors.put(v, u);

					if (!Q.contains(v)) {
						Q.add(v);
					}
				}

			}
		}
	}

	public void printInfo(Graph g) {
		for (Vertex v : g.getVertices()) {
			System.out.println(
					v + " dist: " + distances.get(v) + " pred: " + predecessors.get(v) + " time: " + gamma.get(v));
		}
	}
}
