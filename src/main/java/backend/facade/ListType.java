package backend.facade;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class ListType {
	
    private List<String> stringList;

    @XmlElement
	public List<String> getStringList() {
		return this.stringList;
	}

	public void setStringList(List<String> stringList) {
		this.stringList = stringList;
	}


}
