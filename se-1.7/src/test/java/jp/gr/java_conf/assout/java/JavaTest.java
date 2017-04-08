package jp.gr.java_conf.assout.java;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

@SuppressWarnings("all")
@RunWith(Enclosed.class)
public class JavaTest {

	static ExecutorService service = Executors.newFixedThreadPool(10);

	public static class GeneralTest {

		@Test
		public void testLog() {
			Log log = LogFactory.getLog("test");

			log.error("error");
			log.info("info");
		}

		@Test
		public void testNull() {
			String s = null;
			InnerTest innerTest = new InnerTest();
			innerTest.test(s, null);
		}

		@Test
		public void testTypeCast() throws Exception {
			Integer i = new Integer(1);
			Number n = i;
			System.out.println(i instanceof Integer);
			System.out.println(i instanceof Number);

			System.out.println(n instanceof Integer);
			System.out.println(n instanceof Number);

			System.out.println(i instanceof Object);
		}

		@Test
		public void testStringFormat() throws Exception {
			System.out.println(String.format("[%,d] [%,d]", 100000, 20000));
		}

		class InnerTest {
			void test(String s, String s2) {
				System.out.println(s);
				System.out.println(s2);
			}
		}

		@Test
		public void testDummyDispatcher() throws Exception {
			DummyDispatcher dispatcher = null;
//			Iterable<Promise<?>> dispatch = dispatcher.dispatch(new ArrayList<String>());

			List<String> messages = new ArrayList<>();
//			Iterable<Promise<?>> dispatch2 = dispatcher.dispatch2(messages);

			Promise<String> dispatch3 = dispatcher.dispatch3();

//			Promise<String> dispatch4 = dispatcher.dispatch4();

//			Promise<String> dispatch5 = dispatcher.dispatch5();
		}

		interface DummyDispatcher {
			public <V> Iterable<Promise<V>> dispatch(Iterable<?> messages);

			public <V, T> Iterable<Promise<Object>> dispatch2(Iterable<T> messages);

			public <V> Promise<V> dispatch3();

			public <V> Promise<? extends V> dispatch4();

			public <V> Promise<? super V> dispatch5();
		}

		interface Promise<V> {

		}
	}

	public static class AsyncTest {

		@Test
		public void testAsyncCallable() throws InterruptedException, ExecutionException {
			Future<Integer> submit = service.submit(new Callable<Integer>() {
				@Override
				public Integer call() throws Exception {
					// TODO Auto-generated method stub
					return null;
				}

			});
			submit.get();
		}

