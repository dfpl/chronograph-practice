package org.dfpl.chronograph.crud.memory;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.TreeSet;

import org.dfpl.chronograph.common.TemporalRelation;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Event;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.VertexEvent;

public class ChronoVertexEvent extends VertexEvent {

	public ChronoVertexEvent(Vertex v, Time time) {
		super(v, time);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Event> NavigableSet<T> getVertexEvents(Direction direction, TemporalRelation tr,
			String[] labels) {
		NavigableSet<ChronoVertexEvent> validEvents = new TreeSet<>((ChronoVertexEvent e1, ChronoVertexEvent e2) -> {
			return e1.compareTo(e2);
		});

		Collection<Vertex> neighborVertices = ((ChronoVertex) this.getElement()).getVertices(direction, labels);

		for (Iterator<Vertex> vIter = neighborVertices.iterator(); vIter.hasNext();) {
			Vertex vertex = vIter.next();
			Event currEvent = vertex.getEvent(getTime(), tr);
			if (currEvent != null) {
				validEvents.add((ChronoVertexEvent) currEvent);
			}
		}

		return (NavigableSet<T>) validEvents;
	}
}
