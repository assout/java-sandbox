package com.util;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;

public class WeldContext {
	public static final WeldContext INSTANCE = new WeldContext();

	private final Weld weld;
	private final WeldContainer container;

	private WeldContext() {
		this.weld = new Weld();
		this.container = weld.initialize();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				weld.shutdown();
			}
		});
	}

	public <T> T getBean(Class type) {
		return (T) container.instance().select(type).get();
	}
}
