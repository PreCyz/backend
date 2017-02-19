package backend.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;

public class CustomJsonJaxbProvider extends JacksonJaxbJsonProvider {
	
	

	public static enum SerialFeatures {
		WRAP_ROOT_VALUE (SerializationFeature.WRAP_ROOT_VALUE), 
		INDENT_OUTPUT (SerializationFeature.INDENT_OUTPUT), 
		WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED (SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
		
		private SerializationFeature serializationFeture;
		private SerialFeatures(SerializationFeature serializationFeture) {
			this.serializationFeture = serializationFeture;
		}
		public SerializationFeature serializationFeture() {
			return serializationFeture;
		}
	}
	
	public static enum DeserialFeatures {
		UNWRAP_ROOT_VALUE (DeserializationFeature.UNWRAP_ROOT_VALUE), 
		UNWRAP_SINGLE_VALUE_ARRAYS (DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS),
		ACCEPT_SINGLE_VALUE_AS_ARRAY (DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY),
		ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT (DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT),
		FAIL_ON_IGNORED_PROPERTIES (DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES), 
		FAIL_ON_UNKNOWN_PROPERTIES (DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		
		private DeserializationFeature deserializationFeature;
		private DeserialFeatures(DeserializationFeature deserializationFeature) {
			this.deserializationFeature = deserializationFeature;
		}
		public DeserializationFeature deserializationFeature() {
			return deserializationFeature;
		}
	}

	public CustomJsonJaxbProvider(Boolean defaultSettings) {
		super();
		enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
		enable(SerializationFeature.WRAP_ROOT_VALUE);
		disable(SerializationFeature.INDENT_OUTPUT);
		
		enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		disable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
		disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		
		_mapperConfig.getConfiguredMapper().setSerializationInclusion(Include.NON_NULL);
	}
	
	public CustomJsonJaxbProvider() {
		super();
		enable(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED);
		enable(SerializationFeature.WRAP_ROOT_VALUE);
		disable(SerializationFeature.INDENT_OUTPUT);
		
		enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
		enable(DeserializationFeature.ACCEPT_EMPTY_ARRAY_AS_NULL_OBJECT);
		disable(DeserializationFeature.UNWRAP_ROOT_VALUE);
		disable(DeserializationFeature.UNWRAP_SINGLE_VALUE_ARRAYS);
		disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		disable(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES);
		
		_mapperConfig.getConfiguredMapper().setSerializationInclusion(Include.NON_NULL);

	}

	public CustomJsonJaxbProvider enable(SerialFeatures ... features){
		if(!emptyArray(features)){
			for(SerialFeatures feature : features){
				enable(feature.serializationFeture());
			}
		}
		return this;
	}
	
	public CustomJsonJaxbProvider disable(SerialFeatures ... features){
		if(!emptyArray(features)){
			for(SerialFeatures feature : features){
				disable(feature.serializationFeture());
			}
		}
		return this;
	}
	
	public CustomJsonJaxbProvider enable(DeserialFeatures ... features){
		if(!emptyArray(features)){
			for(DeserialFeatures feature : features){
				enable(feature.deserializationFeature());
			}
		}
		return this;
	}
	
	public CustomJsonJaxbProvider disable(DeserialFeatures ... features){
		if(!emptyArray(features)){
			for(DeserialFeatures feature : features){
				disable(feature.deserializationFeature());
			}
		}
		return this;
	}
	
	private boolean emptyArray(Object[] array){
		return array == null || array.length == 0;
	}

}
