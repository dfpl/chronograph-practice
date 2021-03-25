package org.dfpl.chronograph.impl.hgraph;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class HVertex implements Vertex {

	String id;
	Graph graph;
	HashMap<String, Object> properties;
	HashSet<Edge> outEdges;
	HashSet<Edge> inEdges;

	public HashSet<Edge> getOutEdges() {
		return outEdges;
	}

	public HashSet<Edge> getInEdges() {
		return inEdges;
	}

	public HVertex(Graph graph, String id) {
		this.graph = graph;
		this.id = id;
		this.properties = new HashMap<String, Object>();
		this.outEdges = new HashSet<Edge>();
		this.inEdges = new HashSet<Edge>();
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		return this.graph.getEdges().parallelStream().filter(e -> e.getVertex(direction).equals(this))
				.filter(e -> Arrays.asList(labels).contains(e.getLabel())).collect(Collectors.toSet());
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		Collection<Edge> targetEdges = this.inEdges;

		if (Direction.OUT.equals(direction))
			targetEdges = this.outEdges;

		return targetEdges.parallelStream().filter(e -> Arrays.asList(labels).contains(e.getLabel()))
				.map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		Edge newEdge = new HEdge(this.graph, this, inVertex, label);
		this.outEdges.add(newEdge);
		((HVertex) inVertex).inEdges.add(newEdge);
		return newEdge;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		return (T) this.properties.get(key);
	}

	@Override
	public Set<String> getPropertyKeys() {
		return this.properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		this.properties.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeProperty(String key) {
		return (T) this.properties.remove(key);
	}

	@Override
	public void remove() {
		this.graph.removeVertex(this);
	}

	@Override
	public String getId() {
		return this.id;
	}

	@Override
	public String toString() {
		return this.id;
	}

	public void removeEdgeIndex(Edge edge, Direction direction) {
		if (direction.equals(Direction.IN))
			this.inEdges.remove(edge);
		else if (direction.equals(Direction.OUT))
			this.outEdges.remove(edge);

	}
}
