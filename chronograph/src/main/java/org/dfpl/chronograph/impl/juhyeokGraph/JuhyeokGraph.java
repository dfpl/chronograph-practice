package org.dfpl.chronograph.impl.juhyeokGraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

/**
 * Copyright (C) 2021- DFPL
 *
 *
 * @author Jaewook Byun, Ph.D, Assistant Professor, Department of Software,
 *         Sejong University, Associate Director of Auto-ID Labs Korea,
 *         jwbyun@sejong.ac.kr, bjw0829@gmail.com
 * 
 * @author Juhyeok Lee, Bachelor Student, Department of Software, Sejong
 *         University, zero5.two4@gmail.com
 */

public class JuhyeokGraph implements Graph {
	final Map<String, Vertex> vertices = new HashMap<String, Vertex>();
	final Map<String, Edge> edges = new HashMap<String, Edge>(); // Key: InVertex|label|OutVertex <- Primary key?
	final Object lock = new Object();

	@Override
	public Vertex addVertex(String id) {
		Vertex newVertex = new JuhyeokVertex(this, id);
		this.vertices.put(id, newVertex);
		return newVertex;

	}

	@Override
	public Vertex getVertex(String id) {
		return this.vertices.get(id);
	}

	@Override
	public void removeVertex(Vertex vertex) {
		Iterator<Entry<String, Edge>> esIter = this.edges.entrySet().iterator();
		while(esIter.hasNext()) {
			Entry<String, Edge> entry = esIter.next();
			if (entry.getValue().getVertex(Direction.BOTH).equals(vertex))
				esIter.remove();
			else if (entry.getValue().getVertex(Direction.OUT).equals(vertex))
				esIter.remove();
			else if (entry.getValue().getVertex(Direction.IN).equals(vertex))
				esIter.remove();
		}
		this.vertices.remove(vertex.getId());
	}

	@Override
	public Collection<Vertex> getVertices() {
		return this.vertices.values();
	}

	@Override
	public Collection<Vertex> getVertices(String key, Object value) {
		return this.vertices.values().parallelStream().filter(v -> {
			if (v.getProperty(key) == null)
				return false;
			else if (v.getProperty(key).equals(value))
				return true;
			else
				return false;
		}).collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(Vertex outVertex, Vertex inVertex, String label) {
		String edgeId = outVertex.getId() + "|" + label + "|" + inVertex.getId();
		Edge newEdge = new JuhyeokEdge(this, outVertex, label, inVertex);
		this.edges.put(edgeId, newEdge);
		return newEdge;
	}

	@Override
	public Edge getEdge(Vertex outVertex, Vertex inVertex, String label) {
		String edgeId = outVertex.getId() + "|" + label + "|" + inVertex.getId();
		return this.edges.get(edgeId);
	}

	@Override
	public void removeEdge(Edge edge) {
		String edgeId;
		if (edge.getVertex(Direction.IN).equals(edge.getVertex(Direction.OUT)))
			edgeId = edge.getVertex(Direction.BOTH).getId() + "|" + edge.getLabel() + "|"
					+ edge.getVertex(Direction.BOTH);
		else
			edgeId = edge.getVertex(Direction.OUT).getId() + "|" + edge.getLabel() + "|"
					+ edge.getVertex(Direction.OUT);
		this.edges.remove(edgeId);

	}

	@Override
	public Collection<Edge> getEdges() {
		HashSet<Edge> resultEdges = new HashSet<Edge>();
		this.edges.keySet().parallelStream().forEach(key -> {
			resultEdges.add(this.edges.get(key));
		});
		return resultEdges;
	}

	@Override
	public Collection<Edge> getEdges(String key, Object value) {
		HashSet<Edge> resultEdges = new HashSet<Edge>();
		this.edges.keySet().parallelStream().filter(k -> {
			return this.edges.get(k).getProperty(key).equals(value);
		}).forEach(kk -> {
			resultEdges.add(this.edges.get(kk));
		});
		return resultEdges;
	}

	@Override
	public void shutdown() {

	}

}
