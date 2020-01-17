package com.itheima.util;

import java.io.FileOutputStream;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

public class XmlUtil {
	private static String filepath;//xml文件的路径
	static{
		URL url = XmlUtil.class.getClassLoader().getResource("user.xml");
		filepath = url.getPath();
	}
	public static Document getDocument() throws Exception{
		SAXReader reader = new SAXReader();
		return reader.read(filepath);
	}
	public static void write2xml(Document document)throws Exception{
		OutputFormat format = OutputFormat.createPrettyPrint();
		XMLWriter writer = new XMLWriter(new FileOutputStream(filepath), format);
		writer.write(document);
		writer.close();
	}
}
