package org.dfpl.chronograph.crud.memory;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.dfpl.chronograph.common.TemporalRelation;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class ChronoVertex implements Vertex {

	private ChronoGraph g;
	private String id;
	private HashMap<String, Object> properties;

	ChronoVertex(ChronoGraph g, String id) {
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
		if (direction.equals(Direction.OUT)) {
			HashMap<String, HashSet<Edge>> outEdgeSet = g.getOutEdges();
			if (!outEdgeSet.containsKey(id)) {
				return new HashSet<Edge>();
			} else {
				return outEdgeSet.get(id).parallelStream().filter(e -> {
					if (labels == null)
						return true;

					for (String label : labels) {
						if (e.getLabel().equals(label))
							return true;
					}
					return false;
				}).collect(Collectors.toSet());
			}
		} else {
			HashMap<String, HashSet<Edge>> inEdgeSet = g.getInEdges();
			if (!inEdgeSet.containsKey(id)) {
				return new HashSet<Edge>();
			} else {
				return inEdgeSet.get(id).parallelStream().filter(e -> {
					if (labels == null)
						return true;

					for (String label : labels) {
						if (e.getLabel().equals(label))
							return true;
					}
					return false;
				}).collect(Collectors.toSet());
			}
		}
	}

	@Override
	public Collection<Vertex> getVertices(Direction direction, String... labels) {

		if (direction.equals(Direction.OUT)) {
			HashMap<String, HashSet<Edge>> outEdgeSet = g.getOutEdges();
			if (!outEdgeSet.containsKey(id)) {
				return new HashSet<Vertex>();
			} else {
				return outEdgeSet.get(id).parallelStream().filter(e -> {
					if (labels == null)
						return true;

					for (String label : labels) {
						if (e.getLabel().equals(label))
							return true;
					}
					return false;
				}).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
			}
		} else {
			HashMap<String, HashSet<Edge>> inEdgeSet = g.getInEdges();
			if (!inEdgeSet.containsKey(id)) {
				return new HashSet<Vertex>();
			} else {
				return inEdgeSet.get(id).parallelStream().filter(e -> {
					if (labels == null)
						return true;

					for (String label : labels) {
						if (e.getLabel().equals(label))
							return true;
					}
					return false;
				}).map(e -> e.getVertex(direction.opposite())).collect(Collectors.toSet());
			}
		}
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

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public <T extends Event> T addEvent(Time time) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Event> T getEvent(Time time, TemporalRelation tr) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void removeEvents(Time time, TemporalRelation tr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOrderByStart(boolean setOrderByStart) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr, boolean awareOutEvents,
			boolean awareInEvents) {
		// TODO Auto-generated method stub
		return null;
	}

}
