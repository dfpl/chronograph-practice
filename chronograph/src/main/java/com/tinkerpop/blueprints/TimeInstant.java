package com.tinkerpop.blueprints;

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
}
