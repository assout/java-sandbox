package jp.gr.java_conf.assout.java.util;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassPathModifier {

	private static final Class<?>[] parameters = new Class[] { URL.class };

	public static void addFile(String s) throws IOException {
		File f = new File(s);
		addFile(f);
	}

	public static void addFile(File f) throws IOException {
		addURL(f.toURI().toURL());
	}

	public static void addURLs(URL[] u) throws IOException {
		for (URL url : u) {
			addURL(url);
		}
	}

	public static void addURL(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();

		Class<?> sysclass = URLClassLoader.class;

		try {
			Method method = sysclass.getDeclaredMethod("addURL", parameters);
			method.setAccessible(true);
			method.invoke(sysloader, new Object[] { u });
		} catch (Throwable t) {
			throw new IOException("could not add " + u + " to classpath");
		}
	}

	public static void addFileInFirst(String s) throws IOException {
		File f = new File(s);
		addFileInFirst(f);
	}

	public static void addFileInFirst(File f) throws IOException {
		addURLInFirst(f.toURI().toURL());
	}

	@SuppressWarnings("restriction")
	public static void addURLInFirst(URL u, ClassLoader loader) throws IOException {
		try {
			sun.misc.URLClassPath oldUrl = Reflections.getFieldValue(loader, "ucp");
			List<URL> newUrlList = new ArrayList<>(Arrays.asList(oldUrl.getURLs()));
			newUrlList.add(0, u);

			sun.misc.URLClassPath newUrl = new sun.misc.URLClassPath(newUrlList.toArray(new URL[newUrlList.size()]));

			Reflections.setFieldValue(loader, "ucp", newUrl);

		} catch (Throwable t) {
			throw new IOException("could not add " + u + " to classpath", t);
		}
	}

	public static void addURLInFirst(URL u) throws IOException {
		URLClassLoader sysloader = (URLClassLoader) ClassLoader.getSystemClassLoader();
		addURLInFirst(u, sysloader);
	}

	public static void addFileInFirst(File f, ClassLoader loader) throws IOException {
		addURLInFirst(f.toURI().toURL(), loader);
	}

	public static void addFileInFirst(String s, ClassLoader loader) throws IOException {
		File f = new File(s);
		addFileInFirst(f, loader);
	}
}
