package backend.jpa.converter;

import java.io.UnsupportedEncodingException;

import javax.persistence.AttributeConverter;
//import javax.persistence.Converter;

import static backend.util.BackendConstants.UTF8_CODING;

//@Converter(autoApply = false)
public class BoxMessageResponseConverter implements AttributeConverter<String, byte[]> {

	@Override
	public byte[] convertToDatabaseColumn(String blobMessage) {
		try {
			if (blobMessage == null) {
				return null;
			}
			return blobMessage.getBytes(UTF8_CODING);
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
			return new String(lob, UTF8_CODING);
		} catch (UnsupportedEncodingException e) {
			return new String(lob);
		}
	}
}
