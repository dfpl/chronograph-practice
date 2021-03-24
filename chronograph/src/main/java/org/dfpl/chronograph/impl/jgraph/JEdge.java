package org.dfpl.chronograph.impl.jgraph;

import java.util.HashMap;
import java.util.Set;

import org.dfpl.chronograph.model.Direction;
import org.dfpl.chronograph.model.Edge;
import org.dfpl.chronograph.model.Graph;
import org.dfpl.chronograph.model.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class JEdge implements Edge {

	private Graph g;
	private String id;
	private Vertex out;
	private String label;
	private Vertex in;
	private HashMap<String, Object> properties;

	public JEdge(Graph g, Vertex out, String label, Vertex in) {
		this.g = g;
		this.out = out;
		this.label = label;
		this.in = in;
		this.id = out.toString() + "|" + label + "|" + in.toString();
	}

	@Override
	public Vertex getVertex(Direction direction) throws IllegalArgumentException {
		if (direction.equals(Direction.OUT)) {
			return out;
		} else if (direction.equals(Direction.IN)) {
			return in;
		} else {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public String getLabel() {
		return label;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getProperty(String key) {
		return (T) properties.get(key);
	}

	@Override
	public Set<String> getPropertyKeys() {
		return this.properties.keySet();
	}

	@Override
	public void setProperty(String key, Object value) {
		properties.put(key, value);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T removeProperty(String key) {
		return (T) properties.remove(key);
	}

	@Override
	public void remove() {
		g.removeEdge(this);
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return id.equals(obj.toString());
	}
}