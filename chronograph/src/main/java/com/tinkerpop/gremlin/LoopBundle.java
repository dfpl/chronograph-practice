package com.tinkerpop.gremlin;

import java.util.Map;

public class LoopBundle<T> {
	private T traverser;
	private Map<Object, Object> currentPath;
	private int loopCount;

	public LoopBundle(T traverser, Map<Object, Object> currentPath, int loopCount) {
		super();
		this.traverser = traverser;
		this.currentPath = currentPath;
		this.loopCount = loopCount;
	}

	public T getTraverser() {
		return traverser;
	}

	public void setTraverser(T traverser) {
		this.traverser = traverser;
	}

	public Map<Object, Object> getCurrentPath() {
		return currentPath;
	}

	public void setCurrentPath(Map<Object, Object> currentPath) {
		this.currentPath = currentPath;
	}

	public int getLoopCount() {
		return loopCount;
	}

	public void setLoopCount(int loopCount) {
		this.loopCount = loopCount;
	}
}
