package org.dfpl.chronograph.common.algorithm.search.bfs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Queue;
import java.util.TreeSet;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoEdgeEvent;
import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

public class BreadthFirstSearchTemporal {

	static Map<Vertex, Vertex> predecessors;
	static Map<Vertex, Double> distances;
	static Map<Vertex, Time> visitingTimes;

	public static void main(String[] args) throws IOException {
		// Small scale-graph
//		Graph g = createSmallGraph();
//
//		Vertex source = g.getVertex("A");
//		Time time = new TimeInstant(3);
//
//		BFS(g, source, time, "links");
//
//		printInfo(g);

		// ------ Large-scale graph
		Graph g = createLargeGraph();

		Vertex source = g.getVertex("582");
		Time time = new TimeInstant(0);

		BFS(g, source, time, "emails");

		printInfo(g);
	}

	public static void printInfo(Graph g) {
		for (Vertex v : g.getVertices()) {
			System.out.println(v + " dist: " + distances.get(v) + " pred: " + predecessors.get(v) + " time: "
					+ visitingTimes.get(v));
		}
	}

	public static Map<Vertex, Vertex> initPredecessors(Graph g) {
		Map<Vertex, Vertex> predecessors = new HashMap<>();

		for (Vertex v : g.getVertices()) {
			predecessors.put(v, null);
		}

		return predecessors;
	}

	public static void BFS(Graph g, Vertex source, Time time, String... labels) {
		predecessors = initPredecessors(g);
		distances = initDistances(g);
		visitingTimes = initVisitingTimes(g);
		Queue<Vertex> Q = new LinkedList<>();

		source.setProperty("traversed", true);

		distances.put(source, (double) 0);
		visitingTimes.put(source, time);
		Q.add(source);

		while (!Q.isEmpty()) {
			Vertex u = Q.poll();
			time = visitingTimes.get(u);

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

				if (v.getProperty("traversed").equals(true))
					continue;

				if (!Q.contains(v)) {
					v.setProperty("traversed", true);

					Q.add(v);

					distances.put(v, distances.get(u) + 1);
					visitingTimes.put(v, event.getTime());
					predecessors.put(v, u);
				} else {
					if (Q.contains(v) && distances.get(v) == distances.get(u) + 1) {
						v.setProperty("traversed", true);

						if (visitingTimes.get(v).compareTo(time) > 1) {
							visitingTimes.put(v, time);
							predecessors.put(v, u);
						}
					} else {
						v.setProperty("traversed", true);
						if (visitingTimes.get(v).compareTo(time) > 1) {
							distances.put(v, distances.get(u) + 1);
							visitingTimes.put(v, time);
							predecessors.put(v, u);
						}
					}
				}

			}

		}
	}

	public static Map<Vertex, Double> initDistances(Graph g) {
		Map<Vertex, Double> distances = new HashMap<>();

		for (Vertex v : g.getVertices()) {
			distances.put(v, Double.POSITIVE_INFINITY);
		}

		return distances;
	}

	public static Map<Vertex, Time> initVisitingTimes(Graph g) {
		Map<Vertex, Time> vt = new HashMap<>();

		for (Vertex v : g.getVertices()) {
			vt.put(v, null);
		}

		return vt;
	}

	public static Graph createLargeGraph() throws IOException {
		Graph g = new ChronoGraph();

		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\haifa\\Downloads\\emails.txt"));
		while (true) {
			String line = br.readLine();
			if (line == null)
				break;

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
			Edge fromTo = g.addEdge(from, to, "emails");
			Edge toFrom = g.addEdge(to, from, "emails");

			Time time = new TimeInstant(Integer.parseInt(arr[2]));

			fromTo.addEvent(time);
			toFrom.addEvent(time);
		}
		br.close();

		for (Vertex v : g.getVertices())
			v.setProperty("traversed", false);

		return g;
	}

	public static Graph createSmallGraph() {
		Graph g = new ChronoGraph();

		Vertex a = g.addVertex("A");
		Vertex b = g.addVertex("B");
		Vertex c = g.addVertex("C");
		Vertex d = g.addVertex("D");
		Vertex e = g.addVertex("E");

		Time time5 = new TimeInstant(5);
		Time time8 = new TimeInstant(8);
		Time time10 = new TimeInstant(10);
		Time time12 = new TimeInstant(12);
		Time time13 = new TimeInstant(13);
		Time time14 = new TimeInstant(14);
		Time time16 = new TimeInstant(16);

		// Edges from A
		Edge ad = a.addEdge("links", d);
		ad.addEvent(time5);

		Edge ab = a.addEdge("links", b);
		ab.addEvent(time10);

		// Edges from B
		Edge bc = b.addEdge("links", c);
		bc.addEvent(time8);
		bc.addEvent(time16);

		Edge ba = b.addEdge("links", a);
		ba.addEvent(time10);

		Edge bd = b.addEdge("links", d);
		bd.addEvent(time12);

		// Edges from C
		Edge cb = c.addEdge("links", b);
		cb.addEvent(time8);
		cb.addEvent(time16);

		Edge cd = c.addEdge("links", d);
		cd.addEvent(time13);

		Edge ce = c.addEdge("links", e);
		ce.addEvent(time14);

		// Edges from D
		Edge da = d.addEdge("links", a);
		da.addEvent(time5);

		Edge db = d.addEdge("links", b);
		db.addEvent(time12);

		Edge dc = d.addEdge("links", c);
		dc.addEvent(time13);

		// Edges from E
		Edge ec = e.addEdge("links", c);
		ec.addEvent(time14);

		for (Vertex v : g.getVertices())
			v.setProperty("traversed", false);

		return g;
	}
}
