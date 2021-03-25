package org.dfpl.chronograph.impl.hgraph;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class HVertex implements Vertex {

	String id;
	Graph graph;
	HashMap<String, Object> properties;

	public HVertex(Graph graph, String id) {
		this.graph = graph;
		this.id = id;
		this.properties = new HashMap<String, Object>();
	}

	@Override
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		return this.graph.getEdges().parallelStream().filter(e -> e.getVertex(direction).equals(this))
				.filter(e -> Arrays.asList(labels).contains(e.getLabel())).collect(Collectors.toSet());
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		return this.graph.getEdges().parallelStream()
				.filter(e -> e.getVertex(direction).equals(this))
				.filter(e -> Arrays.asList(labels).contains(e.getLabel()))
				.map(e -> e.getVertex(direction.opposite()))
				.collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		return this.graph.addEdge(this, inVertex, label);
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

}
