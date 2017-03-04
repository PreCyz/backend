package backend.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import backend.exception.ApplicationUncheckedException;

/** This class creates full json. If additional options are required, one has to use proper features.
  * Objects to parse have to use proper annotations. in order to get requested output (the same way as in JXB)
  * How to annotate? Find in JacksonJsonProvider tutorial.
 */

public class JacksonUtilJson {
	
	public static enum SerializationFeatures { 
		INDENT_OUTPUT(SerializationFeature.INDENT_OUTPUT, true),
		NO_INDENT_OUTPUT(SerializationFeature.INDENT_OUTPUT, false),
		WRAP_ROOT(SerializationFeature.WRAP_ROOT_VALUE, true),
		NO_WRAP_ROOT(SerializationFeature.WRAP_ROOT_VALUE, false);
		
		private SerializationFeature key;
		private boolean value;
		
		private SerializationFeatures(SerializationFeature key, boolean value) {
			this.key = key;
			this.value = value;
		}
		public SerializationFeature key() {
			return key;
		}
		public boolean value() {
			return value;
		}
	}
	
	public static enum DeserializationFeatures {
		UNWRAP_ROOT(DeserializationFeature.UNWRAP_ROOT_VALUE, true),
		NO_UNWRAP_ROOT(DeserializationFeature.UNWRAP_ROOT_VALUE, false),
		FAIL_ON_IGNORED_PROPERTIES(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, true),
		NO_FAIL_ON_IGNORED_PROPERTIES(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES, false),
		ACCEPT_SINGLE_VALUE_AS_ARRAY(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true),
		NO_ACCEPT_SINGLE_VALUE_AS_ARRAY(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, false);
		
		private DeserializationFeature key;
		private boolean value;
		
		private DeserializationFeatures(DeserializationFeature key, boolean value) {
			this.key = key;
			this.value = value;
		}
		public DeserializationFeature key() {
			return key;
		}
		public boolean value() {
			return value;
		}
	}
	
	public static String marshalToString(Object object, SerializationFeatures ... features) {
		try {
			ObjectMapper mapper = createSerializationObjectMapper(features);
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			String errorMsg = String.format("Error when marshalToString: %s", e.getMessage());
			throw new ApplicationUncheckedException(errorMsg, e);
		}
	}
	
	private static ObjectMapper createSerializationObjectMapper(SerializationFeatures ... features) {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(SerializationFeatures.NO_INDENT_OUTPUT.key(), SerializationFeatures.NO_INDENT_OUTPUT.value());
		mapper.configure(SerializationFeatures.WRAP_ROOT.key(), SerializationFeatures.WRAP_ROOT.value());
		if (notEmptyArray(features)) {
			for (SerializationFeatures feature : features) {
				mapper.configure(feature.key(), feature.value());
			}
		}
		return mapper;
	}
	
	private static boolean notEmptyArray(Object[] array) {
		return array != null && array.length > 0; 
	}
	
	public static void marshal(Object object, OutputStream out, SerializationFeatures ... features) {
		try {
			ObjectMapper mapper = createSerializationObjectMapper(features);
			mapper.writeValue(out, object);
		} catch (IOException e) {
			throw new ApplicationUncheckedException("Error when marshal to output stream.", e);
		}
	}
	
	private static ObjectMapper createDeserializationObjectMapper(DeserializationFeatures ... features){
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(DeserializationFeatures.UNWRAP_ROOT.key(), DeserializationFeatures.UNWRAP_ROOT.value());
		mapper.configure(DeserializationFeatures.ACCEPT_SINGLE_VALUE_AS_ARRAY.key(), 
				DeserializationFeatures.ACCEPT_SINGLE_VALUE_AS_ARRAY.value());
		if (notEmptyArray(features)) {
			for (DeserializationFeatures feature : features) {
				mapper.configure(feature.key(), feature.value());
			}
		}
		return mapper;
	}


	public static <A> A unmarshalFromString(String json, Class<A> type, DeserializationFeatures ... features) {
		try {
			ObjectMapper mapper = createDeserializationObjectMapper(features);
	        return mapper.readValue(json, type);
		} catch (IOException e) {
			String errMsg = String.format("Error when unmarshal String %s.", json);
			throw new ApplicationUncheckedException(errMsg, e);
		}
	}

	public static <A> A unmarshal(InputStream is, Class<A> type, DeserializationFeatures ... features) {
		try {
			ObjectMapper mapper = createDeserializationObjectMapper(features);
	        return mapper.readValue(is, type);
		} catch (IOException e) {
			throw new ApplicationUncheckedException("Error when unmarshal input stream.", e);
		}
	}
}
