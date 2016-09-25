package com.sandbox;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class FreeMarkerTest {

	@Rule
	public TemporaryFolder folder = new TemporaryFolder();

	@Test
	public void testName() throws Exception {
		// Freemarker configuration object
//		Configuration cfg = new Configuration(Configuration.VERSION_2_3_25);
		Configuration cfg = new Configuration(Configuration.DEFAULT_INCOMPATIBLE_IMPROVEMENTS);
		cfg.setEncoding(Locale.US, "ISO-8859-1");
		try {
			// Load template from source folder
			Template template = cfg.getTemplate("src/test/resources/freemarker.ftl");

			// Build the data-model
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("item", "こんにちは");

			// Console output
			Writer out = new OutputStreamWriter(System.out);
			template.process(data, out);
			out.flush();

			// File output
			File newFile = folder.newFile("test.properties");
			Writer file = new FileWriter(newFile);
			template.process(data, file);
			file.flush();
			file.close();

			System.out.println(newFile);
			System.out.println("");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (TemplateException e) {
			e.printStackTrace();
		}
	}
}
