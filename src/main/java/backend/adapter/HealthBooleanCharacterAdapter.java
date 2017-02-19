package backend.adapter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class HealthBooleanCharacterAdapter extends XmlAdapter<String, Boolean> {

	@Override
	public String marshal(Boolean v) throws Exception {
		if (Boolean.TRUE.equals(v)) {
			return "I";
		} else {
			return "H";
		}
	}

	@Override
	public Boolean unmarshal(String v) throws Exception {
		if ("H".equals(v)) {
			return Boolean.FALSE;
		} else {
			return Boolean.TRUE;
		}
	}

}
