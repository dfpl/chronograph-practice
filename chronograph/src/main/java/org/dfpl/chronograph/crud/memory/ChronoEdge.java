package org.dfpl.chronograph.crud.memory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import com.tinkerpop.blueprints.*;
import org.dfpl.chronograph.common.TemporalRelation;

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
		NavigableSet<ChronoEdgeEvent> eventsToMerge = new TreeSet<>(Event::compareTo);
		NavigableSet<ChronoEdgeEvent> eventsToExtend = new TreeSet<>(Event::compareTo);

		for (ChronoEdgeEvent event : this.events) {
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

		ChronoEdgeEvent newEvent = new ChronoEdgeEvent(this, time);
		this.events.add(newEvent);
		return (T) newEvent;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr) {
		NavigableSet<ChronoEdgeEvent> validEvents = new TreeSet<>((ChronoEdgeEvent e1, ChronoEdgeEvent e2) -> {
			if (this.orderByStart)
				return e1.compareTo(e2);
			return e2.compareTo(e1);
		});

		for (Iterator<ChronoEdgeEvent> eIter = this.events.iterator(); eIter.hasNext();) {
			ChronoEdgeEvent event = eIter.next();

			if (event.getTime().checkTemporalRelation(time, tr))
				validEvents.add(event);
		}
		return (NavigableSet<T>) validEvents;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> T getEvent(Time time, TemporalRelation tr) {
		for (Iterator<ChronoEdgeEvent> eIter = this.events.iterator(); eIter.hasNext();) {
			ChronoEdgeEvent event = eIter.next();

			if (event.getTime().checkTemporalRelation(time, tr))
				return (T) event;
		}
		return null;
	}

	@Override
	public void removeEvents(Time time, TemporalRelation tr) {
		for (Iterator<ChronoEdgeEvent> eIter = this.events.iterator(); eIter.hasNext();) {
			ChronoEdgeEvent event = eIter.next();

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
}
