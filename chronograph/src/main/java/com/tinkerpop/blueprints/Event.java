package com.tinkerpop.blueprints;

public abstract class Event {
	Element element;
	Time time;

	public Time getTime() {
		return time;
	}

	public Element getElement() {
		return element;
	}
}
