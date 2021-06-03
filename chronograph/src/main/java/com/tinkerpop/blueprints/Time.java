package com.tinkerpop.blueprints;

public abstract class Time implements Comparable<Time> {
	/**
	 * Never happen
	 */
	@Override
	public int compareTo(Time o) {
		return 0;
	}
}
