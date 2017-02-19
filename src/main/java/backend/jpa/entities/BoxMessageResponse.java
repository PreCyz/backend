package backend.jpa.entities;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import backend.jpa.Entry;
import backend.jpa.SequenceEntry;
import backend.jpa.converters.BoxMessageResponseConverter;

@Entity
@Table(name="BOX_MESSAGE_RESPONSE")
@NamedQuery(name="BoxMessageResponse.findAll", query="SELECT b FROM BoxMessageResponse b")
@Access(AccessType.FIELD)
public class BoxMessageResponse extends SequenceEntry {

	private static final long serialVersionUID = 1754440721725368185L;
	
	@Column(name="DOCUMENT_ID")
	private String documentId;
	
	@Transient
	private String oryginalType;
	
	@Column(name="RESPONSE_MESSAGE")
	private String message;
	
	@Lob
	@Column(name="RESPONSE_BLOB")
	@Convert(converter = BoxMessageResponseConverter.class)
	private String blobMessage;

	@Column(name="CREATE_DATE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;
	
	//bi-directional one-to-one association to BoxMessage
	@OneToOne(mappedBy="boxMessageResponse", optional=true)
	private BoxMessage boxMessage;
	
	public BoxMessageResponse() {}
	
	public String getDocumentId() {
		return documentId;
	}
	public void setDocumentId(String oryginalMessageId) {
		this.documentId = oryginalMessageId;
	}
	public String getOryginalType() {
		if(oryginalType != null){
			return oryginalType;
		}
		return boxMessage == null ? oryginalType : boxMessage.getType();
	}
	public void setOryginalType(String oryginalType) {
		this.oryginalType = oryginalType;
	}
	public String getMessage() {
		return (message == null ? "" : message) + (blobMessage == null ? "" : blobMessage);
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getBlobMessage() {
		return blobMessage;
	}
	public void setBlobMessage(String blobMessage) {
		this.blobMessage = blobMessage;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public BoxMessage getBoxMessage() {
		return boxMessage;
	}
	public void setBoxMessage(BoxMessage boxMessage) {
		this.boxMessage = boxMessage;
	}

	@Override
	public void loadLazy() {}

	public void update(Entry entry) {
		super.update(entry);
		BoxMessageResponse newEntry = (BoxMessageResponse) entry;
		this.documentId = newEntry.getDocumentId();
		this.oryginalType = newEntry.getOryginalType();
		this.message = newEntry.getMessage();
		this.blobMessage = newEntry.getBlobMessage();
		this.createDate = newEntry.getCreateDate();
	}

}
