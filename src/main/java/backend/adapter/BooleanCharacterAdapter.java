package backend.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class BooleanCharacterAdapter extends XmlAdapter<String, Boolean> {

	@Override
	public String marshal(Boolean v) throws Exception {
		if (Boolean.TRUE.equals(v)) {
			return "Y";
		} else if (Boolean.FALSE.equals(v)) {
			return "N";
		}
		return null;
	}

	@Override
	public Boolean unmarshal(String v) throws Exception {
		if ("Y".equals(v)) {
			return Boolean.TRUE;
		} else if ("N".equals(v)) {
			return Boolean.FALSE;
		} else {
			return null;
		}
	}


}
