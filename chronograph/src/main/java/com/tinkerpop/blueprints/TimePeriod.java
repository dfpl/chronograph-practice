package com.tinkerpop.blueprints;

public class TimePeriod extends TimeInstant {

	protected long f;
	protected boolean orderByStart;

	protected TimePeriod(long s, long f) {
		if (s == f) {
			throw new IllegalArgumentException("Use TimeInstant");
		} else {
			this.s = s;
			this.f = f;
			this.orderByStart = true;
		}
	}

	protected TimePeriod(long s, long f, boolean orderByStart) {
		if (s == f) {
			throw new IllegalArgumentException("Use TimeInstant");
		} else {
			this.s = s;
			this.f = f;
			this.orderByStart = orderByStart;
		}
	}

	@Override
	public int compareTo(Time o) {
		if (o instanceof TimeInstant) {
			TimeInstant oi = (TimeInstant) o;
			if (orderByStart)
				return (s < oi.s) ? -1 : ((s == oi.s) ? 0 : 1);
			else
				return (f < oi.s) ? -1 : ((f == oi.s) ? 0 : 1);
		} else {
			TimePeriod op = (TimePeriod) o;
			if (orderByStart && op.orderByStart)
				return (s < op.s) ? -1 : ((s == op.s) ? 0 : 1);
			else if (orderByStart && !op.orderByStart)
				return (s < op.f) ? -1 : ((s == op.f) ? 0 : 1);
			else if (!orderByStart && op.orderByStart)
				return (f < op.s) ? -1 : ((f == op.s) ? 0 : 1);
			else
				return (f < op.f) ? -1 : ((f == op.f) ? 0 : 1);
		}
	}
}
