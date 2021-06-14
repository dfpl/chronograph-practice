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
	
	/**
	 * Checks whether a temporal relation is true between two time instances 
	 * 
	 * @param t the time to be compared
	 * @param tr the temporal relation to check
	 * */

	public boolean checkTemporalRelation(Time t, TemporalRelation tr) {
		return false;
	}
}
