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
		if (o instanceof TimePeriod) {
			TimePeriod first = this;
			TimePeriod second = (TimePeriod) o;
			if (!first.orderByStart)
				first = new TimePeriod(this.f, this.s);
			if (!second.orderByStart)
				second = new TimePeriod(second.f, second.s);

			int startDiff = (first.s < second.s) ? -1 : ((first.s == second.s) ? 0 : 1);
			if (startDiff != 0)
				return startDiff;

			return (first.f < second.f) ? -1 : ((first.f == second.f) ? 0 : 1);
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
		if (t instanceof TimePeriod) {
			TimePeriod tp = (TimePeriod) t;
			return switch (tr) {
				case isBefore -> f < tp.s;
				case isAfter -> s > tp.f;
				case meets -> f == tp.s;
				case isMetBy -> s == tp.f;
				case overlapsWith -> s < tp.s && f > tp.s && f < tp.f;
				case isOverlappedBy -> f > tp.s && s < tp.f && f > tp.f;
				case starts -> s == tp.s && f < tp.f;
				case isStartedBy -> s == tp.s && f > tp.f;
				case during -> s > tp.s && f < tp.f;
				case contains -> s < tp.s && f > tp.f;
				case finishes -> s > tp.s && f == tp.f;
				case isFinishedBy -> s < tp.s && f == tp.f;
				case cotemporal -> s == tp.s && f == tp.f;
				default -> false;
			};
		} else {
			TimeInstant ti = (TimeInstant) t;
			return switch (tr) {
				case isBefore -> f < ti.getTime();
				case isAfter -> s > ti.getTime();
				case meets, isFinishedBy -> f == ti.getTime();
				case isMetBy, isStartedBy -> s == ti.getTime();
				case contains -> s < ti.getTime() && f > ti.getTime();
				default -> false;
			};
		}
	}

	@Override
	public String toString() {
		return this.s + " " + this.f;
	}

	public long getF() {
		return f;
	}

}
