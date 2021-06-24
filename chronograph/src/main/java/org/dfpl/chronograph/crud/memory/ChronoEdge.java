package org.dfpl.chronograph.crud.memory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.dfpl.chronograph.common.TemporalRelation;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Graph;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;

/**
 * The in-memory implementation of temporal graph database.
 *
 * @author Jaewook Byun, Ph.D., Assistant Professor, Department of Software,
 *         Sejong University (slightly modify interface)
 */
public class ChronoEdge implements Edge {

	private Graph g;
	private String id;
	private Vertex out;
	private String label;
	private Vertex in;
	private HashMap<String, Object> properties;
	private NavigableSet<ChronoEdgeEvent> events;
	private boolean orderByStart;

	public ChronoEdge(Graph g, Vertex out, String label, Vertex in) {
		this.g = g;
		this.out = out;
		this.label = label;
		this.in = in;
		this.id = out.toString() + "|" + label + "|" + in.toString();
		this.properties = new HashMap<String, Object>();

		this.events = new TreeSet<>((ChronoEdgeEvent e1, ChronoEdgeEvent e2) -> {
			return e1.compareTo(e2);
		});

		this.orderByStart = true;
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

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public String getId() {
		return id;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> T addEvent(Time time) {
		ChronoEdgeEvent event = new ChronoEdgeEvent(this, time);
		this.events.add(event);
		return (T) event;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation... temporalRelations) {
		NavigableSet<ChronoEdgeEvent> validEvents = new TreeSet<>((ChronoEdgeEvent e1, ChronoEdgeEvent e2) -> {
			if (this.orderByStart)
				return e1.compareTo(e2);
			return e2.compareTo(e1);
		});

		if (temporalRelations == null) return (NavigableSet<T>) validEvents;

		for (ChronoEdgeEvent event : this.events) {
			for(TemporalRelation tr: temporalRelations){
				if (event.getTime().checkTemporalRelation(time, tr))
					validEvents.add(event);
			}

		}
		return (NavigableSet<T>) validEvents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> T getEvent(Time time, TemporalRelation tr) {
		for (ChronoEdgeEvent event : this.events) {
			if (event.getTime().checkTemporalRelation(time, tr))
				return (T) event;
		}
		return null;
	}

	@Override
	public void removeEvents(Time time, TemporalRelation tr) {
		this.events.removeIf(event -> event.getTime().checkTemporalRelation(time, tr));
	}

	@Override
	public void setOrderByStart(boolean orderByStart) {
		if (this.orderByStart == orderByStart)
			return;

		this.orderByStart = orderByStart;
		this.events = this.events.descendingSet();
	}
}
