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

	@Override
	public boolean equals(Object o) {
		Event e = (Event) o;
		return this.getElement() == e.getElement() && this.getTime() == e.getTime();
	}

	/**
	 * Checks the difference of two events by comparing the time and the element
	 * First, check the difference based on time If the time is equal, check the
	 * difference based on element
	 * 
	 * @param o the event to be compared
	 * @return Integer difference
	 */
	public int compareTo(Object o) {
		Event e = (Event) o;
		int timeComparison = this.getTime().compareTo(e.getTime());
		if (timeComparison != 0)
			return timeComparison;

		return this.getElement().getId().compareTo(e.getElement().getId());
	}
}
