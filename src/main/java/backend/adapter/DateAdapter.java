package backend.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateAdapter extends XmlAdapter<String, Date> {

	private DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

	public Date unmarshal(String date) throws Exception {
		return df.parse(date);
	}

	public String marshal(Date date) throws Exception {
		return df.format(date);
	}
}
