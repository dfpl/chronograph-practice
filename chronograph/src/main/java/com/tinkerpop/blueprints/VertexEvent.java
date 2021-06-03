package com.tinkerpop.blueprints;

import java.util.NavigableSet;

import org.dfpl.chronograph.common.TemporalRelation;

public abstract class VertexEvent extends Event {

	public VertexEvent(Vertex v, Time time) {
		this.element = v;
		this.time = time;
	}

	/**
	 * Return neighbor vertex events per a pair (out vertex, in vertex) If there are
	 * multiple events for a pair, a chronologically closest event is chosen. If
	 * there are multiple closest events, one of events having any label is chosen.
	 * 
	 * @param <T>
	 * @param time
	 * @param direction OUT or IN
	 * @param tr        Allen Temporal Relation
	 * @param labels    labels to retain
	 * @return NavigableSet of VertexEvent or EdgeEvent
	 */
	abstract public <T extends Event> NavigableSet<T> getVertexEvents(Time time, Direction direction,
			TemporalRelation tr, String[] labels);
}
