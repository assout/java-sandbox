package sandbox.jaxb;

import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class DataModel {
	public String name;
	@XmlElementWrapper(name = "items")
	public List<String> item;

	@Override
	public String toString() {
		return "DataModel [name=" + name + ", item=" + item + "]";
	}
}
