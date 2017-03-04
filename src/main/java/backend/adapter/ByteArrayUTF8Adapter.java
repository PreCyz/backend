package backend.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import backend.util.helper.StringHelper;

import static backend.util.BackendConstants.UTF8_CODING;

public class ByteArrayUTF8Adapter extends XmlAdapter<String, byte[]> {

	@Override
	public String marshal(byte[] value) throws Exception {
		if (value != null && value.length > 0) {
			return new String(value, UTF8_CODING);
		}
		return null;
	}

	@Override
	public byte[] unmarshal(String value) throws Exception {
		if (!StringHelper.empty(value)) {
			return value.getBytes(UTF8_CODING);
		}
		return null;
	}
}
