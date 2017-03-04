package backend.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.io.DOMReader;
import org.dom4j.io.DOMWriter;

import org.w3c.dom.*;

import backend.exception.ApplicationUncheckedException;

import java.io.*;

import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import static backend.util.BackendConstants.UTF8_CODING;

public class JAXBUtil {

	private static final Map<Class<? extends Object>, JAXBContext> contexts = 
			new HashMap<Class<? extends Object>, JAXBContext>();
	
	public static org.w3c.dom.Document marshalToW3CDocument(Object object) {
		Class<? extends Object> objectClass = object.getClass();
		return marshalToW3CDocument(object, objectClass);
	}
	
	public static org.w3c.dom.Document marshalToW3CDocument(Object object, Class<? extends Object> type) {
		try {
			org.w3c.dom.Document document = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
			
			if (object == null || type == null) {
				return document;
			}
			
			JAXBContext context = getContext(type);
			
			context.createMarshaller().marshal(object, document);
			
			return document;
		} catch (Exception e) {
			throw new ApplicationUncheckedException("Error marshaling object " + object, e);
		}
	}
	
	public static Document marshal(Object object) {
		Class<? extends Object> objectClass = object.getClass();
		return marshal(object, objectClass);
	}
	
	public static Document marshal(Object object, Class<? extends Object> type) {
		if (object == null || type == null) {
			return DocumentFactory.getInstance().createDocument();
		}
		
		JAXBContext context = getContext(type);
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			org.w3c.dom.Document w3cDoc = builder.newDocument();

			context.createMarshaller().marshal(object, w3cDoc);
			
			DOMReader reader = new DOMReader();
			return reader.read(w3cDoc);
		} catch (Exception e) {
			throw new ApplicationUncheckedException("Error marshaling object " + object, e);
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <A> A unmarshal(Document document, Class<A> type) {
		JAXBContext context = getContext(type);
		try {
			DOMWriter writer = new DOMWriter();
			org.w3c.dom.Document w3cDoc = writer.write(document);
			//System.out.println(xmlToString(w3cDoc));

			return (A)context.createUnmarshaller().unmarshal(w3cDoc);
			
		} catch (Exception e) {
			throw new ApplicationUncheckedException("Error unmarshaling document " + document, e);
		}
	}
	
	private static JAXBContext getContext(Class<? extends Object> type) {
		if (contexts.containsKey(type)) {
			return contexts.get(type);
		} else {
			try {
				JAXBContext context = JAXBContext.newInstance(type);
				contexts.put(type, context);
				
				return context;
			} catch (JAXBException e) {
				throw new ApplicationUncheckedException("error initialiing JAXB context for " + type, e);
			}
		}
	}
	
	public static String xmlToString(Node node) {
        try {
            Source source = new DOMSource(node);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            TransformerFactory factory = TransformerFactory.newInstance();
            Transformer transformer = factory.newTransformer();
            transformer.transform(source, result);
            return stringWriter.getBuffer().toString();
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
        return null;
    }
	
	public static String marshalToString(Object object) {
		Class<? extends Object> objectClass = object.getClass();
		return marshalToString(object, objectClass);
	}
	
	public static String marshalToString(Object object, Class<? extends Object> type) {
		JAXBContext context = getContext(type);
		
		try {
			Marshaller marshaller = context.createMarshaller();
			ByteArrayOutputStream out = new ByteArrayOutputStream(); 
			
			marshaller.marshal(object, out);
			return out.toString(UTF8_CODING);
		} catch (Exception e) {throw new ApplicationUncheckedException("Error in JAXBUtil.marshalToString()", e);}
	}
	
	@SuppressWarnings("unchecked")
	public static <A> A unmarshalFromString(String string, Class<A> type) {
		JAXBContext context = getContext(type);
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			return (A) unmarshaller.unmarshal(new ByteArrayInputStream(string.getBytes(UTF8_CODING)));
		} catch (Exception e) {
			throw new ApplicationUncheckedException("Error unmarshaling string " + string, e);
		}
	}
	
	public static void marshal(Object object, OutputStream out) {
		Class<? extends Object> objectClass = object.getClass();
		marshal(object, out, objectClass);
	}
	
	public static void marshal(Object object, OutputStream out, Class<? extends Object> type) {
		JAXBContext context = getContext(type);
		
		try {
			context.createMarshaller().marshal(object, out);
		} catch (Exception e) {
			throw new ApplicationUncheckedException("Error marshaling object to stream " + object, e);
		}
	}
	
	public static <A> A unmarshal(InputStream in, Class<A> type) {
		JAXBContext context = getContext(type);
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			JAXBElement<A> o = unmarshaller.unmarshal(new StreamSource(in), type);
			return o.getValue();
		} catch (Exception e) {
			throw new ApplicationUncheckedException("Error unmarshaling from stream", e);
		}
	}
}
