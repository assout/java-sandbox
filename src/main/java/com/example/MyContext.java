package com.example;

import java.lang.annotation.Annotation;

import javax.enterprise.context.spi.Contextual;
import javax.enterprise.context.spi.CreationalContext;

import org.jboss.weld.context.AbstractContext;

public class MyContext extends AbstractContext {

	public MyContext(Class<? extends Annotation> scopeType) {
		super(scopeType);
		// TODO Auto-generated constructor stub
	}

	public <T> T get(Contextual<T> arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	public <T> T get(Contextual<T> arg0, CreationalContext<T> arg1) {
		// TODO Auto-generated method stub
		return null;
	}

}
