package backend.jpa.converter;

import java.io.UnsupportedEncodingException;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

//@Converter(autoApply = false)
public class BoxMessageResponseConverter implements AttributeConverter<String, byte[]> {

	private static final String UTF_8 = "UTF-8";

	@Override
	public byte[] convertToDatabaseColumn(String blobMessage) {
		try {
			if (blobMessage == null) {
				return null;
			}
			return blobMessage.getBytes(UTF_8);
		} catch (UnsupportedEncodingException e) {
			return blobMessage.getBytes();
		}
	}

	@Override
	public String convertToEntityAttribute(byte[] lob) {
		try {
			if (lob == null || lob.length == 0) {
				return null;
			}
			return new String(lob, UTF_8);
		} catch (UnsupportedEncodingException e) {
			return new String(lob);
		}
	}
}
