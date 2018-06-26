package jp.gr.java_conf.assout.java.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Reflections {

	public static Field getField(Object target, Class<?> clazz, String fieldName) throws NoSuchFieldException {
		while (clazz != null) {
			try {
				Field field = clazz.getDeclaredField(fieldName);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException e) {
				clazz = clazz.getSuperclass();
			}
		}

		throw new NoSuchFieldException(target.getClass().getName() + ":" + fieldName);
	}

	public static Field getField(Object target, String fieldName) throws NoSuchFieldException {
		return getField(target, target.getClass(), fieldName);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object target, String fieldName) {
		try {
			return (T) getField(target, fieldName).get(target);
		} catch (Exception e) {
			AssertionError error = new AssertionError();
			error.initCause(e);
			throw error;
		}
	}

	public static void setFieldValue(Object target, String fieldName, Object value) {
		setFieldValue(target, target.getClass(), fieldName, value);
	}

	public static void setFieldValue(Object target, Class<?> clazz, String fieldName, Object value) {
		try {
			getField(target, clazz, fieldName).set(target, value);
		} catch (Exception e) {
			AssertionError error = new AssertionError();
			error.initCause(e);
			throw error;
		}
	}

	public static Method getMethod(Object target, String methodName, Class<?>... parameterTypes) {
		Class<?> clazz = target.getClass();
		while (clazz != null) {
			try {
				Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
				method.setAccessible(true);
				return method;
			} catch (NoSuchMethodException e) {
				clazz = clazz.getSuperclass();
			}
		}

		throw new NoSuchMethodError(target.getClass().getName() + ":" + methodName);
	}

	public static Object invoke(Object target, Method method, Object... parameter) throws InvocationTargetException {

		try {
			return method.invoke(target, parameter);
		} catch (InvocationTargetException ite) {
			throw ite;
		} catch (Exception e) {
			AssertionError error = new AssertionError();
			error.initCause(e);
			throw error;
		}
	}
}
