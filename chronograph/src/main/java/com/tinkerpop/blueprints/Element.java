package com.tinkerpop.blueprints;

import java.util.NavigableSet;
import java.util.Set;

import org.dfpl.chronograph.common.TemporalRelation;

public interface Element {

	/**
	 * An identifier that is unique to its inheriting class. All vertices of a graph
	 * must have unique identifiers. All edges of a graph must have unique
	 * identifiers.
	 *
	 * @return the identifier of the element
	 */
	public String getId();

	/**
	 * Return the object value associated with the provided string key. If no value
	 * exists for that key, return null.
	 *
	 * @param key the key of the key/value property
	 * @return the object value related to the string key
	 */
	public <T> T getProperty(String key);

	/**
	 * Return all the keys associated with the element.
	 *
	 * @return the set of all string keys associated with the element
	 */
	public Set<String> getPropertyKeys();

	/**
	 * Assign a key/value property to the element. If a value already exists for
	 * this key, then the previous key/value is overwritten.
	 *
	 * @param key   the string key of the property
	 * @param value the object value o the property
	 */
	public void setProperty(String key, Object value);

	/**
	 * Un-assigns a key/value property from the element. The object value of the
	 * removed property is returned.
	 *
	 * @param key the key of the property to remove from the element
	 * @return the object value associated with that key prior to removal
	 */
	public <T> T removeProperty(String key);

	/**
	 * Add an event valid at time. The caller (Element) keeps distinct events
	 * regarding their valid time.
	 * 
	 * If time is an instance of TimeInstant and a time instant t is equal to
	 * existing time instant or is in a range of existing time-period, the method
	 * fails and return null.
	 * 
	 * If time is an instance of TimeInstant and a time instant t is not equal to
	 * any existing time instant or is not in a range of any existing time-period,
	 * return a newly created event.
	 * 
	 * If time is an instance of TimePeriod, the method may return a newly created
	 * event. If the time-period p covers any time-instants, the instants are merged
	 * to p. If the time-period p is overlapped with other time-periods, p extends.
	 * If the time-period p is exactly equal to an existing time-period, the method
	 * fails and returns null.
	 * 
	 * @param <T>  VertexEvent or EdgeEvent
	 * @param time
	 * @return VertexEvent or EdgeEvent
	 */
	public <T extends Event> T addEvent(Time time);

	/**
	 * Return events of this element that are matched with tr for time
	 * 
	 * @param <T>  VertexEvent or EdgeEvent
	 * @param time
	 * @param tr
	 * @return NavigableSet of VertexEvent or EdgeEvent
	 */
	public <T extends Event> NavigableSet<T> getEvents(Time time, TemporalRelation tr);

	/**
	 * Return a chronologically closest event that are matched with tr for time
	 * 
	 * @param <T>  VertexEvent or EdgeEvent
	 * @param time
	 * @param tr
	 * @return VertexEvent or EdgeEvent
	 */
	public <T extends Event> T getEvent(Time time, TemporalRelation tr);

	/**
	 * Remove all the events that are matched with tr for time
	 * 
	 * @param time
	 * @param tr
	 */
	public void removeEvents(Time time, TemporalRelation tr);

	/**
	 * Keep the order of valid events by its start time or not if necessary
	 */
	public void setOrderByStart(boolean setOrderByStart);
}
