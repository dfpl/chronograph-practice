package org.dfpl.chronograph.impl.hgraph;

import java.util.HashMap;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Vertex;

public class HEdge implements Edge {

	Vertex outVertex;
	Vertex inVertex;
	String label;
	HashMap<String, Object> properties;

	public HEdge(Vertex outVertex, Vertex inVertex, String label) {
		this.outVertex = outVertex;
		this.inVertex = inVertex;
		this.label = label;
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

	}

}
