package backend.util;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CustomJsonObjectMapper extends ObjectMapper {
	private static final long serialVersionUID = 8022149579749642253L;
	
	public CustomJsonObjectMapper() {
		super();
		enable(Feature.ALLOW_UNQUOTED_CONTROL_CHARS);
		enable(JsonGenerator.Feature.AUTO_CLOSE_JSON_CONTENT);
		enable(MapperFeature.AUTO_DETECT_IS_GETTERS);
	}
}
