package org.dfpl.chronograph.impl.jgraph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Set;
import java.util.stream.Collectors;

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
public class JVertex implements Vertex {

	private Graph g;
	private String id;
	private HashMap<String, Object> properties;

	public JVertex(Graph g, String id) {
		this.id = id;
		this.g = g;
		this.properties = new HashMap<String, Object>();
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
	public Collection<Edge> getEdges(Direction direction, String... labels) {
		return g.getEdges().parallelStream().filter(e -> {
			return e.getVertex(direction).equals(this);
		}).filter(e -> {
			for (String label : labels) {
				if (e.getLabel().equals(label))
					return true;
			}
			return false;
		}).collect(Collectors.toSet());
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {
		return g.getEdges().parallelStream().filter(e -> {
			return e.getVertex(direction).equals(this);
		}).filter(e -> {
			for (String label : labels) {
				if (e.getLabel().equals(label))
					return true;
			}
			return false;
		}).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
	}

	@Override
	public Edge addEdge(String label, Vertex inVertex) {
		return g.addEdge(this, inVertex, label);
	}

	@Override
	public void remove() {
		g.removeVertex(this);
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String toString() {
		return id;
	}

	@Override
	public boolean equals(Object obj) {
		return this.getId().equals(obj.toString());
	}

}