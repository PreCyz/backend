package backend.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.PropertyException;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.stream.StreamSource;

import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.JAXBContextProperties;
import org.eclipse.persistence.jaxb.MarshallerProperties;

import backend.exception.DAOException;

public class JAXBUtilJSON {
	
	private static final Map<Class<? extends Object>, JAXBContext> contexts = new HashMap<Class<? extends Object>, JAXBContext>();
	private static String UTF8_CODING = "UTF-8";
	
	public static enum OperationProperties { 
		NO_ROOT(MarshallerProperties.JSON_INCLUDE_ROOT, false), NO_FORMATTED_OUTPUT(Marshaller.JAXB_FORMATTED_OUTPUT, false),
		INCLUDE_ROOT(MarshallerProperties.JSON_INCLUDE_ROOT, true), FORMATTED_OUTPUT(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		
		private String key;
		private boolean value;
		
		private OperationProperties(String key, boolean value) {
			this.key = key;
			this.value = value;
		}
		public String getKey() {
			return key;
		}
		public boolean getValue() {
			return value;
		}
	}
	
	protected static JAXBContext getContext(Class<? extends Object> type) {
		if (contexts.containsKey(type)) {
			return contexts.get(type);
		} else {
			try {
				JAXBContext context = JAXBContextFactory.createContext(new Class[]{type}, contextProperty());
				contexts.put(type, context);
				return context;
			} catch (JAXBException e) {
				throw new DAOException("error initializing JAXB context for " + type, e);
			}
		}
	}

	//metoda utworzona dla czystości kodu, w tym miejscu można ustawić parametry dla JAXBContext, jeśli jakieś są potrzebne
	private static Properties contextProperty() {
		Properties ctxProperties = new Properties();
		ctxProperties.setProperty(JAXBContextProperties.MEDIA_TYPE, "application/json");
		return ctxProperties;
	}
	
	public static <A> A unmarshal(InputStream in, Class<A> type, OperationProperties ... unmarshalProperties) {
		JAXBContext context = getContext(type);
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			setUnmarshallerProperties(unmarshaller);
			JAXBElement<A> o = unmarshaller.unmarshal(new StreamSource(in), type);
			return o.getValue();
		} catch (Exception e) {
			throw new DAOException("Error unmarshaling from stream", e);
		}
	}

	private static void setUnmarshallerProperties(Unmarshaller unmarshaller, OperationProperties ... unmarshalProperties) throws PropertyException {
		if(unmarshalProperties == null || unmarshalProperties.length == 0){
			unmarshaller.setProperty(OperationProperties.INCLUDE_ROOT.getKey(), OperationProperties.INCLUDE_ROOT.getValue());
		} else{
			for(OperationProperties property : unmarshalProperties){
				unmarshaller.setProperty(property.getKey(), property.getValue());
			}
		}
	}
	
	public static <A> A unmarshalFromString(String string, Class<A> type, OperationProperties ... unmarshalProperties) {
		JAXBContext context = getContext(type);
		try {
			Unmarshaller unmarshaller = context.createUnmarshaller();
			setUnmarshallerProperties(unmarshaller, unmarshalProperties);
			StreamSource src = new StreamSource();
			src.setInputStream(new ByteArrayInputStream(string.getBytes(UTF8_CODING)));
			JAXBElement<A> o = unmarshaller.unmarshal(src, type);
			return o.getValue();
		} catch (Exception e) {
			throw new DAOException("Error unmarshaling string " + string, e);
		}
	}
	
	public static String marshalToString(Object object, OperationProperties ... contextParams) {
		Class<? extends Object> objectClass = object.getClass();
		return marshalToString(object, objectClass, contextParams);
	}
	
	public static String marshalToString(Object object, Class<? extends Object> type, OperationProperties ... marshalProperties) {
		JAXBContext context = getContext(type);
		
		try {
			Marshaller marshaller = context.createMarshaller();
			setMarshallerProperties(marshaller, marshalProperties);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			
			marshaller.marshal(object, out);
			return out.toString(UTF8_CODING);
		} catch (Exception e) {throw new DAOException("Error in JAXBUtil.marshalToString()", e);}
	}
	
	private static void setMarshallerProperties(Marshaller marshaller, OperationProperties ... marshalProperties) throws PropertyException {
		if(marshalProperties == null || marshalProperties.length == 0){
			marshaller.setProperty(OperationProperties.INCLUDE_ROOT.getKey(), OperationProperties.INCLUDE_ROOT.getValue());
			marshaller.setProperty(OperationProperties.FORMATTED_OUTPUT.getKey(), OperationProperties.FORMATTED_OUTPUT.getValue());
		} else{
			for(OperationProperties property : marshalProperties){
				marshaller.setProperty(property.getKey(), property.getValue());
			}
		}
	}
	
	public static void marshal(Object object, OutputStream out, OperationProperties ... marshalProperties) {
		Class<? extends Object> objectClass = object.getClass();
		marshal(object, out, objectClass, marshalProperties);
	}
	
	public static void marshal(Object object, OutputStream out, Class<? extends Object> type, OperationProperties ... marshalProperties) {
		JAXBContext context = getContext(type);
		
		try {
			Marshaller marshaller = context.createMarshaller();
			setMarshallerProperties(marshaller);
			marshaller.marshal(object, out);
		} catch (Exception e) {
			throw new DAOException("Error marshaling object to stream " + object, e);
		}
	}
}
