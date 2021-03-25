package org.dfpl.chronograph.impl.hgraph;

import java.util.HashMap;
import java.util.Set;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Vertex;

public class HEdge implements Edge {

	String id;
	Vertex outVertex;
	Vertex inVertex;
	String label;
	HashMap<String, Object> properties;
	Graph graph;

	public HEdge(Graph graph, Vertex outVertex, Vertex inVertex, String label) {
		this.graph = graph;
		this.outVertex = outVertex;
		this.inVertex = inVertex;
		this.label = label;
		this.properties = new HashMap<String, Object>();
		this.id = outVertex.getId() + '|' + label + '|' + inVertex.getId();
	}

	public HEdge(Graph graph, Vertex outVertex, Vertex inVertex, String label, String id) {
		this.graph = graph;
		this.outVertex = outVertex;
		this.inVertex = inVertex;
		this.label = label;
		this.id = id;
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		if (direction.equals(Direction.OUT))
			return this.outVertex;
		return this.inVertex;
	}

	@Override
	public String getLabel() {
		return this.label;
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
		this.graph.removeEdge(this);
	}

	@Override
	public String toString() {
		return this.id;
	}
}
