package sandbox.jaxb;

import java.io.StringReader;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.ValidationEvent;
import javax.xml.bind.ValidationEventHandler;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamReader;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;

public class JAXBTest {

	public static class Marshal {
		@Test
		public void testSimple() throws Exception {
			DataModel m = new DataModel();
			m.name = "taro";
			m.item = Arrays.asList("mikan", "ringo");
			JAXB.marshal(m, System.out);
		}

		@Test
		public void testMarhasller() throws Exception {
			DataModel m = new DataModel();
			m.name = "taro";
			m.item = Arrays.asList("mikan", "ringo");

			JAXBContext newInstance = JAXBContext.newInstance(DataModel.class);

			Marshaller createMarshaller = newInstance.createMarshaller();

			createMarshaller.marshal(m, System.out);
		}
	}

	public static class Unmarshal {

		@Test
		public void testJAXB() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name>jonh</name>");
			input.append("	<items>");
			input.append("		<item>neko</item>");
			input.append("		<item>inu</item>");
			input.append("	</items>");
			input.append("</dataModel>");
			DataModel unmarshal = JAXB.unmarshal(new StringReader(input.toString()), DataModel.class);
			System.out.println(unmarshal);
		}

		@Test
		public void testUnmarshallerWithSchema() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name>jonh</name>");
			input.append("	<items>");
			input.append("		<item>neko</item>");
			input.append("		<item>inu</item>");
			input.append("	</items>");
			input.append("</dataModel>");

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("jaxb/schema1.xsd"));
			JAXBContext context = JAXBContext.newInstance(DataModel.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);

			DataModel unmarshal = (DataModel) unmarshaller.unmarshal(new StringReader(input.toString()));

			System.out.println(unmarshal);
		}

		@Test
		public void testUnmarshalIgnoreNamespace() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name>jonh</name>");
			input.append("	<items>");
			input.append("		<item>neko</item>");
			input.append("		<item>inu</item>");
			input.append("	</items>");
			input.append("</dataModel>");

			JAXBContext context = JAXBContext.newInstance(DataModel.class);
			XMLInputFactory factory = XMLInputFactory.newFactory();
			factory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, false);
			XMLStreamReader createXMLStreamReader = factory.createXMLStreamReader(new StringReader(input.toString()));

			Unmarshaller unmarshaller = context.createUnmarshaller();

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("jaxb/schema1.xsd"));
			unmarshaller.setSchema(schema);

			Object unmarshal = unmarshaller.unmarshal(createXMLStreamReader);

			// DataModel unmarshal = (DataModel) unmarshaller.unmarshal(new
			// StringReader(input.toString()));

			System.out.println(unmarshal);
		}

		@Test
		public void testUnmarshalWithSchemaNoNamespace() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name>jonh</name>");
			input.append("	<items>");
			input.append("		<item>neko</item>");
			input.append("		<item>inu</item>");
			input.append("	</items>");
			input.append("</dataModel>");

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("jaxb/schema2.xsd"));
			JAXBContext context = JAXBContext.newInstance(DataModel.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);

			DataModel unmarshal = (DataModel) unmarshaller.unmarshal(new StringReader(input.toString()));

			System.out.println(unmarshal);
		}

		@Test
		public void testWithSchema() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name>jonh</name>");
			input.append("	<items>");
			input.append("		<item>neko</item>");
			input.append("		<item>inu</item>");
			input.append("	</items>");
			input.append("</dataModel>");

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("jaxb/schema4.xsd"));
			JAXBContext context = JAXBContext.newInstance(DataModel.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);

			DataModel unmarshal = (DataModel) unmarshaller.unmarshal(new StringReader(input.toString()));

			System.out.println(unmarshal);
		}

		@Test
		public void testWithSchemaCheckEventHandler() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name></name>");
			input.append("	<items>");
			input.append("	</items>");
			input.append("</dataModel>");

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("jaxb/schema5.xsd"));
			JAXBContext context = JAXBContext.newInstance(DataModel.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			unmarshaller.setEventHandler(new ValidationEventHandler() {
				@Override
				public boolean handleEvent(ValidationEvent paramValidationEvent) {
					System.out.println(paramValidationEvent);
					return false;
				}
			});

			DataModel unmarshal = (DataModel) unmarshaller.unmarshal(new StringReader(input.toString()));

			System.out.println(unmarshal);
		}

		@Test
		public void testWithSchemaCheck() throws Exception {
			StringBuilder input = new StringBuilder();
			input.append("<?xml version='1.0' encoding='UTF-8'?>");
			input.append("<dataModel>");
			input.append("	<name>test</name>");
//			input.append("	<items>");
//			input.append("		<item>aa</item>");
//			input.append("	</items>");
			input.append("</dataModel>");

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(getClass().getClassLoader().getResource("jaxb/schema5.xsd"));
			JAXBContext context = JAXBContext.newInstance(DataModel.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);

			DataModel unmarshal = (DataModel) unmarshaller.unmarshal(new StringReader(input.toString()));

			System.out.println(unmarshal);
		}
	}
}
