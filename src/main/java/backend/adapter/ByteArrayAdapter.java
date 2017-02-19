package backend.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import backend.helper.StringHelper;

public class ByteArrayAdapter extends XmlAdapter<String, byte[]> {

	private final static String CHARSET = "UTF-8";
	
	@Override
	public String marshal(byte[] value) throws Exception {
		if (value != null && value.length > 0) {
			return new String(value, CHARSET);
		}
		return null;
	}

	@Override
	public byte[] unmarshal(String value) throws Exception {
		if (!StringHelper.empty(value)) {
			return value.getBytes(CHARSET);
		}
		return null;
	}
}
