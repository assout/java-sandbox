package jp.gr.java_conf.assout.javax.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AnnotatedDataModel {
	@XmlElement(nillable = false, required = true)
	public String name;
	@XmlElementWrapper(name = "items")
	@XmlElement(required = false)
	public List<String> item;

	@Override
	public String toString() {
		return "AnnotatedDataModel [name=" + name + ", item=" + item + "]";
	}

}