		@Test
		public void testAsyncCallableVoid() throws InterruptedException, ExecutionException {
			Future<Void> submit = service.submit(new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					return null;
				}

			});
			Void voidd = submit.get();
			System.out.println(voidd);
		}

		@Test
		public void testAsyncRunnableVoid() throws InterruptedException, ExecutionException {
			Future<?> submit = service.submit(new Runnable() {
				@Override
				public void run() {
					System.out.println("run");
				}
			});
			submit.get();
		}

		@Test
		public void testAsnycRunnableWithreturn() throws InterruptedException, ExecutionException {
			Runnable runnable = new Runnable() {
				@Override
				public void run() {
					System.out.println("run");
				}
			};

			String result = "aaa";
			Future<String> submit = service.submit(runnable, result);
			String string = submit.get();
			System.out.println(string);
		}

	}

	public static class GenericsTest {

		@Test
		public void testGenerics() {
			Context<String> c = new Context<>();
		}

		@Test
		public void testGenericsVoid() {
			Void dispatch4 = new Dispatcher().dispatch4();
			System.out.println(dispatch4);
		}

		@Test
		public void testGenerics0() {
			TestTarget<String, Integer> target = new TestTarget<>();
			target.setHoge(null);

			String foo = target.getFoo();
			Integer bar = target.getBar();
		}

		@Test
		public void testGenerics2() throws InterruptedException, ExecutionException {
			Dispatcher dispatcher = new Dispatcher();
			Future<Integer> dispatch = dispatcher.dispatch("Test");
			dispatch.get();
		}

		@Test
		public void testGenerics3() throws InterruptedException, ExecutionException {
			Dispatcher dispatcher = new Dispatcher();
			Future<Integer> dispatch = dispatcher.dispatch("Test");
			dispatch.get();
		}

		@Test
		public void testGenerics4() throws InterruptedException, ExecutionException {
			Dispatcher dispatcher = new Dispatcher();
			Future<Integer> dispatch = dispatcher.dispatch("Test");
			dispatch.get();
		}

		@Test
		public void testGenerics5() {
			Dispatcher dispatcher = new Dispatcher();
			Integer result = dispatcher.dispatch3("Test", String.class);
			System.out.println(result);
		}

		@Test
		public void testGenerics6() {
			Dispatcher dispatcher = new Dispatcher();
			Integer result = dispatcher.dispatch3("Test", String.class);
			System.out.println(result);
		}

		@Test
		public void testGenericsGetParameterType() throws NoSuchMethodException, SecurityException {
			Class<?> c = GenericsTest.class;
			Method[] allMethods = c.getDeclaredMethods();
			for (Method m : allMethods) {
				System.out.println("#" + m.getName());
				Class<?>[] pTypes = m.getParameterTypes();
				for (Class<?> pType : pTypes) {
					System.out.println(pType);
				}
			}
		}

		@Test
		public void testGenericsGetGenericParameterTypes() throws NoSuchMethodException, SecurityException {
			Class<?> c = GenericsTest.class;
			Method[] allMethods = c.getDeclaredMethods();
			for (Method m : allMethods) {
				System.out.println("#" + m.getName());
				Type[] gpType = m.getGenericParameterTypes();
				for (int i = 0; i < gpType.length; i++) {
					System.out.println(gpType[i]);
				}
			}
		}

		@Test
		public void testFormalParameter() throws Exception {
			Integer testFomalParamT = testFomalParamT("test");
			System.out.println(testFomalParamT);
		}

		@Test
		public void testClass() throws Exception {
			Method[] methods = MyProcessor.class.getMethods();
			for (Method m : methods) {
				System.out.println("name:" + m.getName());
				System.out.println("# parameterTypes");
				for (Class<?> c : m.getParameterTypes()) {
					System.out.println("name:" + c.getName());
					Class<?> componentType = c.getComponentType();
					System.out.println("componentType:" + componentType);
					for (Annotation a : c.getAnnotations()) {
						System.out.println("annotation:" + a);
					}
				}

				System.out.println("# parameterAnnotations");

				for (Annotation[] annotations : m.getParameterAnnotations()) {
					for (Annotation a : annotations) {
						System.out.println("annotation:" + a);
					}
				}

				System.out.println("# genericParameterTypes");

				for (Type t : m.getGenericParameterTypes()) {
					System.out.println(t);
				}
			}
		}

		@Test
		public void testRedunduntTypeParameter() {
			final Map<String, Integer> a = new HashMap<String, Integer>();
		}

		@Test
		public void testAutoBoxing() {
			Object o = "aaa";
		}

		static class MyProcessor {

			public void handle1(List<Integer> msg) {
				return;
			}

			public void handle2(List<String> msg) {
				return;
			}
		}

		void receiveContextString(Context<String> c) {
			c.complete("aa");
		}

//	// コンパイルエラーとなること
//	void receiveContextObject(Context<Object> c) { }

		void receiveContext(Context c) {
			c.complete("aa");
		}

		<V> void testFomalParamV(V v) {
			System.out.println(v);
			System.out.println(v.getClass());
		}

		<T> T testFomalParamT(String s) {
			Integer i = new Integer(1);
			System.out.println(s);
			return (T) s;
		}

		public void dummyHandle(Object o, Context<String> c) {
			c.complete("dummy");
		}

		public <T> void dummyHandleWithT(T t) {
		}

		public <T> void dummyHandleWithPrimitive(int i, long l) {
		}

		public class Context<T extends Serializable> {
			void complete(T result) {
				System.out.println(result);
			}
		}

		class Dispatcher {

			public <T, V> Future<T> dispatch(V message) {
				System.out.println(message);
				return null;
			}

			public <T> Future<T> dispatch2(Object message) {
				System.out.println(message);
				return null;
			}

			public <T> T dispatch3(Object message, Class<?> returnType) {
				Integer i = new Integer(1);
				if (!returnType.isInstance(i)) {
					throw new IllegalArgumentException();
				}
				return (T) i;
			}

			public <T> T dispatch4() {
				return null;
			}

		}

		class TestTarget<T, V> {
			public void setHoge(V value) {
				System.out.println(value);
			}

			public T getFoo() {
				return null;
			}

			public V getBar() {
				return null;
			}
		}
	}

	public static class ReflectionTest {
		@Test
		public void testMethod() throws Exception {
			Class<?> c = ReflectionTest.class;
			Method[] allMethods = c.getDeclaredMethods();
			for (Method m : allMethods) {
				System.out.println("# name" + m.getName());
				Class<?> returnType = m.getReturnType();
				System.out.println(returnType);
				System.out.println(returnType.equals(Void.TYPE));
			}
		}

		private void noArgseNoReturn() {

		}

		private String noArgseStringReturn() {
			return null;
		}

		private Void noArgseVoidReturn() {
			return null;
		}

		private void StringArgseNoReturn(String arg) {
		}
	}
}
