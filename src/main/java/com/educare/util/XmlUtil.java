package com.educare.util;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.URL;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * @Class Name : XmlUtil.java
 * @author SI개발팀 박용주
 * @since 2019. 12. 23.
 * @version 1.0
 * @see
 * @Description
 * 
 * <pre>
 *
 *
 * 수정일		    	수정자		    	수정내용
 * --------	  		---------	  		------------------------
 * 2019. 12. 23.	SI개발팀 박용주     		최초생성 
 * </pre>
 */
public class XmlUtil {
	
	public static Document parseWithSAX(File aFile) throws DocumentException {
		SAXReader xmlReader = new SAXReader();
		return xmlReader.read(aFile);
	}

	public static Document parseWithSAX(String file) throws DocumentException {
		return parseWithSAX(new File(file));
	}

	public static Document parseWithSAX(URL aURL) throws DocumentException {
		SAXReader xmlReader = new SAXReader();
		return xmlReader.read(aURL);
	}

	public static void serializetoXML(OutputStream out, String aEncodingScheme,
			Document doc) throws UnsupportedEncodingException, IOException {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding(aEncodingScheme);
		XMLWriter writer = new XMLWriter(out, outformat);
		writer.write(doc);
		writer.flush();
	}

	public static void serializetoXML(Writer out, String aEncodingScheme,
			Document doc) throws IOException {
		OutputFormat outformat = OutputFormat.createPrettyPrint();
		outformat.setEncoding(aEncodingScheme);
		XMLWriter writer = new XMLWriter(out, outformat);
		writer.write(doc);
		writer.flush();
	}

	public static Object XMLDecode(String file) {
		/*
		 * try { XMLDecoder e = new XMLDecoder(new BufferedInputStream( new
		 * FileInputStream(file))); Object obj = e.readObject(); e.close();
		 * return obj; } catch (FileNotFoundException ex) { return null; }
		 */
		FileInputStream fis = null;
		BufferedInputStream bis = null;
		try {
			fis = new FileInputStream(file); 
			bis = new BufferedInputStream(fis); 
			XMLDecoder e = new XMLDecoder(bis);

			return e.readObject();

		} catch (FileNotFoundException ex) {
			return null;
		} finally{
			try {
				if (bis != null) bis.close();
				if (fis != null) fis.close();
			} catch (IOException e) {
				return null;
			}
		}

	}
}
