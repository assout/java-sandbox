package sandbox.jaxb;

import java.io.StringReader;
import java.util.Arrays;

import javax.xml.XMLConstants;
import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.junit.Test;

public class JAXBTest {

	@Test
	public void testMarshal() throws Exception {
		DataModel m = new DataModel();
		m.name = "taro";
		m.item = Arrays.asList("mikan", "ringo");
		JAXB.marshal(m, System.out);
	}

	@Test
	public void testUnmarshal() throws Exception {
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
	public void testUnmarshalWithSchema() throws Exception {
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
}
