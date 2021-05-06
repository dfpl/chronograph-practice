package org.dfpl.chronograph.common;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Step {
	private Object classInstance;
	private Method method;
	private Object[] params;

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Step(final String className, final String methodName, final Class[] args, final Object... params) {
		try {
			Class cls = Class.forName(className);
			this.method = cls.getDeclaredMethod(methodName, args);
			this.params = params;
		} catch (ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
		}
	}

	public void setInstance(Object classInstance) {
		this.classInstance = classInstance;
	}

	public void invoke() {
		try {
			method.invoke(classInstance, params);
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
			System.out.println(e.getCause());
		}
	}

}
