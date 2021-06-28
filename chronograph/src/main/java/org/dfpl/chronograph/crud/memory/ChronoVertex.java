package org.dfpl.chronograph.crud.memory;

import java.util.*;
import java.util.stream.Collectors;

import com.tinkerpop.blueprints.*;
import org.dfpl.chronograph.common.TemporalRelation;

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
	private NavigableSet<ChronoVertexEvent> events;
	private boolean orderByStart;

	ChronoVertex(ChronoGraph g, String id) {
		this.id = id;
		this.g = g;
		this.properties = new HashMap<String, Object>();

		this.events = new TreeSet<>(Event::compareTo);

		this.orderByStart = true;
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

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> T addEvent(Time time) {
		NavigableSet<ChronoVertexEvent> eventsToMerge = new TreeSet<>(Event::compareTo);
		NavigableSet<ChronoVertexEvent> eventsToExtend = new TreeSet<>(Event::compareTo);

		for (ChronoVertexEvent event : this.events) {
			Time existingTime = event.getTime();

			// Null conditions
			if ( time.checkTemporalRelation(existingTime, TemporalRelation.cotemporal) ||
				time.checkTemporalRelation(existingTime, TemporalRelation.during) ||
				time.checkTemporalRelation(existingTime, TemporalRelation.starts) ||
				time.checkTemporalRelation(existingTime, TemporalRelation.finishes)
			) return null;

			// Merge conditions
			if (time instanceof TimePeriod && (
				time.checkTemporalRelation(existingTime, TemporalRelation.contains) ||
					time.checkTemporalRelation(existingTime, TemporalRelation.isStartedBy) ||
					time.checkTemporalRelation(existingTime, TemporalRelation.isFinishedBy)
			)) eventsToMerge.add(event);

			// Extend conditions
			if (time instanceof TimePeriod && existingTime instanceof TimePeriod && (
				time.checkTemporalRelation(existingTime, TemporalRelation.isOverlappedBy) ||
					time.checkTemporalRelation(existingTime, TemporalRelation.overlapsWith) ||
					time.checkTemporalRelation(existingTime, TemporalRelation.meets) ||
					time.checkTemporalRelation(existingTime, TemporalRelation.isMetBy)
			)) eventsToExtend.add(event);
		}

		if (!eventsToMerge.isEmpty())
			this.events.removeAll(eventsToMerge);

		if(!eventsToExtend.isEmpty()){
			TimePeriod startTime = (TimePeriod) eventsToExtend.first().getTime();
			TimePeriod finishTime = (TimePeriod) eventsToExtend.last().getTime();
			if( time.compareTo(startTime) < 0)
				startTime = (TimePeriod) time;
			else
				finishTime = (TimePeriod) time;
			time = new TimePeriod( startTime.getS(), finishTime.getF());
			this.events.removeAll(eventsToExtend);
		}

		ChronoVertexEvent newEvent = new ChronoVertexEvent(this, time);
		this.events.add(newEvent);
		return (T) newEvent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr) {
		NavigableSet<ChronoVertexEvent> validEvents = new TreeSet<>((ChronoVertexEvent e1, ChronoVertexEvent e2) -> {
			if (this.orderByStart)
				return e1.compareTo(e2);
			return e2.compareTo(e1);
		});

		for (Iterator<ChronoVertexEvent> eIter = this.events.iterator(); eIter.hasNext();) {
			ChronoVertexEvent event = eIter.next();

			if (event.getTime().checkTemporalRelation(time, tr))
				validEvents.add(event);
		}
		return (NavigableSet<T>) validEvents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> T getEvent(Time time, TemporalRelation tr) {
		for (Iterator<ChronoVertexEvent> eIter = this.events.iterator(); eIter.hasNext();) {
			ChronoVertexEvent event = eIter.next();

			if (event.getTime().checkTemporalRelation(time, tr))
				return (T) event;
		}

		return null;
	}

	@Override
	public void removeEvents(Time time, TemporalRelation tr) {
		for (Iterator<ChronoVertexEvent> eIter = this.events.iterator(); eIter.hasNext();) {
			ChronoVertexEvent event = eIter.next();

			if (event.getTime().checkTemporalRelation(time, tr))
				eIter.remove();
		}
	}

	@Override
	public void setOrderByStart(boolean orderByStart) {
		if (this.orderByStart == orderByStart)
			return;

		this.orderByStart = orderByStart;
		this.events = this.events.descendingSet();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr, boolean awareOutEvents,
			boolean awareInEvents) {

		NavigableSet<ChronoVertexEvent> validEvents = new TreeSet<>((ChronoVertexEvent e1, ChronoVertexEvent e2) -> {
			if (this.orderByStart)
				return e1.compareTo(e2);
			return e2.compareTo(e1);
		});

		validEvents.addAll(this.getEvents(time, tr));

		if (awareOutEvents) {
			this.getVertices(Direction.OUT, (String[]) null).forEach(n -> {
				validEvents.addAll(n.getEvents(time, tr));
			});
		}

		if (awareInEvents) {
			this.getVertices(Direction.IN, (String[]) null).forEach(n -> {
				validEvents.addAll(n.getEvents(time, tr));
			});
		}

		return (NavigableSet<T>) validEvents;
	}

}
