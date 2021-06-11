package com.tinkerpop.blueprints;

import org.dfpl.chronograph.common.TemporalRelation;

public abstract class Time implements Comparable<Time> {
	/**
	 * Never happen
	 */
	@Override
	public int compareTo(Time o) {
		return 0;
	}

	public boolean checkTemporalRelation(Time t, TemporalRelation tr) {
		return false;
	}
}
