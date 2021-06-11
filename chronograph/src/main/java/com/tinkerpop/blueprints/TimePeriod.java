package com.tinkerpop.blueprints;

import org.dfpl.chronograph.common.TemporalRelation;

public class TimePeriod extends TimeInstant {

	protected long f;
	protected boolean orderByStart;

	public TimePeriod(long s, long f) {
		super(s);
		if (s == f) {
			throw new IllegalArgumentException("Use TimeInstant");
		} else {
			this.f = f;
			this.orderByStart = true;
		}
	}

	public TimePeriod(long s, long f, boolean orderByStart) {
		super(s);
		if (s == f) {
			throw new IllegalArgumentException("Use TimeInstant");
		} else {
			this.f = f;
			this.orderByStart = orderByStart;
		}
	}

	@Override
	public int compareTo(Time o) {
		if (o instanceof TimeInstant) {
			TimePeriod op = (TimePeriod) o;
			if (orderByStart && op.orderByStart)
				return (s < op.s) ? -1 : ((s == op.s) ? 0 : 1);
			else if (orderByStart && !op.orderByStart)
				return (s < op.f) ? -1 : ((s == op.f) ? 0 : 1);
			else if (!orderByStart && op.orderByStart)
				return (f < op.s) ? -1 : ((f == op.s) ? 0 : 1);
			else
				return (f < op.f) ? -1 : ((f == op.f) ? 0 : 1);
		} else {
			TimeInstant oi = (TimeInstant) o;
			if (orderByStart)
				return (s < oi.s) ? -1 : ((s == oi.s) ? 0 : 1);
			else
				return (f < oi.s) ? -1 : ((f == oi.s) ? 0 : 1);
		}
	}

	@Override
	public boolean checkTemporalRelation(Time t, TemporalRelation tr) {
		if (t instanceof TimeInstant) {
			TimeInstant ti = (TimeInstant) t;
			switch (tr) {
			case isBefore:
				return f < ti.getTime();
			case isAfter:
				return s > ti.getTime();
			case meets:
			case isFinishedBy:
				return f == ti.getTime();
			case isMetBy:
			case isStartedBy:
				return s == ti.getTime();
			case contains:
				return s > ti.getTime() && f < ti.getTime();
			default:
				return false;
			}
		} else {
			TimePeriod tp = (TimePeriod) t;
			switch (tr) {
			case isBefore:
				return f < tp.s;
			case isAfter:
				return s > tp.f;
			case meets:
				return f == tp.s;
			case isMetBy:
				return s == tp.f;
			case overlapsWith:
				return s < tp.s && f > tp.s && f < tp.f;
			case isOverlappedBy:
				return f > tp.s && s < tp.f && f > tp.f;
			case starts:
				return s == tp.s && f < tp.f;
			case isStartedBy:
				return s == tp.s && f > tp.f;
			case during:
				return s > tp.s && f < tp.f;
			case contains:
				return s < tp.s && f > tp.f;
			case finishes:
				return s > tp.s && f == tp.f;
			case isFinishedBy:
				return s < tp.s && f == tp.f;
			default:
				return false;
			}
		}
	}
}
