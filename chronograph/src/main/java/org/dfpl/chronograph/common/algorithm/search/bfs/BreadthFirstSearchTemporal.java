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
	static Map<Vertex, Integer> distances;
	static Map<Vertex, Time> gamma;

	public static void main(String[] args) throws IOException {
		Graph g;
		Vertex source;
		Time time;

		// -------- Example: Small Scale Graph 1 -------
//		g = createSmallGraph1();
//
//		source = g.getVertex("A");
//		time = new TimeInstant(3);
//
//		BFS(g, source, time, "links");
//
//		printInfo(g);

		// -------- Example: Small Scale Graph 2 -------
//		g = createSmallGraph2();
//
//		source = g.getVertex("A");
//		time = new TimeInstant(1);
//
//		BFS(g, source, time, "links");
//
//		printInfo(g);

		// -------- Example: Small Scale Graph 3 -------
		g = createSmallGraph3();
		source = g.getVertex("A");
		time = new TimeInstant(3);

		BFS(g, source, time, "links");

		printInfo(g);

		// ------ Large-scale graph
//		g = createLargeGraph();
//
//		source = g.getVertex("582");
//		time = new TimeInstant(0);
//
//		BFS(g, source, time, "emails");
//
//		printInfo(g);
	}

	public static void printInfo(Graph g) {
		for (Vertex v : g.getVertices()) {
			System.out.println(
					v + " dist: " + distances.get(v) + " pred: " + predecessors.get(v) + " time: " + gamma.get(v));
		}
	}

	public static void BFS(Graph g, Vertex source, Time time, String... labels) {
		predecessors = new HashMap<>();
		distances = new HashMap<>();
		gamma = new HashMap<>();
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

		return g;
	}

	public static Graph createSmallGraph1() {
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

		return g;
	}

	public static Graph createSmallGraph2() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");
		Vertex f = graph.addVertex("F");
		Vertex g = graph.addVertex("G");

		Time time2 = new TimeInstant(2);
		Time time3 = new TimeInstant(3);
		Time time4 = new TimeInstant(4);
		Time time5 = new TimeInstant(5);
		Time time7 = new TimeInstant(7);

		// Edges from A
		Edge ab = a.addEdge("links", b);
		ab.addEvent(time2);

		Edge af = a.addEdge("links", f);
		af.addEvent(time7);

		Edge ac = a.addEdge("links", c);
		ac.addEvent(time4);

		// Edges from B
		Edge ba = b.addEdge("links", a);
		ba.addEvent(time5);

		Edge bf = b.addEdge("links", f);
		bf.addEvent(time3);

		// Edges from C
		Edge cf = c.addEdge("links", f);
		cf.addEvent(time5);

		// Edges from F
		Edge fg = f.addEdge("links", g);
		fg.addEvent(time3);

		return graph;
	}

	public static Graph createSmallGraph3() {
		Graph graph = new ChronoGraph();

		Vertex a = graph.addVertex("A");
		Vertex b = graph.addVertex("B");
		Vertex c = graph.addVertex("C");
		Vertex f = graph.addVertex("F");
		Vertex g = graph.addVertex("G");

		Time time3 = new TimeInstant(3);
		Time time5 = new TimeInstant(5);
		Time time6 = new TimeInstant(6);
		Time time7 = new TimeInstant(7);
		Time time8 = new TimeInstant(8);
		Time time9 = new TimeInstant(9);

		// Edges from A
		Edge af = a.addEdge("links", f);
		af.addEvent(time3);
		af.addEvent(time7);

		Edge ab = a.addEdge("links", b);
		ab.addEvent(time6);

		// Edges from B
		Edge bc = b.addEdge("links", c);
		bc.addEvent(time7);

		Edge ba = b.addEdge("links", a);
		ba.addEvent(time8);

		// Edges from C
		Edge cb = c.addEdge("links", b);
		cb.addEvent(time6);

		// Edges from F
		Edge fc = f.addEdge("links", c);
		fc.addEvent(time5);

		Edge fg = f.addEdge("links", g);
		fg.addEvent(time7);

		// Edges from G
		Edge ga = g.addEdge("links", a);
		ga.addEvent(time9);

		for (Edge edge : graph.getEdges()) {
			edge.setOrderByStart(false);
		}

		return graph;
	}
}
