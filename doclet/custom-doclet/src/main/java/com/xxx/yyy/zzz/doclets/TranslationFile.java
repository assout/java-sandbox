package com.xxx.yyy.zzz.doclets;
///*** Eclipse Class Decompiler plugin, copyright (c) 2016 Chen Chao (cnfree2000@hotmail.com) ***/
//package doclets.l10n;
//
//import com.sun.javadoc.ClassDoc;
//import com.sun.javadoc.Doc;
//import com.sun.javadoc.PackageDoc;
//import com.sun.javadoc.RootDoc;
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import javax.xml.parsers.DocumentBuilder;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerException;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.transform.stream.StreamResult;
//import org.w3c.dom.CDATASection;
//import org.w3c.dom.Document;
//import org.w3c.dom.Element;
//import org.w3c.dom.Node;
//import org.w3c.dom.NodeList;
//import org.w3c.dom.Text;
//import org.xml.sax.SAXException;
//
//public class TranslationFile {
//	private Configuration config;
//	private static Doc doc;
//	private List<Comment> commentsList;
//	private static String fs = System.getProperty("file.separator");
//	private File file;
//
//	public TranslationFile(Configuration configuration, Doc document) {
//		doc = document;
//		this.config = configuration;
//		this.commentsList = new ArrayList();
//		this.file = getFile();
//	}
//
//	public boolean canRead() {
//		return (getFile().canRead());
//	}
//
//	private File getFile() {
//		String path;
//		if ((doc.isClass()) || (doc.isInterface()) || (doc.isAnnotationType()))
//			path = getPath((ClassDoc) doc);
//		else {
//			path = getPath((PackageDoc) doc);
//		}
//
//		if (!(this.config.getCmntpath().equals("")))
//			return new File(this.config.getCmntpath(), path);
//		if (!(this.config.destDirName.equals(""))) {
//			return new File(this.config.destDirName, path);
//		}
//		return new File(path);
//	}
//
//	private static String getPath(PackageDoc pd) {
//		return pd.name().replace(".", fs) + fs + "package.cmnt";
//	}
//
//	private static String getPath(ClassDoc cd) {
//		if (cd.containingPackage().name().equals("")) {
//			return cd.name() + "-cmnt.xml";
//		}
//		return cd.containingPackage().name().replace(".", fs) + fs + cd.name() + "-cmnt.xml";
//	}
//
//	public void addComment(String key, String data) {
//		this.commentsList.add(new Comment(key, data));
//	}
//
//	public void generate() {
//		try {
//			Document dom = getDom();
//
//			File dir = this.file.getParentFile();
//			if (dir.mkdirs()) {
//				this.config.root.printNotice("Created directory: " + dir.getAbsolutePath());
//			}
//
//			TransformerFactory tf = TransformerFactory.newInstance();
//			Transformer transformer = tf.newTransformer();
//			transformer.setOutputProperty("encoding", "UTF-8");
//			transformer.setOutputProperty("indent", "yes");
//			transformer.setOutputProperty("method", "xml");
//			StreamResult result = new StreamResult(this.file);
//			DOMSource source = new DOMSource(dom);
//
//			this.config.root.printNotice("Generating " + this.file.getPath() + "...");
//			transformer.transform(source, result);
//		} catch (TransformerException ex) {
//			ex.printStackTrace();
//			this.config.root.printError(ex.getMessageAndLocation());
//		}
//	}
//
//	private Document getDom() {
//		Document dom = null;
//		try {
//			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//			DocumentBuilder db = dbf.newDocumentBuilder();
//			dom = db.newDocument();
//			Element javadoc = dom.createElement("javadoc");
//			dom.appendChild(javadoc);
//			Element meta = dom.createElement("meta");
//			javadoc.appendChild(meta);
//			Element date = dom.createElement("date-generated");
//			meta.appendChild(date);
//			Text dateString = dom.createTextNode(new Date().toString());
//			date.appendChild(dateString);
//
//			Iterator itr = this.commentsList.iterator();
//			while (itr.hasNext()) {
//				Comment c = (Comment) itr.next();
//				Element comment = dom.createElement("comment");
//				Element key = dom.createElement("key");
//				Element data = dom.createElement("data");
//				comment.appendChild(key);
//				comment.appendChild(data);
//				CDATASection keyText = dom.createCDATASection(c.key);
//				CDATASection dataText = dom.createCDATASection(c.data);
//				key.appendChild(keyText);
//				data.appendChild(dataText);
//				javadoc.appendChild(comment);
//			}
//		} catch (ParserConfigurationException ex) {
//			this.config.root.printError(ex.getMessage());
//		}
//		return dom;
//	}
//
//	public Map<String, String> getTranslation() {
//		Map translations = new HashMap();
//		try {
//			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
//			DocumentBuilder builder = builderFactory.newDocumentBuilder();
//			Document dom = builder.parse(getFile());
//			NodeList comments = dom.getElementsByTagName("comment");
//			for (int i = 0; i < comments.getLength(); ++i) {
//				Element comment = (Element) comments.item(i);
//				String key = comment.getElementsByTagName("key").item(0).getTextContent().trim();
//				String data = comment.getElementsByTagName("data").item(0).getTextContent();
//				translations.put(key, data);
//			}
//		} catch (SAXException ex) {
//			this.config.root.printError(ex.getMessage());
//		} catch (IOException ex) {
//			this.config.root.printError(ex.getMessage());
//		} catch (ParserConfigurationException ex) {
//			this.config.root.printError(ex.getMessage());
//		}
//		return translations;
//	}
//
//	private class Comment {
//		String key;
//		String data;
//
//		Comment(String paramString1, String paramString2) {
//			this.key = paramString1;
//			this.data = data;
//		}
//	}
//}