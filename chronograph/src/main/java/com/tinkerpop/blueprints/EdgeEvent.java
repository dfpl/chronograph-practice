package com.tinkerpop.blueprints;

public abstract class EdgeEvent extends Event {

	public EdgeEvent(Edge e, Time time) {
		this.element = e;
		this.time = time;
	}

	/**
	 * Get a vertex event
	 * 
	 * @param direction
	 * @return the vertex event
	 */
	abstract public VertexEvent getVertexEvent(Direction direction);
}
