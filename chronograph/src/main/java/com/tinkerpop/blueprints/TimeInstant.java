package com.tinkerpop.blueprints;

import org.dfpl.chronograph.common.TemporalRelation;

public class TimeInstant extends Time {
	protected long s;

	public TimeInstant() {
		throw new UnsupportedOperationException();
	}

	public TimeInstant(long t) {
		s = t;
	}

	public long getTime() {
		return s;
	}

	public void setTime(long s) {
		this.s = s;
	}

	@Override
	public int compareTo(Time o) {
		if (o instanceof TimeInstant) {
			TimeInstant oi = (TimeInstant) o;
			return (s < oi.s) ? -1 : ((s == oi.s) ? 0 : 1);
		} else {
			TimePeriod op = (TimePeriod) o;
			if (op.orderByStart)
				return (s < op.s) ? -1 : ((s == op.s) ? 0 : 1);
			else
				return (s < op.f) ? -1 : ((s == op.f) ? 0 : 1);
		}
	}

	@Override
	public boolean checkTemporalRelation(Time t, TemporalRelation tr) {
		// TODO: Confirm relation
		if (t instanceof TimePeriod) {
			TimePeriod tp = (TimePeriod) t;
			return switch (tr) {
				case isBefore -> s < tp.s;
				case isAfter -> s > tp.f;
				case meets, starts -> s == tp.s;
				case isMetBy, finishes -> s == tp.f;
				case during -> s > tp.s && s < tp.f;
				default -> false;
			};
		} else {
			TimeInstant ti = (TimeInstant) t;
			return switch (tr) {
				case isBefore -> s < ti.s;
				case isAfter -> s > ti.s;
				case cotemporal -> s == ti.s;
				default -> false;
			};
		}
	}
	
	@Override
	public String toString() {
		return Long.toString(s);
	}

	public long getS(){
		return s;
	}
}
