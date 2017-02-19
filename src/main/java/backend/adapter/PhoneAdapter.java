package backend.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class PhoneAdapter extends XmlAdapter<String, String> {

	@Override
	public String marshal(String v) throws Exception {
		if (v != null && v.startsWith("+48")) {
			return v.substring(3);
		}
		return v;
	}

	@Override
	public String unmarshal(String v) throws Exception {
		if (v != null && v.length() == 9) {
			return "+48" + v;
		}
		return v;
	}

}
