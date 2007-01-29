/**
 * 
 */
package com.redv.blogmover.bsps.com.blogchinese;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

import org.cyberneko.html.parsers.DOMParser;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * @author shutrazh
 * 
 */
public class HtmlFileToDocument {
	/**
	 * Get the xml document from a url.
	 * 
	 * @param url
	 * @param encoding
	 * @return
	 * @throws IOException
	 * @throws SAXException
	 */
	public Document getDocument(URL url, String encoding) throws IOException,
			SAXException {
		InputStream inputStream = url.openStream();
		try {
			return getDocument(inputStream, encoding);
		} finally {
			inputStream.close();
		}
	}

	/**
	 * Get the xml document from a html file.
	 * 
	 * @param file
	 * @param encoding
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getDocument(File file, String encoding)
			throws SAXException, IOException {
		FileInputStream inputStream = new FileInputStream(file);
		try {
			return getDocument(inputStream, encoding);
		} finally {
			inputStream.close();
		}
	}

	/**
	 * Get the xml document from a html input stream.
	 * 
	 * @param inputStream
	 * @param encoding
	 * @return
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getDocument(InputStream inputStream, String encoding)
			throws SAXException, IOException {
		Reader reader = new InputStreamReader(inputStream, encoding);
		return getDocument(reader);
	}

	/**
	 * Get the xml dom document from a reader.
	 * 
	 * @param characterStream
	 * @return the xml dom of the character stream.
	 * @throws SAXException
	 * @throws IOException
	 */
	public Document getDocument(Reader characterStream) throws SAXException,
			IOException {
		DOMParser parser = new DOMParser();
		parser
				.setFeature(
						"http://cyberneko.org/html/features/scanner/notify-builtin-refs",
						true);
		parser.setProperty("http://cyberneko.org/html/properties/names/elems",
				"lower");
		InputSource inputSource = new InputSource();
		inputSource.setCharacterStream(characterStream);
		parser.parse(inputSource);
		return parser.getDocument();
	}
}
