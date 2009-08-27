package com.redv.blogmover.util;

import java.io.StringWriter;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;

public class DocumentToString {
	public static String toString(Document doc){
		DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();       
        Transformer transformer;
        String s = "";
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
			transformer.transform(source, new StreamResult(writer));
		} catch (TransformerConfigurationException e) {
			
		} catch (TransformerFactoryConfigurationError e) {
			
		} catch (TransformerException e) {
			
		}
		
        s = writer.getBuffer().toString();
        return s;
	}
}
