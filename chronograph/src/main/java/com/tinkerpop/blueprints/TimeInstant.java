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
			switch (tr) {
			case isBefore:
				return s < tp.s;
			case isAfter:
				return s > tp.f;
			case meets:
			case starts:
				return s == tp.s;
			case isMetBy:
			case finishes:
				return s == tp.f;
			case during:
				return s > tp.s && s < tp.f;
			default:
				return false;
			}
		} else {
			TimeInstant ti = (TimeInstant) t;
			switch (tr) {
			case isBefore:
				return s < ti.s;
			case isAfter:
				return s > ti.s;
			case cotemporal:
				return s == ti.s;
			default:
				return false;
			}
		}
	}
}
