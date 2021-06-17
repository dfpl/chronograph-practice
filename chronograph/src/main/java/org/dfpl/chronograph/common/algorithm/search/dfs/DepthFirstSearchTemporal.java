package org.dfpl.chronograph.common.algorithm.search.dfs;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.dfpl.chronograph.common.TemporalRelation;
import org.dfpl.chronograph.crud.memory.ChronoEdgeEvent;
import org.dfpl.chronograph.crud.memory.ChronoGraph;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.TimeInstant;
import com.tinkerpop.blueprints.Vertex;

public class DepthFirstSearchTemporal {

	static HashMap<Vertex, Time> gamma;
	static HashMap<Vertex, Vertex> predecessors;

	public static void main(String[] args) throws IOException {
		Graph g;
		Vertex source;
		Time time;

		// ------- EXAMPLE: Small Graph 1 --------
//		g = createSmallGraph1();
//		source = g.getVertex("A");
//		time = new TimeInstant(3);
//
//		DFS(g, source, time, "links");
//
//		printInfo(g);

		// ------- EXAMPLE: Small Graph 2 --------
		g = createSmallGraph2();
		source = g.getVertex("A");
		time = new TimeInstant(1);

		DFS(g, source, time, "links");

		printInfo(g);

		// ------- EXAMPLE: Small Graph 3 --------
//		g = createSmallGraph3();
//		source = g.getVertex("A");
//		time = new TimeInstant(3);
//
//		DFS(g, source, time, "links");
//
//		printInfo(g);

		// ------- EXAMPLE: Large Graph--------
//		g = createLargeGraph();
//
//		source = g.getVertex("582");
//		time = new TimeInstant(0);
//
//		DFS(g, source, time, "emails");
//		printInfo(g);
	}

	public static void printInfo(Graph g) {
		for (Vertex v : g.getVertices()) {
			System.out.println(v + " Time: " + gamma.get(v) + " Pred: " + predecessors.get(v));
		}
	}

	public static void DFS(Graph g, Vertex source, Time time, String... labels) {
		gamma = new HashMap<>();
		predecessors = new HashMap<>();

		gamma.put(source, time);

		Vertex u = source;
		while (true) {
			NavigableSet<ChronoEdgeEvent> validEvents = new TreeSet<>((ChronoEdgeEvent e1, ChronoEdgeEvent e2) -> {
				return e2.compareTo(e1);
			});

			for (Edge edge : u.getEdges(Direction.OUT, labels)) {
				if (edge.getProperty("traversed") != null)
					continue;

				Event event = edge.getEvent(gamma.get(u), TemporalRelation.isAfter);
				if (event != null)
					validEvents.add((ChronoEdgeEvent) event);
				event = edge.getEvent(gamma.get(u), TemporalRelation.cotemporal);
				if (event != null)
					validEvents.add((ChronoEdgeEvent) event);
			}

			if (!validEvents.isEmpty()) {
				ChronoEdgeEvent traverseEvent = validEvents.first();

				Edge e = ((Edge) traverseEvent.getElement());
				e.setProperty("traversed", true);

				Vertex v = ((Edge) traverseEvent.getElement()).getVertex(Direction.IN);
				if (gamma.get(v) == null || gamma.get(v).compareTo(traverseEvent.getTime()) == 1) {
					predecessors.put(v, u);
					gamma.put(v, traverseEvent.getTime());
					u = v;
				}
			} else {
				u = predecessors.get(u);
				if (u == null)
					break;
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

		for (Edge e : g.getEdges()) {
			e.setOrderByStart(false);
		}

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

		for (Edge edge : g.getEdges()) {
			edge.setOrderByStart(false);
		}

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

		for (Edge edge : graph.getEdges()) {
			edge.setOrderByStart(false);
		}

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
