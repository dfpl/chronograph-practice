package org.dfpl.chronograph.crud.memory;

import java.util.NavigableSet;

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

	@Override
	public <T extends Event> NavigableSet<T> getVertexEvents(Time time, Direction direction, TemporalRelation tr,
			String[] labels) {
		// TODO Auto-generated method stub
		return null;
	}

}
