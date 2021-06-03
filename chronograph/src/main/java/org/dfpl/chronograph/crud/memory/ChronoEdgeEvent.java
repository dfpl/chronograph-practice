package org.dfpl.chronograph.crud.memory;

import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.EdgeEvent;
import com.tinkerpop.blueprints.Time;
import com.tinkerpop.blueprints.VertexEvent;

public class ChronoEdgeEvent extends EdgeEvent {

	public ChronoEdgeEvent(Edge e, Time time) {
		super(e, time);
	}

	@Override
	public VertexEvent getVertexEvent(Direction direction) {
		// TODO Auto-generated method stub
		return null;
	}
}
