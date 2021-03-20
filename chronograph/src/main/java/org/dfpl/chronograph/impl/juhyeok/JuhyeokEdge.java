package org.dfpl.chronograph.impl.juhyeok;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

public class JuhyeokEdge implements Edge {
	private String label;	
	private Map<String, Object> properties;
	@SuppressWarnings("unused")
	private Graph g;
	private Vertex in;
	private Vertex out;
	
	public JuhyeokEdge(Graph g, Vertex outV, String label, Vertex inV) {
		this.g = g;
		this.in = inV;
		this.label = label;
		this.out = outV;
		this.properties = new HashMap<String, Object>();
	}
	
	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		if (direction.equals(Direction.IN))
			return in;
		else
			return out;
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
		if (this.properties.containsKey(key)) {
			Object removedValue = this.properties.get(key);
			this.properties.remove(key);
			return (T) removedValue;
		} else {
			return null;
		}
	}

	@Override
	public void remove() {
		((JuhyeokVertex)this.in).removeEdge(this);
		((JuhyeokVertex)this.out).removeEdge(this);
	}

	public Vertex getIn() {
		return in;
	}

	public Vertex getOut() {
		return out;
	}

}
